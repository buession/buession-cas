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
import org.apereo.cas.logging.config.history.HistoryJdbcLogProperties;
import org.apereo.cas.logging.model.LoginData;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.StringJoiner;

/**
 * JDBC 历史登录日志管理器
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class JdbcHistoryLoginLoggingManager extends AbstractLoginLoggingManager implements HistoryLoginLoggingManager {

	private final JdbcHistoryLoginLoggingJdbcDaoSupport daoSupport;

	public JdbcHistoryLoginLoggingManager(final DataSource dataSource, final TransactionTemplate transactionTemplate,
										  final HistoryJdbcLogProperties properties){
		super();
		Assert.isNull(transactionTemplate, "TransactionTemplate cloud not be null.");
		this.daoSupport = new JdbcHistoryLoginLoggingJdbcDaoSupport(dataSource, transactionTemplate, properties);
	}

	@Override
	public void execute(final LoginData loginData){
		daoSupport.execute(loginData);
	}

	private final static class JdbcHistoryLoginLoggingJdbcDaoSupport extends NamedParameterJdbcDaoSupport {

		private final TransactionTemplate transactionTemplate;

		private final HistoryJdbcLogProperties properties;

		private final String sql;

		public JdbcHistoryLoginLoggingJdbcDaoSupport(final DataSource dataSource,
													 final TransactionTemplate transactionTemplate,
													 final HistoryJdbcLogProperties properties){
			setDataSource(dataSource);

			this.transactionTemplate = transactionTemplate;
			this.properties = properties;
			this.sql = buildLogSql();
		}

		public void execute(final LoginData loginData){
			Object[] arguments = new Object[12];

			// 用户名
			arguments[0] = loginData.getId();
			// 登录时间
			arguments[1] = loginData.getDateTime();
			// 登录 IP 地址
			arguments[2] = loginData.getClientIp();
			// 登录 User-Agent
			arguments[3] = loginData.getUserAgent();
			// 登录操作系统名称
			arguments[4] = loginData.getOperatingSystem().name();
			// 登录操作系统版本
			arguments[5] = loginData.getOperatingSystem().getVersion();
			// 登录设备
			arguments[6] = loginData.getOperatingSystem().getDeviceType().name();
			// 登录浏览器名称
			arguments[7] = loginData.getBrowser().name();
			// 登录浏览器版本
			arguments[8] = loginData.getBrowser().getVersion();
			// 登录国家 code
			arguments[9] = loginData.getLocation().getCountry().getCode();
			// 登录国家名称
			arguments[10] = loginData.getLocation().getCountry().getName();
			// 登录地区名称
			arguments[11] = loginData.getLocation().getDistrict().getFullName();

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(@NotNull TransactionStatus transactionStatus){
					getJdbcTemplate().update(sql, arguments);
				}

			});
		}

		private String buildLogSql(){
			final StringBuilder sql = new StringBuilder();
			final StringJoiner fields = new StringJoiner(", ");

			// 用户名字段名称
			fields.add("`" + properties.getUserNameFieldName() + "`");

			// 登录时间字段名称
			fields.add("`" + properties.getLoginTimeFieldName() + "`");

			// 登录 IP 字段名称
			fields.add("`" + properties.getLoginIpFieldName() + "`");

			// User-Agent 字段名称
			fields.add("`" + properties.getUserAgentFieldName() + "`");

			// 操作系统名称字段名称
			fields.add("`" + properties.getOperatingSystemNameFieldName() + "`");

			// 操作系统版本字段名称
			fields.add("`" + properties.getOperatingSystemVersionFieldName() + "`");

			// 设备类型字段名称
			fields.add("`" + properties.getDeviceTypeFieldName() + "`");

			// 浏览器名称字段名称
			fields.add("`" + properties.getBrowserNameFieldName() + "`");

			// 浏览器版本字段名称
			fields.add("`" + properties.getBrowserVersionFieldName() + "`");

			// 国家 Code 字段名称
			fields.add("`" + properties.getCountryCodeFieldName() + "`");

			// 国家名称字段名称
			fields.add("`" + properties.getCountryNameFieldName() + "`");

			// 地区名称字段名称
			fields.add("`" + properties.getDistrictNameFieldName() + "`");

			sql.append("INSERT `")
					.append(properties.getTableName())
					.append("` (")
					.append(fields)
					.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			return sql.toString();
		}

	}

}
