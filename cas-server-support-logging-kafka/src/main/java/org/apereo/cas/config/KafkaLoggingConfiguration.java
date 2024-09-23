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

import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.kafka.spring.config.AbstractKafkaConfiguration;
import com.buession.logging.kafka.spring.config.AbstractKafkaLogHandlerConfiguration;
import com.buession.logging.kafka.spring.config.KafkaConfigurer;
import org.apereo.cas.configuration.model.support.logging.KafkaLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.util.spring.DirectObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Kafka 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(KafkaLogHandlerFactoryBean.class)
public class KafkaLoggingConfiguration extends AbstractLogHandlerConfiguration {

	public KafkaLoggingConfiguration(final ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	static class KafkaLogHandlerConfiguration extends AbstractKafkaLogHandlerConfiguration {

		private final List<KafkaLoggingProperties> kafkaLoggingProperties;

		public KafkaLogHandlerConfiguration(final LoggingProperties properties) {
			this.kafkaLoggingProperties = properties.getKafka();
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<KafkaLogHandlerFactoryBean> logHandlerFactoryBean() {
			return kafkaLoggingProperties.stream().map((properties)->{
				final KafkaConfiguration kafkaConfiguration = new KafkaConfiguration(properties);
				final KafkaTemplate<String, Object> kafkaTemplate = kafkaConfiguration.kafkaTemplate();

				final KafkaLogHandlerFactoryBean kafkaLogHandlerFactoryBean = super.logHandlerFactoryBean(
						kafkaTemplate);

				kafkaLogHandlerFactoryBean.setTopic(properties.getTopic());

				return kafkaLogHandlerFactoryBean;
			}).collect(Collectors.toList());
		}

		@Override
		protected String getTopic() {
			return "";
		}

	}

	static class KafkaConfiguration extends AbstractKafkaConfiguration {

		private final KafkaLoggingProperties kafkaLoggingProperties;

		public KafkaConfiguration(final KafkaLoggingProperties kafkaLoggingProperties) {
			this.kafkaLoggingProperties = kafkaLoggingProperties;
		}

		public KafkaTemplate<String, Object> kafkaTemplate() {
			final KafkaConfigurer kafkaConfigurer = kafkaConfigurer();
			final ProducerFactory<String, Object> producerFactory = super.producerFactory(kafkaConfigurer,
					new DirectObjectProvider<>(pf->{

					}));
			final ProducerListener<String, Object> kafkaProducerListener = super.kafkaProducerListener();

			return kafkaTemplate(kafkaConfigurer, producerFactory, kafkaProducerListener);
		}

		private KafkaConfigurer kafkaConfigurer() {
			final KafkaConfigurer configurer = new KafkaConfigurer();

			configurer.setBootstrapServers(kafkaLoggingProperties.getBootstrapServers());
			configurer.setConfigs(kafkaLoggingProperties.buildProperties());
			configurer.setTransactionIdPrefix(kafkaLoggingProperties.getTransactionIdPrefix());

			return configurer;
		}

	}

}
