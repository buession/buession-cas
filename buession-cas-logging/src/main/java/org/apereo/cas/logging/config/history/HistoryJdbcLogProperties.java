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
import com.buession.logging.jdbc.core.FieldConfiguration;
import com.buession.logging.jdbc.formatter.DateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultDateTimeFormatter;
import com.buession.logging.jdbc.formatter.DefaultGeoFormatter;
import com.buession.logging.jdbc.formatter.GeoFormatter;
import com.buession.logging.jdbc.formatter.JsonMapFormatter;
import com.buession.logging.jdbc.formatter.MapFormatter;

import java.io.Serializable;

/**
 * JDBC 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryJdbcProperties implements Serializable {

	private final static long serialVersionUID = 6396868404292124235L;

	/**
	 * 数据库驱动类名
	 */
	private String driverClassName;

	/**
	 * JDBC URL
	 */
	private String url;

	/**
	 * 数据库账号
	 */
	private String username;

	/**
	 * 数据库密码
	 */
	private String password;

	/**
	 * 连接池配置
	 */
	private PoolConfiguration poolConfiguration;

	/**
	 * 数据表名称
	 */
	private String tableName;

	/**
	 * 字段配置
	 */
	private FieldConfiguration fieldConfiguration;

	/**
	 * ID 生成器
	 */
	private Class<? extends IdGenerator<?>> idGenerator;

	/**
	 * 日期时间格式化对象
	 */
	private Class<? extends DateTimeFormatter> dateTimeFormatter = DefaultDateTimeFormatter.class;

	/**
	 * 请求参数格式化为字符串
	 */
	private Class<? extends MapFormatter> requestParametersFormatter = JsonMapFormatter.class;

	/**
	 * Geo 格式化
	 */
	private Class<? extends GeoFormatter> geoFormatter = DefaultGeoFormatter.class;

	/**
	 * 附加参数格式化为字符串
	 */
	private Class<? extends MapFormatter> extraFormatter = JsonMapFormatter.class;

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
	 * 返回 JDBC URL
	 *
	 * @return JDBC URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 JDBC URL
	 *
	 * @param url
	 * 		JDBC URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回数据库账号
	 *
	 * @return 数据库账号
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置数据库账号
	 *
	 * @param username
	 * 		数据库账号
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回数据库密码
	 *
	 * @return 数据库密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置数据库密码
	 *
	 * @param password
	 * 		数据库密码
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * 返回数据表名称
	 *
	 * @return 数据表名称
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置数据表名称
	 *
	 * @param tableName
	 * 		数据表名称
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回字段配置
	 *
	 * @return 字段配置
	 */
	public FieldConfiguration getFieldConfiguration() {
		return fieldConfiguration;
	}

	/**
	 * 设置字段配置
	 *
	 * @param fieldConfiguration
	 * 		字段配置
	 */
	public void setFieldConfiguration(FieldConfiguration fieldConfiguration) {
		this.fieldConfiguration = fieldConfiguration;
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
	 * 返回日期时间格式化对象
	 *
	 * @return 日期时间格式化对象
	 */
	public Class<? extends DateTimeFormatter> getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	/**
	 * 设置日期时间格式化对象
	 *
	 * @param dateTimeFormatter
	 * 		日期时间格式化对象
	 */
	public void setDateTimeFormatter(Class<? extends DateTimeFormatter> dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	/**
	 * 返回请求参数格式化为字符串
	 *
	 * @return 请求参数格式化为字符串
	 */
	public Class<? extends MapFormatter> getRequestParametersFormatter() {
		return requestParametersFormatter;
	}

	/**
	 * 设置请求参数格式化为字符串
	 *
	 * @param requestParametersFormatter
	 * 		请求参数格式化为字符串
	 */
	public void setRequestParametersFormatter(Class<? extends MapFormatter> requestParametersFormatter) {
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
	public Class<? extends MapFormatter> getExtraFormatter() {
		return extraFormatter;
	}

	/**
	 * 设置附加参数格式化为字符串
	 *
	 * @param extraFormatter
	 * 		附加参数格式化为字符串
	 */
	public void setExtraFormatter(Class<? extends MapFormatter> extraFormatter) {
		this.extraFormatter = extraFormatter;
	}

}
