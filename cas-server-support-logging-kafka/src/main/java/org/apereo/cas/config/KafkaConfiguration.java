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

import com.buession.logging.kafka.ProducerFactoryCustomizer;
import com.buession.logging.kafka.spring.config.AbstractKafkaConfiguration;
import com.buession.logging.kafka.spring.config.KafkaConfigurer;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.KafkaLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
public class KafkaConfiguration {

	protected static KafkaConfigurer kafkaConfigurer(final KafkaLoggingProperties kafkaLoggingProperties) {
		final KafkaConfigurer configurer = new KafkaConfigurer();

		configurer.setBootstrapServers(kafkaLoggingProperties.getBootstrapServers());
		configurer.setConfigs(kafkaLoggingProperties.buildProperties());
		configurer.setTransactionIdPrefix(kafkaLoggingProperties.getTransactionIdPrefix());

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "kafka.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractKafkaConfiguration {

		private final KafkaLoggingProperties kafkaLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.kafkaLoggingProperties = properties.getBasic().getKafka();
		}

		@Bean(name = "casBasicLoggingKafkaConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaConfigurer kafkaConfigurer() {
			return KafkaConfiguration.kafkaConfigurer(kafkaLoggingProperties);
		}

		@Bean(name = "casBasicLoggingKafkaProducerFactory")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ProducerFactory<String, Object> producerFactory(
				@Qualifier("casBasicLoggingKafkaConfigurer") KafkaConfigurer configurer,
				@Qualifier("casBasicLoggingKafkaProducerFactoryCustomizer") ObjectProvider<ProducerFactoryCustomizer> producerFactoryCustomizers) {
			return super.producerFactory(configurer, producerFactoryCustomizers);
		}

		@Bean(name = "casBasicLoggingKafkaProducerListener")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public LoggingProducerListener<String, Object> kafkaProducerListener() {
			return super.kafkaProducerListener();
		}

		@Bean(name = "casBasicLoggingKafkaTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public KafkaTemplate<String, Object> kafkaTemplate(
				@Qualifier("casBasicLoggingKafkaConfigurer") KafkaConfigurer configurer,
				@Qualifier("casBasicLoggingKafkaProducerFactory")
				ProducerFactory<String, Object> producerFactory,
				@Qualifier("casBasicLoggingKafkaProducerListener") ProducerListener<String, Object> kafkaProducerListener) {
			return super.kafkaTemplate(configurer, producerFactory, kafkaProducerListener);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "kafka.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractKafkaConfiguration {

		private final KafkaLoggingProperties kafkaLoggingProperties;

		public History(final LoggingProperties properties) {
			this.kafkaLoggingProperties = properties.getHistory().getKafka();
		}

		@Bean(name = "casHistoryLoggingKafkaConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public KafkaConfigurer kafkaConfigurer() {
			return KafkaConfiguration.kafkaConfigurer(kafkaLoggingProperties);
		}

		@Bean(name = "casHistoryLoggingKafkaProducerFactory")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ProducerFactory<String, Object> producerFactory(
				@Qualifier("casHistoryLoggingKafkaConfigurer") KafkaConfigurer configurer,
				@Qualifier("casHistoryLoggingKafkaProducerFactoryCustomizer") ObjectProvider<ProducerFactoryCustomizer> producerFactoryCustomizers) {
			return super.producerFactory(configurer, producerFactoryCustomizers);
		}

		@Bean(name = "casHistoryLoggingKafkaProducerListener")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public LoggingProducerListener<String, Object> kafkaProducerListener() {
			return super.kafkaProducerListener();
		}

		@Bean(name = "casHistoryLoggingKafkaTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public KafkaTemplate<String, Object> kafkaTemplate(
				@Qualifier("casHistoryLoggingKafkaConfigurer") KafkaConfigurer configurer,
				@Qualifier("casHistoryLoggingKafkaProducerFactory")
				ProducerFactory<String, Object> producerFactory,
				@Qualifier("casHistoryLoggingKafkaProducerListener") ProducerListener<String, Object> kafkaProducerListener) {
			return super.kafkaTemplate(configurer, producerFactory, kafkaProducerListener);
		}

	}

}
