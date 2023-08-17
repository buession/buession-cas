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
package org.apereo.cas.logging.config.history;

import com.buession.core.id.IdGenerator;
import com.buession.jdbc.datasource.config.PoolConfiguration;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.support.config.HandlerProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;

import java.io.Serializable;

/**
 * JDBC 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryJdbcLogProperties extends AbstractJpaProperties implements HandlerProperties, Serializable {

	private final static long serialVersionUID = 6396868404292124235L;

	/**
	 * 数据库驱动类名
	 */
	private String driverClassName;

	/**
	 * 连接池配置
	 */
	private PoolConfiguration poolConfiguration;

	/**
	 * SQL
	 */
	private String sql;

	/**
	 * ID 生成器
	 */
	private Class<? extends IdGenerator<?>> idGenerator;

	/**
	 * 日期时间格式
	 */
	private String dateTimeFormat;

	/**
	 * 请求参数格式化为字符串
	 */
	private Class<? extends MapFormatter<Object>> requestParametersFormatter = JsonMapFormatter.class;

	/**
	 * Geo 格式化
	 */
	private Class<? extends GeoFormatter> geoFormatter = DefaultGeoFormatter.class;

	/**
	 * 附加参数格式化为字符串
	 */
	private Class<? extends MapFormatter<Object>> extraFormatter = JsonMapFormatter.class;

	/**
	 * 返回数据库驱动类名
	 *
	 * @return 数据库驱动类名
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * 设置数据库驱动类名
	 *
	 * @param driverClassName
	 * 		数据库驱动类名
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * 返回连接池配置
	 *
	 * @return 连接池配置
	 */
	public PoolConfiguration getPoolConfiguration() {
		return poolConfiguration;
	}

	/**
	 * 设置连接池配置
	 *
	 * @param poolConfiguration
	 * 		连接池配置
	 */
	public void setPoolConfiguration(PoolConfiguration poolConfiguration) {
		this.poolConfiguration = poolConfiguration;
	}

	/**
	 * 返回 SQL
	 *
	 * @return SQL
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 设置 SQL
	 *
	 * @param sql
	 * 		SQL
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 返回 ID 生成器
	 *
	 * @return ID 生成器
	 */
	public Class<? extends IdGenerator<?>> getIdGenerator() {
		return idGenerator;
	}

	/**
	 * 设置 ID 生成器
	 *
	 * @param idGenerator
	 * 		ID 生成器
	 */
	public void setIdGenerator(Class<? extends IdGenerator<?>> idGenerator) {
		this.idGenerator = idGenerator;
	}

	/**
	 * 返回日期时间格式
	 *
	 * @return 日期时间格式
	 */
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	/**
	 * 设置日期时间格式
	 *
	 * @param dateTimeFormat
	 * 		日期时间格式
	 */
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * 返回请求参数格式化为字符串
	 *
	 * @return 请求参数格式化为字符串
	 */
	public Class<? extends MapFormatter<Object>> getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(Class<? extends MapFormatter<Object>> requestParametersFormatter) {
		this.requestParametersFormatter = requestParametersFormatter;
	}

	/**
	 * 返回 Geo 格式化
	 *
	 * @return Geo 格式化
	 */
	public Class<? extends GeoFormatter> getGeoFormatter() {
		return geoFormatter;
	}

	/**
	 * 设置 Geo 格式化
	 *
	 * @param geoFormatter
	 * 		Geo 格式化
	 */
	public void setGeoFormatter(Class<? extends GeoFormatter> geoFormatter) {
		this.geoFormatter = geoFormatter;
	}

	/**
	 * 返回附加参数格式化为字符串
	 *
	 * @return 附加参数格式化为字符串
	 */
	public Class<? extends MapFormatter<Object>> getExtraFormatter() {
		return extraFormatter;
	}

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(Class<? extends MapFormatter<Object>> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

}
