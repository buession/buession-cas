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
package org.apereo.cas.config;

import com.buession.core.validator.Validate;
import com.buession.logging.rabbitmq.spring.config.AbstractRabbitConfiguration;
import com.buession.logging.rabbitmq.spring.config.RabbitConfigurer;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.RabbitLoggingProperties;
import org.apereo.cas.logging.Constants;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
public class RabbitConfiguration {

	protected static RabbitConfigurer rabbitConfigurer(final RabbitLoggingProperties rabbitLoggingProperties) {
		final RabbitConfigurer configurer = new RabbitConfigurer();

		configurer.setHost(rabbitLoggingProperties.getHost());
		configurer.setPort(determinePort(rabbitLoggingProperties));
		configurer.setUsername(rabbitLoggingProperties.getUsername());
		configurer.setPassword(rabbitLoggingProperties.getPassword());
		configurer.setVirtualHost(rabbitLoggingProperties.getVirtualHost());
		configurer.setConnectionTimeout(rabbitLoggingProperties.getConnectionTimeout());
		configurer.setChannelRpcTimeout(rabbitLoggingProperties.getChannelRpcTimeout());
		configurer.setRequestedHeartbeat(rabbitLoggingProperties.getRequestedHeartbeat());
		configurer.setRequestedChannelMax(rabbitLoggingProperties.getRequestedChannelMax());
		configurer.setReceiveTimeout(rabbitLoggingProperties.getReceiveTimeout());
		configurer.setReplyTimeout(rabbitLoggingProperties.getReplyTimeout());
		configurer.setDefaultReceiveQueue(rabbitLoggingProperties.getDefaultReceiveQueue());
		configurer.setSslConfiguration(rabbitLoggingProperties.getSslConfiguration());
		configurer.setPublisherReturns(rabbitLoggingProperties.isPublisherReturns());
		configurer.setPublisherConfirmType(rabbitLoggingProperties.getPublisherConfirmType());

		if(rabbitLoggingProperties.getCache() != null){
			com.buession.logging.rabbitmq.core.Cache cache = new com.buession.logging.rabbitmq.core.Cache();

			if(rabbitLoggingProperties.getCache().getConnection() != null){
				com.buession.logging.rabbitmq.core.Cache.Connection connection =
						new com.buession.logging.rabbitmq.core.Cache.Connection();

				connection.setMode(rabbitLoggingProperties.getCache().getConnection().getMode());
				connection.setSize(rabbitLoggingProperties.getCache().getConnection().getSize());

				cache.setConnection(connection);
			}

			if(rabbitLoggingProperties.getCache().getChannel() != null){
				com.buession.logging.rabbitmq.core.Cache.Channel channel = new com.buession.logging.rabbitmq.core.Cache.Channel();

				channel.setSize(rabbitLoggingProperties.getCache().getChannel().getSize());
				channel.setCheckoutTimeout(rabbitLoggingProperties.getCache().getChannel().getCheckoutTimeout());

				cache.setChannel(channel);
			}

			configurer.setCache(cache);
		}

		if(rabbitLoggingProperties.getRetry() != null){
			com.buession.logging.rabbitmq.core.Retry retry = new com.buession.logging.rabbitmq.core.Retry();

			retry.setEnabled(rabbitLoggingProperties.getRetry().isEnabled());
			retry.setMaxAttempts(rabbitLoggingProperties.getRetry().getMaxAttempts());
			retry.setInitialInterval(rabbitLoggingProperties.getRetry().getInitialInterval());
			retry.setMultiplier(rabbitLoggingProperties.getRetry().getMultiplier());
			retry.setMaxInterval(rabbitLoggingProperties.getRetry().getMaxInterval());
			retry.setRetryCustomizers(rabbitLoggingProperties.getRetry().getRetryCustomizers());

			configurer.setRetry(retry);
		}

		if(Validate.hasText(rabbitLoggingProperties.getMessageConverterClass())){
			try{
				configurer.setMessageConverter(
						(MessageConverter) BeanUtils.instantiateClass(
								Class.forName(rabbitLoggingProperties.getMessageConverterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		return configurer;
	}

	protected static int determinePort(final RabbitLoggingProperties rabbitLoggingProperties) {
		if(rabbitLoggingProperties.getPort() > 0){
			return rabbitLoggingProperties.getPort();
		}

		return rabbitLoggingProperties.getSslConfiguration() != null &&
				rabbitLoggingProperties.getSslConfiguration()
						.isEnabled() ? com.buession.logging.rabbitmq.core.Constants.DEFAULT_SECURE_PORT :
				com.buession.logging.rabbitmq.core.Constants.DEFAULT_PORT;
	}

	protected static boolean determineMandatoryFlag(final RabbitLoggingProperties rabbitLoggingProperties) {
		Boolean mandatory = rabbitLoggingProperties.getMandatory();
		if(mandatory != null){
			return mandatory;
		}

		return rabbitLoggingProperties.isPublisherReturns();
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rabbit.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractRabbitConfiguration {

		private final RabbitLoggingProperties rabbitLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.rabbitLoggingProperties = properties.getBasic().getRabbit();
		}

		@Bean(name = "casBasicLoggingRabbitConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RabbitConfigurer rabbitConfigurer() {
			return RabbitConfiguration.rabbitConfigurer(rabbitLoggingProperties);
		}

		@Bean(name = "casBasicLoggingRabbitConnectionFactory")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConnectionFactory rabbitConnectionFactory(
				@Qualifier("casBasicLoggingRabbitConfigurer") RabbitConfigurer configurer) throws Exception {
			return super.rabbitConnectionFactory(configurer);
		}

		@Bean(name = "casBasicLoggingRabbitTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RabbitTemplate rabbitTemplate(
				@Qualifier("casBasicLoggingRabbitConfigurer") RabbitConfigurer configurer,
				@Qualifier("casBasicLoggingRabbitConnectionFactory") ConnectionFactory connectionFactory) {
			return super.rabbitTemplate(configurer, connectionFactory);
		}

		@Override
		protected boolean determineMandatoryFlag() {
			return RabbitConfiguration.determineMandatoryFlag(rabbitLoggingProperties);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rabbit.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractRabbitConfiguration {

		private final RabbitLoggingProperties rabbitLoggingProperties;

		public History(final LoggingProperties properties) {
			this.rabbitLoggingProperties = properties.getHistory().getRabbit();
		}

		@Bean(name = "casHistoryLoggingRabbitConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RabbitConfigurer rabbitConfigurer() {
			return RabbitConfiguration.rabbitConfigurer(rabbitLoggingProperties);
		}

		@Bean(name = "casHistoryLoggingRabbitConnectionFactory")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConnectionFactory rabbitConnectionFactory(
				@Qualifier("casHistoryLoggingRabbitConfigurer") RabbitConfigurer configurer) throws Exception {
			return super.rabbitConnectionFactory(configurer);
		}

		@Bean(name = "casHistoryLoggingRabbitTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RabbitTemplate rabbitTemplate(
				@Qualifier("casHistoryLoggingRabbitConfigurer") RabbitConfigurer configurer,
				@Qualifier("casHistoryLoggingRabbitConnectionFactory") ConnectionFactory connectionFactory) {
			return super.rabbitTemplate(configurer, connectionFactory);
		}

		@Override
		protected boolean determineMandatoryFlag() {
			return RabbitConfiguration.determineMandatoryFlag(rabbitLoggingProperties);
		}

	}

}
