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
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.rabbitmq.spring.config.AbstractRabbitConfiguration;
import com.buession.logging.rabbitmq.spring.config.AbstractRabbitLogHandlerConfiguration;
import com.buession.logging.rabbitmq.spring.config.RabbitConfigurer;
import com.buession.logging.rabbitmq.spring.config.RabbitLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.RabbitLoggingProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RabbitMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(RabbitLogHandlerFactoryBean.class)
public class RabbitLoggingConfiguration {

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	static class RabbitLogHandlerConfiguration extends AbstractRabbitLogHandlerConfiguration {

		private final List<RabbitLoggingProperties> rabbitLoggingProperties;

		public RabbitLogHandlerConfiguration(final LoggingProperties properties) {
			this.rabbitLoggingProperties = properties.getRabbit();
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<RabbitLogHandlerFactoryBean> logHandlerFactoryBean() {
			return rabbitLoggingProperties.stream().map((properties)->{
				final RabbitLogHandlerFactoryBeanConfigurer configurer = rabbitLogHandlerFactoryBeanConfigurer(
						properties);
				final RabbitConfiguration rabbitConfiguration = new RabbitConfiguration(properties);
				final RabbitTemplate rabbitTemplate;

				try{
					rabbitTemplate = rabbitConfiguration.rabbitTemplate();
				}catch(Exception e){
					throw new BeanInstantiationException(RabbitTemplate.class, e.getMessage(), e);
				}

				return super.logHandlerFactoryBean(configurer, rabbitTemplate);
			}).collect(Collectors.toList());
		}

		private RabbitLogHandlerFactoryBeanConfigurer rabbitLogHandlerFactoryBeanConfigurer(
				final RabbitLoggingProperties rabbitLoggingProperties) {
			final RabbitLogHandlerFactoryBeanConfigurer configurer = new RabbitLogHandlerFactoryBeanConfigurer();

			configurer.setExchange(rabbitLoggingProperties.getExchange());
			configurer.setRoutingKey(rabbitLoggingProperties.getRoutingKey());

			return configurer;
		}

	}

	static class RabbitConfiguration extends AbstractRabbitConfiguration {

		private final RabbitLoggingProperties rabbitLoggingProperties;

		public RabbitConfiguration(final RabbitLoggingProperties rabbitLoggingProperties) {
			this.rabbitLoggingProperties = rabbitLoggingProperties;
		}

		public RabbitTemplate rabbitTemplate() throws Exception {
			final RabbitConfigurer configurer = rabbitConfigurer();
			final ConnectionFactory connectionFactory = rabbitConnectionFactory(configurer);

			return super.rabbitTemplate(configurer, connectionFactory);
		}

		private RabbitConfigurer rabbitConfigurer() {
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
					MessageConverter messageConverter = (MessageConverter) BeanUtils.instantiateClass(
							Class.forName(rabbitLoggingProperties.getMessageConverterClass()));
					configurer.setMessageConverter(messageConverter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(MessageConverter.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

		@Override
		protected boolean determineMandatoryFlag() {
			Boolean mandatory = rabbitLoggingProperties.getMandatory();
			if(mandatory != null){
				return mandatory;
			}

			return rabbitLoggingProperties.isPublisherReturns();
		}

		private static int determinePort(final RabbitLoggingProperties rabbitLoggingProperties) {
			if(rabbitLoggingProperties.getPort() > 0){
				return rabbitLoggingProperties.getPort();
			}

			return rabbitLoggingProperties.getSslConfiguration() != null &&
					rabbitLoggingProperties.getSslConfiguration()
							.isEnabled() ? com.buession.logging.rabbitmq.core.Constants.DEFAULT_SECURE_PORT :
					com.buession.logging.rabbitmq.core.Constants.DEFAULT_PORT;
		}

	}

}
