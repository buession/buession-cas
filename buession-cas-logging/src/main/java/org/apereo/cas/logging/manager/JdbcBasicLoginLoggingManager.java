/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.logging.manager;

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.logging.core.LogData;
import com.buession.logging.core.request.Request;
import org.apereo.cas.logging.config.basic.BasicJdbcLogProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * JDBC 基本登录日志管理器
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class JdbcBasicLoginLoggingManager extends AbstractLoginLoggingManager implements BasicLoginLoggingManager {

	private final JdbcBasicLoginLoggingJdbcDaoSupport daoSupport;

	public JdbcBasicLoginLoggingManager(final DataSource dataSource, final TransactionTemplate transactionTemplate,
										final BasicJdbcLogProperties properties) {
		super();
		Assert.isNull(transactionTemplate, "TransactionTemplate cloud not be null.");
		this.daoSupport = new JdbcBasicLoginLoggingJdbcDaoSupport(dataSource, transactionTemplate, properties);
	}

	@Override
	public void execute(final LogData logData, final Request request) {
		daoSupport.execute(logData);
	}

	private final static class JdbcBasicLoginLoggingJdbcDaoSupport extends NamedParameterJdbcDaoSupport {

		private final TransactionTemplate transactionTemplate;

		private final BasicJdbcLogProperties properties;

		private final String sql;

		public JdbcBasicLoginLoggingJdbcDaoSupport(final DataSource dataSource,
												   final TransactionTemplate transactionTemplate,
												   final BasicJdbcLogProperties properties) {
			this.transactionTemplate = transactionTemplate;
			this.properties = properties;
			this.sql = buildLogSql();
			setDataSource(dataSource);
		}

		public void execute(final LogData logData) {
			List<Object> arguments = new ArrayList<>(12);

			// 登录时间
			if(Validate.isNotEmpty(properties.getLoginTimeFieldName())){
				arguments.add(logData.getDateTime());
			}

			// 登录 IP 地址
			if(Validate.isNotEmpty(properties.getLoginIpFieldName())){
				arguments.add(logData.getClientIp());
			}

			// 登录 User-Agent
			if(Validate.isNotEmpty(properties.getUserAgentFieldName())){
				arguments.add(logData.getUserAgent());
			}

			// 登录操作系统名称
			if(Validate.isNotEmpty(properties.getOperatingSystemNameFieldName())){
				arguments.add(logData.getOperatingSystem().getName());
			}

			// 登录操作系统版本
			if(Validate.isNotEmpty(properties.getOperatingSystemVersionFieldName())){
				arguments.add(logData.getOperatingSystem().getVersion());
			}

			// 登录设备
			if(Validate.isNotEmpty(properties.getDeviceTypeFieldName())){
				arguments.add(logData.getDeviceType().name());
			}

			// 登录浏览器名称
			if(Validate.isNotEmpty(properties.getBrowserNameFieldName())){
				arguments.add(logData.getBrowser().getName());
			}

			// 登录浏览器版本
			if(Validate.isNotEmpty(properties.getBrowserVersionFieldName())){
				arguments.add(logData.getBrowser().getVersion());
			}

			// 登录国家 code
			if(Validate.isNotEmpty(properties.getCountryCodeFieldName())){
				arguments.add(logData.getLocation().getCountry().getCode());
			}

			// 登录国家名称
			if(Validate.isNotEmpty(properties.getCountryNameFieldName())){
				arguments.add(logData.getLocation().getCountry().getName());
			}

			// 登录地区名称
			if(Validate.isNotEmpty(properties.getDistrictNameFieldName())){
				arguments.add(logData.getLocation().getDistrict().getFullName());
			}

			arguments.add(logData.getPrincipal().getUserName());

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(@NotNull TransactionStatus transactionStatus) {
					getJdbcTemplate().update(sql, arguments.toArray(new Object[]{}));
				}

			});
		}

		private String buildLogSql() {
			final StringBuilder sql = new StringBuilder();
			final StringJoiner updateFields = new StringJoiner(", ");

			sql.append("UPDATE `").append(properties.getTableName()).append("` SET ");

			// 登录次数
			if(Validate.isNotEmpty(properties.getLoginTimesFieldName())){
				updateFields.add("`" + properties.getLoginTimesFieldName() + "` = `" +
						properties.getLoginTimesFieldName() + "` + 1");
			}

			// 上次登录时间
			if(Validate.isNotEmpty(properties.getLastLoginTimeFieldName()) &&
					Validate.isNotEmpty(properties.getLoginTimeFieldName())){
				updateFields.add("`" + properties.getLastLoginTimeFieldName() + "` = `" +
						properties.getLoginTimeFieldName() + "`");
			}

			// 登录时间
			if(Validate.isNotEmpty(properties.getLoginTimeFieldName())){
				updateFields.add("`" + properties.getLoginTimeFieldName() + "` = ?");
			}

			// 上次登录 IP 地址
			if(Validate.isNotEmpty(properties.getLastLoginIpFieldName()) &&
					Validate.isNotEmpty(properties.getLoginIpFieldName())){
				updateFields.add("`" + properties.getLastLoginIpFieldName() + "` = `" +
						properties.getLoginIpFieldName() + "`");
			}

			// 登录 IP 地址
			if(Validate.isNotEmpty(properties.getLoginIpFieldName())){
				updateFields.add("`" + properties.getLoginIpFieldName() + "` = ?");
			}

			// 上次登录 User-Agent
			if(Validate.isNotEmpty(properties.getLastUserAgentFieldName()) &&
					Validate.isNotEmpty(properties.getUserAgentFieldName())){
				updateFields.add("`" + properties.getLastUserAgentFieldName() + "` = `" +
						properties.getUserAgentFieldName() + "`");
			}

			// 登录 User-Agent
			if(Validate.isNotEmpty(properties.getUserAgentFieldName())){
				updateFields.add("`" + properties.getUserAgentFieldName() + "` = ?");
			}

			// 上次登录操作系统名称
			if(Validate.isNotEmpty(properties.getLastOperatingSystemNameFieldName()) &&
					Validate.isNotEmpty(properties.getOperatingSystemNameFieldName())){
				updateFields.add("`" + properties.getLastOperatingSystemNameFieldName() + "` = `" +
						properties.getOperatingSystemNameFieldName() + "`");
			}

			// 登录操作系统名称
			if(Validate.isNotEmpty(properties.getOperatingSystemNameFieldName())){
				updateFields.add("`" + properties.getOperatingSystemNameFieldName() + "` = ?");
			}

			// 上次登录操作系统版本
			if(Validate.isNotEmpty(properties.getLastOperatingSystemVersionFieldName()) &&
					Validate.isNotEmpty(properties.getOperatingSystemVersionFieldName())){
				updateFields.add("`" + properties.getLastOperatingSystemVersionFieldName() + "` = `" +
						properties.getOperatingSystemVersionFieldName() + "`");
			}

			// 登录操作系统版本
			if(Validate.isNotEmpty(properties.getOperatingSystemVersionFieldName())){
				updateFields.add("`" + properties.getOperatingSystemVersionFieldName() + "` = ?");
			}

			// 上次登录设备
			if(Validate.isNotEmpty(properties.getLastDeviceTypeFieldName()) &&
					Validate.isNotEmpty(properties.getDeviceTypeFieldName())){
				updateFields.add("`" + properties.getLastDeviceTypeFieldName() + "` = `" +
						properties.getDeviceTypeFieldName() + "`");
			}

			// 登录设备
			if(Validate.isNotEmpty(properties.getDeviceTypeFieldName())){
				updateFields.add("`" + properties.getDeviceTypeFieldName() + "` = ?");
			}

			// 上次登录浏览器名称
			if(Validate.isNotEmpty(properties.getLastBrowserNameFieldName()) &&
					Validate.isNotEmpty(properties.getBrowserNameFieldName())){
				updateFields.add("`" + properties.getLastBrowserNameFieldName() + "` = `" +
						properties.getBrowserNameFieldName() + "`");
			}

			// 登录浏览器名称
			if(Validate.isNotEmpty(properties.getBrowserNameFieldName())){
				updateFields.add("`" + properties.getBrowserNameFieldName() + "` = ?");
			}

			// 上次登录浏览器版本
			if(Validate.isNotEmpty(properties.getLastBrowserVersionFieldName()) &&
					Validate.isNotEmpty(properties.getBrowserVersionFieldName())){
				updateFields.add("`" + properties.getLastBrowserVersionFieldName() + "` = `" +
						properties.getBrowserVersionFieldName() + "`");
			}

			// 登录浏览器版本
			if(Validate.isNotEmpty(properties.getBrowserVersionFieldName())){
				updateFields.add("`" + properties.getBrowserVersionFieldName() + "` = ?");
			}

			// 上次登录国家 code
			if(Validate.isNotEmpty(properties.getLastCountryCodeFieldName()) &&
					Validate.isNotEmpty(properties.getCountryCodeFieldName())){
				updateFields.add("`" + properties.getLastCountryCodeFieldName() + "` = `" +
						properties.getCountryCodeFieldName() + "`");
			}

			// 登录国家 code
			if(Validate.isNotEmpty(properties.getCountryCodeFieldName())){
				updateFields.add("`" + properties.getCountryCodeFieldName() + "` = ?");
			}

			// 上次登录国家名称
			if(Validate.isNotEmpty(properties.getLastCountryNameFieldName()) &&
					Validate.isNotEmpty(properties.getCountryNameFieldName())){
				updateFields.add("`" + properties.getLastCountryNameFieldName() + "` = `" +
						properties.getCountryNameFieldName() + "`");
			}

			// 登录国家名称
			if(Validate.isNotEmpty(properties.getCountryNameFieldName())){
				updateFields.add("`" + properties.getCountryNameFieldName() + "` = ?");
			}

			// 上次登录地区名称
			if(Validate.isNotEmpty(properties.getLastDistrictNameFieldName()) &&
					Validate.isNotEmpty(properties.getDistrictNameFieldName())){
				updateFields.add("`" + properties.getLastDistrictNameFieldName() + "` = `" +
						properties.getDistrictNameFieldName() + "`");
			}

			// 登录地区名称
			if(Validate.isNotEmpty(properties.getDistrictNameFieldName())){
				updateFields.add("`" + properties.getDistrictNameFieldName() + "` = ?");
			}

			sql.append(updateFields)
					.append(" WHERE `")
					.append(properties.getIdFieldName())
					.append("` = ?");

			return sql.toString();
		}

	}

}
