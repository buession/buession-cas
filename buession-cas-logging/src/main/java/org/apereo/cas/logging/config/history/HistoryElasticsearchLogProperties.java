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

import com.buession.logging.elasticsearch.spring.RestHighLevelClientFactory;
import com.buession.logging.support.config.HandlerProperties;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 * Elasticsearch 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryElasticsearchLogProperties implements HandlerProperties, Serializable {

	private final static long serialVersionUID = 4759244909748729352L;

	/**
	 * Elasticsearch URL 地址
	 */
	private List<String> urls = RestHighLevelClientFactory.DEFAULT_URLS;

	/**
	 * Elasticsearch 地址
	 */
	private String host;

	/**
	 * Elasticsearch 端口
	 */
	private int port = RestHighLevelClientFactory.DEFAULT_PORT;

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
	private Duration connectionTimeout = RestHighLevelClientFactory.DEFAULT_CONNECTION_TIMEOUT;

	/**
	 * 读取超时
	 */
	private Duration readTimeout = RestHighLevelClientFactory.DEFAULT_READ_TIMEOUT;

	/**
	 * 索引名称
	 */
	private String indexName;

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
	 * 返回 Elasticsearch 地址
	 *
	 * @return Elasticsearch 地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 Elasticsearch 地址
	 *
	 * @param host
	 * 		Elasticsearch 地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 Elasticsearch 端口
	 *
	 * @return Elasticsearch 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 Elasticsearch 端口
	 *
	 * @param port
	 * 		Elasticsearch 端口
	 */
	public void setPort(int port) {
		this.port = port;
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

}
