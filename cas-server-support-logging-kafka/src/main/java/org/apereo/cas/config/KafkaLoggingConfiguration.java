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

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.kafka.spring.KafkaTemplateFactoryBean;
import com.buession.logging.kafka.spring.ProducerFactoryBean;
import org.apereo.cas.configuration.model.support.logging.KafkaLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.autoconfigure.BaseHandlerConfiguration;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * 控制台日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(KafkaLogHandlerFactoryBean.class)
public class KafkaLoggingConfiguration extends BaseHandlerConfiguration {

	private final static BeanCondition BASIC_CONDITION =
			BeanCondition.on(LoggingProperties.PREFIX + ".basic.kafka.bootstrapServers").evenIfMissing();

	private final static BeanCondition HISTORY_CONDITION =
			BeanCondition.on(LoggingProperties.PREFIX + ".history.kafka.bootstrapServers").evenIfMissing();

	private final static Logger logger = LoggerFactory.getLogger(KafkaLoggingConfiguration.class);

	public KafkaLoggingConfiguration(final ConfigurableApplicationContext applicationContext,
									 final LoggingProperties loggingProperties) {
		super(applicationContext, loggingProperties);
	}

	protected static ProducerFactoryBean createKafkaProducerFactoryBean(
			final ConfigurableApplicationContext applicationContext,
			final KafkaLoggingProperties kafkaLoggingProperties, final PropertyMapper propertyMapper,
			final BeanCondition beanCondition) {
		return BeanSupplier.of(ProducerFactoryBean.class)
				.when(beanCondition.given(applicationContext.getEnvironment()))
				.supply(()->{
					final ProducerFactoryBean producerFactoryBean = new ProducerFactoryBean();

					propertyMapper.from(kafkaLoggingProperties::getBootstrapServers)
							.to(producerFactoryBean::setBootstrapServers);
					propertyMapper.from(kafkaLoggingProperties::getClientId).to(producerFactoryBean::setClientId);
					propertyMapper.from(kafkaLoggingProperties::getTransactionIdPrefix)
							.to(producerFactoryBean::setTransactionIdPrefix);
					propertyMapper.from(kafkaLoggingProperties::getAcks).to(producerFactoryBean::setAcks);
					propertyMapper.from(kafkaLoggingProperties::getBatchSize).to(producerFactoryBean::setBatchSize);
					propertyMapper.from(kafkaLoggingProperties::getBufferMemory)
							.to(producerFactoryBean::setBufferMemory);
					propertyMapper.from(kafkaLoggingProperties::getCompressionType)
							.to(producerFactoryBean::setCompressionType);
					propertyMapper.from(kafkaLoggingProperties::getRetries).to(producerFactoryBean::setRetries);
					propertyMapper.from(kafkaLoggingProperties::getSslConfiguration)
							.to(producerFactoryBean::setSslConfiguration);
					propertyMapper.from(kafkaLoggingProperties::getSecurityConfiguration)
							.to(producerFactoryBean::setSecurityConfiguration);
					propertyMapper.from(kafkaLoggingProperties::getTransactionIdPrefix)
							.to(producerFactoryBean::setTransactionIdPrefix);
					propertyMapper.from(kafkaLoggingProperties::getProperties).to(producerFactoryBean::setProperties);

					return producerFactoryBean;
				})
				.otherwiseProxy()
				.get();
	}

	@SuppressWarnings({"unchecked"})
	protected static KafkaTemplateFactoryBean<String, Object> createKafkaTemplateFactoryBean(
			final ConfigurableApplicationContext applicationContext,
			final ProducerFactory<String, Object> producerFactory, final BeanCondition beanCondition) {
		return BeanSupplier.of(KafkaTemplateFactoryBean.class)
				.when(beanCondition.given(applicationContext.getEnvironment()))
				.supply(()->{
					final KafkaTemplateFactoryBean<String, Object> kafkaTemplateFactoryBean = new KafkaTemplateFactoryBean<>();

					kafkaTemplateFactoryBean.setProducerFactory(producerFactory);

					return kafkaTemplateFactoryBean;
				})
				.otherwiseProxy()
				.get();
	}

	protected static KafkaLogHandlerFactoryBean createKafkaLogHandlerFactoryBean(
			final ConfigurableApplicationContext applicationContext,
			final KafkaLoggingProperties kafkaLoggingProperties, final KafkaTemplate<String, Object> kafkaTemplate,
			final BeanCondition beanCondition) {
		return BeanSupplier.of(KafkaLogHandlerFactoryBean.class)
				.when(beanCondition.given(applicationContext.getEnvironment()))
				.supply(()->{
					final KafkaLogHandlerFactoryBean logHandlerFactoryBean = new KafkaLogHandlerFactoryBean();

					logHandlerFactoryBean.setKafkaTemplate(kafkaTemplate);
					logHandlerFactoryBean.setTopic(kafkaLoggingProperties.getTopic());

					return logHandlerFactoryBean;
				})
				.otherwiseProxy()
				.get();
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends BaseModeHandlerConfiguration<KafkaLoggingProperties> {

		public Basic(final ConfigurableApplicationContext applicationContext, final LoggingProperties properties) {
			super(applicationContext, properties.getBasic().getKafka());
		}

		@Bean(name = "casBasicLoggingKafkaProducerFactoryBean")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ProducerFactoryBean producerFactoryBean() {
			return createKafkaProducerFactoryBean(applicationContext, properties, propertyMapper, BASIC_CONDITION);
		}

		@Bean(name = "casBasicLoggingKafkaTemplateFactoryBean")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaTemplateFactoryBean<String, Object> kafkaTemplateFactoryBean(
				@Qualifier("casBasicLoggingKafkaProducerFactoryBean") ObjectProvider<ProducerFactory<String, Object>> producerFactory) {
			return createKafkaTemplateFactoryBean(applicationContext, producerFactory.getIfAvailable(),
					BASIC_CONDITION);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaLogHandlerFactoryBean basicKafkaLogHandlerFactoryBean(
				@Qualifier("casBasicLoggingKafkaTemplateFactoryBean") ObjectProvider<KafkaTemplate<String, Object>> kafkaTemplate) {
			return createKafkaLogHandlerFactoryBean(applicationContext, properties, kafkaTemplate.getIfAvailable(),
					BASIC_CONDITION);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends BaseModeHandlerConfiguration<KafkaLoggingProperties> {

		public History(final ConfigurableApplicationContext applicationContext, final LoggingProperties properties) {
			super(applicationContext, properties.getHistory().getKafka());
		}

		@Bean(name = "casHistoryLoggingKafkaProducerFactoryBean")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ProducerFactoryBean producerFactoryBean() {
			return createKafkaProducerFactoryBean(applicationContext, properties, propertyMapper, HISTORY_CONDITION);
		}

		@Bean(name = "casHistoryLoggingKafkaTemplateFactoryBean")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaTemplateFactoryBean<String, Object> kafkaTemplateFactoryBean(
				@Qualifier("casHistoryLoggingKafkaProducerFactoryBean") ObjectProvider<ProducerFactory<String, Object>> producerFactory) {
			return createKafkaTemplateFactoryBean(applicationContext, producerFactory.getIfAvailable(),
					HISTORY_CONDITION);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaLogHandlerFactoryBean historyKafkaLogHandlerFactoryBean(
				@Qualifier("casHistoryLoggingKafkaTemplateFactoryBean") ObjectProvider<KafkaTemplate<String, Object>> kafkaTemplate) {
			return createKafkaLogHandlerFactoryBean(applicationContext, properties, kafkaTemplate.getIfAvailable(),
					HISTORY_CONDITION);
		}

	}

}
