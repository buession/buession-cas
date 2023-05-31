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

import com.buession.logging.core.SslConfiguration;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.rabbitmq.core.Template;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

import java.io.Serializable;
import java.time.Duration;

/**
 * RabbitMQ 历史登录日志配置
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
public class HistoryRabbitLogProperties implements Serializable {

	private final static long serialVersionUID = 7838178327531884281L;

	/**
	 * RabbitMQ 地址
	 */
	private String host = "localhost";

	/**
	 * RabbitMQ 端口
	 */
	private int port;

	/**
	 * 用户名
	 */
	private String username = "guest";

	/**
	 * 密码
	 */
	private String password = "guest";

	/**
	 * 虚拟机
	 */
	private String virtualHost;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout;

	/**
	 * SSL 配置
	 */
	private SslConfiguration sslConfiguration = new SslConfiguration();

	/**
	 * Requested heartbeat timeout; zero for none. If a duration suffix is not specified,
	 * seconds will be used.
	 */
	private Duration requestedHeartbeat;

	/**
	 * Number of channels per connection requested by the client. Use 0 for unlimited.
	 */
	private int requestedChannelMax = 2047;

	/**
	 * Whether to enable publisher returns.
	 */
	private boolean publisherReturns;

	/**
	 * Type of publisher confirms to use.
	 */
	private CachingConnectionFactory.ConfirmType publisherConfirmType;

	/**
	 * 缓存配置
	 */
	private Cache cache;

	/**
	 * Template 配置
	 */
	private Template template;

	/**
	 * 返回 RabbitMQ 地址
	 *
	 * @return RabbitMQ 地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置 RabbitMQ 地址
	 *
	 * @param host
	 * 		RabbitMQ 地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 返回 RabbitMQ 端口
	 *
	 * @return RabbitMQ 端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置 RabbitMQ 端口
	 *
	 * @param port
	 * 		RabbitMQ 端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回虚拟机
	 *
	 * @return 虚拟机
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * 设置虚拟机
	 *
	 * @param virtualHost
	 * 		虚拟机
	 */
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
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
	 * 返回 SSL 配置
	 *
	 * @return SSL 配置
	 */
	public SslConfiguration getSslConfiguration() {
		return sslConfiguration;
	}

	/**
	 * 设置 SSL 配置
	 *
	 * @param sslConfiguration
	 * 		SSL 配置
	 */
	public void setSslConfiguration(SslConfiguration sslConfiguration) {
		this.sslConfiguration = sslConfiguration;
	}

	/**
	 * Return requested heartbeat timeout.
	 *
	 * @return Requested heartbeat timeout.
	 */
	public Duration getRequestedHeartbeat() {
		return requestedHeartbeat;
	}

	/**
	 * Sets requested heartbeat timeout.
	 *
	 * @param requestedHeartbeat
	 * 		Requested heartbeat timeout
	 */
	public void setRequestedHeartbeat(Duration requestedHeartbeat) {
		this.requestedHeartbeat = requestedHeartbeat;
	}

	/**
	 * Return number of channels per connection requested by the client. Use 0 for unlimited.
	 *
	 * @return Number of channels per connection requested by the client.
	 */
	public int getRequestedChannelMax() {
		return requestedChannelMax;
	}

	/**
	 * Sets number of channels per connection requested by the client.
	 *
	 * @param requestedChannelMax
	 * 		Number of channels per connection requested by the client.
	 */
	public void setRequestedChannelMax(int requestedChannelMax) {
		this.requestedChannelMax = requestedChannelMax;
	}

	/**
	 * Return whether to enable publisher returns.
	 *
	 * @return Whether to enable publisher returns.
	 */
	public boolean isPublisherReturns() {
		return publisherReturns;
	}

	/**
	 * Sets enable publisher returns.
	 *
	 * @param publisherReturns
	 * 		Whether to enable publisher returns.
	 */
	public void setPublisherReturns(boolean publisherReturns) {
		this.publisherReturns = publisherReturns;
	}

	/**
	 * Return type of publisher confirms to use.
	 *
	 * @return Type of publisher confirms to use.
	 */
	public CachingConnectionFactory.ConfirmType getPublisherConfirmType() {
		return publisherConfirmType;
	}

	/**
	 * Sets type of publisher confirms to use.
	 *
	 * @param publisherConfirmType
	 * 		Type of publisher confirms to use.
	 */
	public void setPublisherConfirmType(
			CachingConnectionFactory.ConfirmType publisherConfirmType) {
		this.publisherConfirmType = publisherConfirmType;
	}

	/**
	 * 返回缓存配置
	 *
	 * @return 缓存配置
	 */
	public Cache getCache() {
		return cache;
	}

	/**
	 * 设置缓存配置
	 *
	 * @param cache
	 * 		缓存配置
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * 返回 Template 配置
	 *
	 * @return Template 配置
	 */
	public Template getTemplate() {
		return template;
	}

	/**
	 * 设置 Template 配置
	 *
	 * @param template
	 * 		Template 配置
	 */
	public void setTemplate(Template template) {
		this.template = template;
	}

}
