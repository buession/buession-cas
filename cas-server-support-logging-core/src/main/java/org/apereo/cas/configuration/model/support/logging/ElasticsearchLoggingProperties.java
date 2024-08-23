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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.configuration.model.support.logging;

import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.buession.core.builder.ListBuilder;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.mapping.callback.EntityCallbacks;

import java.io.Serializable;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 日志适配器配置
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@JsonFilter("ElasticsearchLoggingProperties")
public class ElasticsearchLoggingProperties implements AdapterLoggingProperties, Serializable {

	private final static long serialVersionUID = 4759244909748729352L;

	/**
	 * Elasticsearch URL 地址
	 */
	@RequiredProperty
	private List<String> urls = ListBuilder.of("http://localhost:9200");

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout = Duration.ofMillis(RestClientBuilder.DEFAULT_CONNECT_TIMEOUT_MILLIS);

	/**
	 * 读取超时
	 */
	private Duration readTimeout = Duration.ofMillis(RestClientBuilder.DEFAULT_SOCKET_TIMEOUT_MILLIS);

	/**
	 * 路径前缀
	 *
	 * @since 1.0.0
	 */
	private String pathPrefix;

	/**
	 * 请求头
	 *
	 * @since 1.0.0
	 */
	private Map<String, String> headers = new LinkedHashMap<>();

	/**
	 * 请求参数
	 *
	 * @since 1.0.0
	 */
	private Map<String, String> parameters = new LinkedHashMap<>();

	/**
	 * 索引名称
	 */
	@RequiredProperty
	private String indexName;

	/**
	 * 是否自动创建索引
	 *
	 * @since 1.0.0
	 */
	private Boolean autoCreateIndex;

	/**
	 * JSONP Mapper {@link JsonpMapper}
	 *
	 * @since 1.0.0
	 */
	private Class<? extends JsonpMapper> jsonpMapper = JacksonJsonpMapper.class;

	/**
	 * 刷新策略
	 *
	 * @since 1.0.0
	 */
	private RefreshPolicy refreshPolicy;

	/**
	 * {@link EntityCallbacks}
	 *
	 * @since 1.0.0
	 */
	private Class<? extends EntityCallbacks> entityCallbacks;

	/**
	 * 返回 Elasticsearch URL 地址
	 *
	 * @return Elasticsearch URL 地址
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * 设置 Elasticsearch URL 地址
	 *
	 * @param urls
	 * 		Elasticsearch URL 地址
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * 返回 Elasticsearch 用户名
	 *
	 * @return Elasticsearch 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置 Elasticsearch 用户名
	 *
	 * @param username
	 * 		Elasticsearch 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回 Elasticsearch 密码
	 *
	 * @return Elasticsearch 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置 Elasticsearch 密码
	 *
	 * @param password
	 * 		Elasticsearch 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectionTimeout
	 * 		连接超时
	 */
	public void setConnectionTimeout(Duration connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * 返回读取超时
	 *
	 * @return 读取超时
	 */
	public Duration getReadTimeout() {
		return readTimeout;
	}

	/**
	 * 设置读取超时
	 *
	 * @param readTimeout
	 * 		读取超时
	 */
	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}

	/**
	 * 返回路径前缀
	 *
	 * @return 路径前缀
	 *
	 * @since 1.0.0
	 */
	public String getPathPrefix() {
		return pathPrefix;
	}

	/**
	 * 设置路径前缀
	 *
	 * @param pathPrefix
	 * 		路径前缀
	 *
	 * @since 1.0.0
	 */
	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	/**
	 * 返回请求头
	 *
	 * @return 请求头
	 *
	 * @since 1.0.0
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * 设置请求头
	 *
	 * @param headers
	 * 		请求头
	 *
	 * @since 1.0.0
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * 返回请求参数
	 *
	 * @return 请求参数
	 *
	 * @since 1.0.0
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * 设置请求参数
	 *
	 * @param parameters
	 * 		请求参数
	 *
	 * @since 1.0.0
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 返回索引名称
	 *
	 * @return 索引名称
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * 设置索引名称
	 *
	 * @param indexName
	 * 		索引名称
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * 返回是否自动创建索引
	 *
	 * @return 是否自动创建索引
	 */
	public Boolean isAutoCreateIndex() {
		return getAutoCreateIndex();
	}

	/**
	 * 返回是否自动创建索引
	 *
	 * @return 是否自动创建索引
	 */
	public Boolean getAutoCreateIndex() {
		return autoCreateIndex;
	}

	/**
	 * 设置是否自动创建索引
	 *
	 * @param autoCreateIndex
	 * 		是否自动创建索引
	 */
	public void setAutoCreateIndex(Boolean autoCreateIndex) {
		this.autoCreateIndex = autoCreateIndex;
	}

	/**
	 * 返回 JSONP Mapper {@link JsonpMapper}
	 *
	 * @return JSONP Mapper {@link JsonpMapper}
	 *
	 * @since 1.0.0
	 */
	public Class<? extends JsonpMapper> getJsonpMapper() {
		return jsonpMapper;
	}

	/**
	 * 设置 JSONP Mapper {@link JsonpMapper}
	 *
	 * @param jsonpMapper
	 * 		JSONP Mapper {@link JsonpMapper}
	 *
	 * @since 1.0.0
	 */
	public void setJsonpMapper(Class<? extends JsonpMapper> jsonpMapper) {
		this.jsonpMapper = jsonpMapper;
	}

	/**
	 * 返回刷新策略
	 *
	 * @return 刷新策略
	 *
	 * @since 1.0.0
	 */
	public RefreshPolicy getRefreshPolicy() {
		return refreshPolicy;
	}

	/**
	 * 设置刷新策略
	 *
	 * @param refreshPolicy
	 * 		刷新策略
	 *
	 * @since 1.0.0
	 */
	public void setRefreshPolicy(RefreshPolicy refreshPolicy) {
		this.refreshPolicy = refreshPolicy;
	}

	/**
	 * 返回 {@link EntityCallbacks}
	 *
	 * @return {@link EntityCallbacks}
	 *
	 * @since 1.0.0
	 */
	public Class<? extends EntityCallbacks> getEntityCallbacks() {
		return entityCallbacks;
	}

	/**
	 * 设置  {@link EntityCallbacks}
	 *
	 * @param entityCallbacks
	 *        {@link EntityCallbacks}
	 *
	 * @since 1.0.0
	 */
	public void setEntityCallbacks(Class<? extends EntityCallbacks> entityCallbacks) {
		this.entityCallbacks = entityCallbacks;
	}

}
