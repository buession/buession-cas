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
package org.apereo.cas.logging.autoconfigure.rabbit;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.rabbitmq.spring.ConnectionFactoryBean;
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.rabbitmq.spring.RabbitTemplateFactoryBean;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.HistoryRabbitLogProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({RabbitLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = RabbitLogHandlerConfiguration.PREFIX, name = "rabbit.enabled", havingValue = "true")
public class RabbitLogHandlerConfiguration extends AbstractLogHandlerConfiguration<HistoryRabbitLogProperties> {

	public RabbitLogHandlerConfiguration(CasLoggingConfigurationProperties logProperties) {
		super(logProperties.getHistory().getRabbit());
	}

	@Bean(name = "logRabbitConnectionFactory")
	public ConnectionFactoryBean connectionFactoryBean() {
		final ConnectionFactoryBean connectionFactoryBean = new ConnectionFactoryBean();

		propertyMapper.from(handlerProperties::getHost).to(connectionFactoryBean::setHost);
		propertyMapper.from(handlerProperties::getPort).to(connectionFactoryBean::setPort);
		propertyMapper.from(handlerProperties::getVirtualHost).to(connectionFactoryBean::setVirtualHost);
		propertyMapper.from(handlerProperties::getUsername).to(connectionFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(connectionFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getRequestedHeartbeat).to(connectionFactoryBean::setRequestedHeartbeat);
		propertyMapper.from(handlerProperties::getRequestedChannelMax)
				.to(connectionFactoryBean::setRequestedChannelMax);
		propertyMapper.from(handlerProperties::getPublisherConfirmType)
				.to(connectionFactoryBean::setPublisherConfirmType);
		propertyMapper.from(handlerProperties::getConnectionTimeout).to(connectionFactoryBean::setConnectionTimeout);
		propertyMapper.from(handlerProperties::getSslConfiguration).to(connectionFactoryBean::setSslConfiguration);
		propertyMapper.from(handlerProperties::getCache).to(connectionFactoryBean::setCache);

		return connectionFactoryBean;
	}

	@Bean(name = "logRabbitRabbitTemplate")
	public RabbitTemplateFactoryBean rabbitTemplateFactoryBean(
			@Qualifier("logRabbitConnectionFactory") ObjectProvider<org.springframework.amqp.rabbit.connection.ConnectionFactory> connectionFactory) {
		final RabbitTemplateFactoryBean rabbitTemplateFactoryBean = new RabbitTemplateFactoryBean();

		connectionFactory.ifUnique(rabbitTemplateFactoryBean::setConnectionFactory);
		propertyMapper.from(handlerProperties::getTemplate).to(rabbitTemplateFactoryBean::setTemplate);
		propertyMapper.from(handlerProperties::isPublisherReturns).to(rabbitTemplateFactoryBean::setPublisherReturns);

		return rabbitTemplateFactoryBean;
	}

	@Bean
	public RabbitLogHandlerFactoryBean logHandlerFactoryBean(@Qualifier("logRabbitRabbitTemplate")
																	 ObjectProvider<RabbitTemplate> rabbitTemplate) {
		final RabbitLogHandlerFactoryBean logHandlerFactoryBean = new RabbitLogHandlerFactoryBean();

		rabbitTemplate.ifUnique(logHandlerFactoryBean::setRabbitTemplate);
		logHandlerFactoryBean.setExchange(handlerProperties.getExchange());
		logHandlerFactoryBean.setRoutingKey(handlerProperties.getRoutingKey());

		return logHandlerFactoryBean;
	}

}
