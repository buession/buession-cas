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

import com.buession.logging.core.SslConfiguration;
import com.buession.logging.rabbitmq.core.Cache;
import com.buession.logging.rabbitmq.core.Retry;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.Serializable;
import java.time.Duration;

/**
 * RabbitMQ 日志适配器配置
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@JsonFilter("RabbitLoggingProperties")
public class RabbitLoggingProperties implements AdapterLoggingProperties, Serializable {

	private final static long serialVersionUID = 7838178327531884281L;

	/**
	 * RabbitMQ 地址
	 */
	@RequiredProperty
	private String host = "localhost";

	/**
	 * RabbitMQ 端口
	 */
	private int port = 5672;

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
	@RequiredProperty
	private String virtualHost = "/";

	/**
	 * Exchange 名称
	 */
	private String exchange;

	/**
	 * Routing key 名称
	 */
	private String routingKey;

	/**
	 * 连接超时
	 */
	private Duration connectionTimeout = Duration.ofSeconds(1);

	/**
	 * Continuation timeout for RPC calls in channels. Set it to zero to wait forever.
	 *
	 * @since 1.0.0
	 */
	private Duration channelRpcTimeout = Duration.ofMinutes(10);

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
	 * SSL 配置
	 */
	private SslConfiguration sslConfiguration = new SslConfiguration();

	/**
	 * Whether to enable publisher returns.
	 */
	private boolean publisherReturns;

	/**
	 * Type of publisher confirms to use.
	 */
	private CachingConnectionFactory.ConfirmType publisherConfirmType;

	/**
	 * Timeout for `receive()` operations.
	 *
	 * @since 1.0.0
	 */
	private Duration receiveTimeout;

	/**
	 * Timeout for `sendAndReceive()` operations.
	 *
	 * @since 1.0.0
	 */
	private Duration replyTimeout;

	/**
	 * Whether to enable mandatory messages.
	 *
	 * @since 1.0.0
	 */
	private Boolean mandatory;

	/**
	 * Name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @since 1.0.0
	 */
	private String defaultReceiveQueue;

	/**
	 * 消息转换器
	 *
	 * @since 1.0.0
	 */
	private Class<? extends MessageConverter> messageConverter;

	/**
	 * 缓存配置
	 */
	private Cache cache = new Cache();

	/**
	 * 重试配置
	 *
	 * @since 1.0.0
	 */
	private Retry retry = new Retry();

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
	 * 返回 Exchange 名称
	 *
	 * @return Exchange 名称
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * 设置 Exchange 名称
	 *
	 * @param exchange
	 * 		Exchange 名称
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * 返回 Routing key 名称
	 *
	 * @return Routing key 名称
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * 设置 Routing key 名称
	 *
	 * @param routingKey
	 * 		Routing key 名称
	 */
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
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
	 * Return continuation timeout for RPC calls in channels. Set it to zero to wait forever.
	 *
	 * @return Continuation timeout for RPC calls in channels.
	 *
	 * @since 1.0.0
	 */
	public Duration getChannelRpcTimeout() {
		return channelRpcTimeout;
	}

	/**
	 * Sets continuation timeout for RPC calls in channels. Set it to zero to wait forever.
	 *
	 * @param channelRpcTimeout
	 * 		Continuation timeout for RPC calls in channels.
	 *
	 * @since 1.0.0
	 */
	public void setChannelRpcTimeout(Duration channelRpcTimeout) {
		this.channelRpcTimeout = channelRpcTimeout;
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
	public void setPublisherConfirmType(CachingConnectionFactory.ConfirmType publisherConfirmType) {
		this.publisherConfirmType = publisherConfirmType;
	}

	/**
	 * Return timeout for `receive()` operations.
	 *
	 * @return Timeout for `receive()` operations.
	 *
	 * @since 1.0.0
	 */
	public Duration getReceiveTimeout() {
		return this.receiveTimeout;
	}

	/**
	 * Sets timeout for `receive()` operations.
	 *
	 * @param receiveTimeout
	 * 		Timeout for `receive()` operations.
	 *
	 * @since 1.0.0
	 */
	public void setReceiveTimeout(Duration receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	/**
	 * Return timeout for `sendAndReceive()` operations.
	 *
	 * @return Timeout for `sendAndReceive()` operations.
	 *
	 * @since 1.0.0
	 */
	public Duration getReplyTimeout() {
		return this.replyTimeout;
	}

	/**
	 * Sets timeout for `sendAndReceive()` operations.
	 *
	 * @param replyTimeout
	 * 		Timeout for `sendAndReceive()` operations.
	 *
	 * @since 1.0.0
	 */
	public void setReplyTimeout(Duration replyTimeout) {
		this.replyTimeout = replyTimeout;
	}

	/**
	 * Return Whether to enable mandatory messages.
	 *
	 * @return true / false
	 *
	 * @since 1.0.0
	 */
	public Boolean getMandatory() {
		return this.mandatory;
	}

	/**
	 * Sets enable mandatory messages.
	 *
	 * @param mandatory
	 * 		Whether to enable mandatory messages.
	 *
	 * @since 1.0.0
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * Return Name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @return Name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @since 1.0.0
	 */
	public String getDefaultReceiveQueue() {
		return this.defaultReceiveQueue;
	}

	/**
	 * Sets name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @param defaultReceiveQueue
	 * 		Name of the default queue to receive messages from when none is specified explicitly.
	 *
	 * @since 1.0.0
	 */
	public void setDefaultReceiveQueue(String defaultReceiveQueue) {
		this.defaultReceiveQueue = defaultReceiveQueue;
	}

	/**
	 * 返回消息转换器
	 *
	 * @return 消息转换器
	 */
	public Class<? extends MessageConverter> getMessageConverter() {
		return messageConverter;
	}

	/**
	 * 设置消息转换器
	 *
	 * @param messageConverter
	 * 		消息转换器
	 */
	public void setMessageConverter(Class<? extends MessageConverter> messageConverter) {
		this.messageConverter = messageConverter;
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
	 * 返回重试配置
	 *
	 * @return 重试配置
	 *
	 * @since 1.0.0
	 */
	public Retry getRetry() {
		return retry;
	}

	/**
	 * 设置重试配置
	 *
	 * @param retry
	 * 		重试配置
	 *
	 * @since 1.0.0
	 */
	public void setRetry(Retry retry) {
		this.retry = retry;
	}

}
