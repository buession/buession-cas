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
package org.apereo.cas.logging.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.kafka.config.SecurityConfiguration;
import com.buession.logging.kafka.config.SslConfiguration;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.mongodb.spring.MongoHandlerFactoryBean;
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;
import com.buession.logging.support.spring.BaseLogHandlerFactoryBean;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.*;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
@ConditionalOnMissingBean(LogHandler.class)
public class HistoryLoginLoggingLogHandlerConfiguration {

	private final static String PREFIX = CasLoggingConfigurationProperties.PREFIX + ".history";

	static abstract class AbstractLogHandler<T extends BaseLogHandlerFactoryBean<?>> {

		protected final CasLoggingConfigurationProperties.History historyProperties;

		protected final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		public AbstractLogHandler(
				CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			historyProperties = casLoggingConfigurationProperties.getHistory();
		}

		abstract T logHandler();

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "elasticsearch.enabled", havingValue = "true")
	@ConditionalOnClass(ElasticsearchLogHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class ElasticsearchLogHandler extends AbstractLogHandler<ElasticsearchLogHandlerFactoryBean> {

		public ElasticsearchLogHandler(
				CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean logHandler() {
			final com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean = new com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean();
			final HistoryElasticsearchLogProperties elasticsearchLogProperties = historyProperties.getElasticsearch();

			propertyMapper.from(elasticsearchLogProperties::getUrls).to(logHandlerFactoryBean::setUrls);
			propertyMapper.from(elasticsearchLogProperties::getHost).to(logHandlerFactoryBean::setHost);
			propertyMapper.from(elasticsearchLogProperties::getPort).to(logHandlerFactoryBean::setPort);
			propertyMapper.from(elasticsearchLogProperties::getUsername).to(logHandlerFactoryBean::setUsername);
			propertyMapper.from(elasticsearchLogProperties::getPassword).to(logHandlerFactoryBean::setPassword);
			propertyMapper.from(elasticsearchLogProperties::getConnectionTimeout)
					.to(logHandlerFactoryBean::setConnectionTimeout);
			propertyMapper.from(elasticsearchLogProperties::getReadTimeout).to(logHandlerFactoryBean::setReadTimeout);
			propertyMapper.from(elasticsearchLogProperties::getIndexName).to(logHandlerFactoryBean::setIndexName);

			return logHandlerFactoryBean;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnClass(JdbcLogHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class JdbcLogHandler extends AbstractLogHandler<JdbcLogHandlerFactoryBean> {

		public JdbcLogHandler(CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public JdbcLogHandlerFactoryBean logHandler() {
			final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();
			final HistoryJdbcLogProperties jdbcLogProperties = historyProperties.getJdbc();

			propertyMapper.from(jdbcLogProperties::getDriverClass).to(logHandlerFactoryBean::setDriverClassName);
			propertyMapper.from(jdbcLogProperties::getUrl).to(logHandlerFactoryBean::setUrl);
			propertyMapper.from(jdbcLogProperties::getUser).to(logHandlerFactoryBean::setUsername);
			propertyMapper.from(jdbcLogProperties::getPassword).to(logHandlerFactoryBean::setPassword);
			propertyMapper.from(jdbcLogProperties::getPoolConfiguration)
					.to(logHandlerFactoryBean::setPoolConfiguration);
			propertyMapper.from(jdbcLogProperties::getTableName).to(logHandlerFactoryBean::setTableName);
			propertyMapper.from(jdbcLogProperties::getField).to(logHandlerFactoryBean::setFieldConfiguration);
			propertyMapper.from(jdbcLogProperties::getIdGenerator).as(BeanUtils::instantiateClass)
					.to(logHandlerFactoryBean::setIdGenerator);
			propertyMapper.from(jdbcLogProperties::getDateTimeFormatter).as(BeanUtils::instantiateClass)
					.to(logHandlerFactoryBean::setDateTimeFormatter);
			propertyMapper.from(jdbcLogProperties::getRequestParametersFormatter)
					.as(BeanUtils::instantiateClass)
					.to(logHandlerFactoryBean::setRequestParametersFormatter);
			propertyMapper.from(jdbcLogProperties::getExtraFormatter).as(BeanUtils::instantiateClass)
					.to(logHandlerFactoryBean::setExtraFormatter);

			return logHandlerFactoryBean;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "kafka.enabled", havingValue = "true")
	@ConditionalOnClass(KafkaLogHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class KafkaLogHandler extends AbstractLogHandler<KafkaLogHandlerFactoryBean> {

		public KafkaLogHandler(CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public KafkaLogHandlerFactoryBean logHandler() {
			final KafkaLogHandlerFactoryBean logHandlerFactoryBean = new KafkaLogHandlerFactoryBean();
			final HistoryKafkaLogProperties kafkaLogProperties = historyProperties.getKafka();

			propertyMapper.from(kafkaLogProperties::getBootstrapServers).to(logHandlerFactoryBean::setBootstrapServers);
			propertyMapper.from(kafkaLogProperties::getClientId).to(logHandlerFactoryBean::setClientId);
			propertyMapper.from(kafkaLogProperties::getTopic).to(logHandlerFactoryBean::setTopic);
			propertyMapper.from(kafkaLogProperties::getTransactionIdPrefix)
					.to(logHandlerFactoryBean::setTransactionIdPrefix);
			propertyMapper.from(kafkaLogProperties::getAcks).to(logHandlerFactoryBean::setAcks);
			propertyMapper.from(kafkaLogProperties::getBatchSize).to(logHandlerFactoryBean::setBatchSize);
			propertyMapper.from(kafkaLogProperties::getBufferMemory).to(logHandlerFactoryBean::setBufferMemory);
			propertyMapper.from(kafkaLogProperties::getCompressionType).to(logHandlerFactoryBean::setCompressionType);
			propertyMapper.from(kafkaLogProperties::getRetries).to(logHandlerFactoryBean::setRetries);
			propertyMapper.from(kafkaLogProperties::getSsl).as(SslConfiguration::from)
					.to(logHandlerFactoryBean::setSslConfiguration);
			propertyMapper.from(kafkaLogProperties::getSecurity).as((security)->{
				final SecurityConfiguration securityConfiguration = new SecurityConfiguration();

				securityConfiguration.setProtocol(security.getProtocol());

				return securityConfiguration;
			}).to(logHandlerFactoryBean::setSecurityConfiguration);
			propertyMapper.from(kafkaLogProperties::getProperties).to(logHandlerFactoryBean::setProperties);

			return logHandlerFactoryBean;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "mongo.enabled", havingValue = "true")
	@ConditionalOnClass(MongoHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class MongoLogHandler extends AbstractLogHandler<MongoHandlerFactoryBean> {

		public MongoLogHandler(CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public MongoHandlerFactoryBean logHandler() {
			final MongoHandlerFactoryBean logHandlerFactoryBean = new MongoHandlerFactoryBean();
			final HistoryMongoLogProperties mongoLogProperties = historyProperties.getMongo();

			propertyMapper.from(mongoLogProperties::getHost).to(logHandlerFactoryBean::setHost);
			propertyMapper.from(mongoLogProperties::getPort).to(logHandlerFactoryBean::setPort);
			propertyMapper.from(mongoLogProperties::getUsername).to(logHandlerFactoryBean::setUsername);
			propertyMapper.from(mongoLogProperties::getPassword).to(logHandlerFactoryBean::setPassword);
			propertyMapper.from(mongoLogProperties::getUrl).to(logHandlerFactoryBean::setUrl);
			propertyMapper.from(mongoLogProperties::getReplicaSetName).to(logHandlerFactoryBean::setReplicaSetName);
			propertyMapper.from(mongoLogProperties::getDatabaseName).to(logHandlerFactoryBean::setDatabaseName);
			propertyMapper.from(mongoLogProperties::getAuthenticationDatabase)
					.to(logHandlerFactoryBean::setAuthenticationDatabase);
			propertyMapper.from(mongoLogProperties::getCollectionName).to(logHandlerFactoryBean::setCollectionName);
			propertyMapper.from(mongoLogProperties::getConnectionTimeout)
					.to(logHandlerFactoryBean::setConnectionTimeout);
			propertyMapper.from(mongoLogProperties::getReadTimeout).to(logHandlerFactoryBean::setReadTimeout);
			propertyMapper.from(mongoLogProperties::getUuidRepresentation)
					.to(logHandlerFactoryBean::setUuidRepresentation);
			propertyMapper.from(mongoLogProperties::getAutoIndexCreation)
					.to(logHandlerFactoryBean::setAutoIndexCreation);
			propertyMapper.from(mongoLogProperties::getFieldNamingStrategy)
					.to(logHandlerFactoryBean::setFieldNamingStrategy);
			propertyMapper.from(mongoLogProperties::getReadPreference).to(logHandlerFactoryBean::setReadPreference);
			propertyMapper.from(mongoLogProperties::getReadConcern).to(logHandlerFactoryBean::setReadConcern);
			propertyMapper.from(mongoLogProperties::getWriteConcern).to(logHandlerFactoryBean::setWriteConcern);
			propertyMapper.from(mongoLogProperties::getPool).to(logHandlerFactoryBean::setPoolConfiguration);

			return logHandlerFactoryBean;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "rabbit.enabled", havingValue = "true")
	@ConditionalOnClass(RabbitLogHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class RabbitLogHandler extends AbstractLogHandler<RabbitLogHandlerFactoryBean> {

		public RabbitLogHandler(CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public RabbitLogHandlerFactoryBean logHandler() {
			final RabbitLogHandlerFactoryBean logHandlerFactoryBean = new RabbitLogHandlerFactoryBean();
			final HistoryRabbitLogProperties rabbitLogProperties = historyProperties.getRabbit();

			propertyMapper.from(rabbitLogProperties::getHost).to(logHandlerFactoryBean::setHost);
			propertyMapper.from(rabbitLogProperties::getPort).to(logHandlerFactoryBean::setPort);
			propertyMapper.from(rabbitLogProperties::getUsername).to(logHandlerFactoryBean::setUsername);
			propertyMapper.from(rabbitLogProperties::getPassword).to(logHandlerFactoryBean::setPassword);
			propertyMapper.from(rabbitLogProperties::getVirtualHost).to(logHandlerFactoryBean::setVirtualHost);
			propertyMapper.from(rabbitLogProperties::getRequestedHeartbeat)
					.to(logHandlerFactoryBean::setRequestedHeartbeat);
			propertyMapper.from(rabbitLogProperties::getRequestedChannelMax)
					.to(logHandlerFactoryBean::setRequestedChannelMax);
			propertyMapper.from(rabbitLogProperties::isPublisherReturns).to(logHandlerFactoryBean::setPublisherReturns);
			propertyMapper.from(rabbitLogProperties::getPublisherConfirmType)
					.to(logHandlerFactoryBean::setPublisherConfirmType);
			propertyMapper.from(rabbitLogProperties::getConnectionTimeout)
					.to(logHandlerFactoryBean::setConnectionTimeout);
			propertyMapper.from(rabbitLogProperties::getSslConfiguration)
					.to(logHandlerFactoryBean::setSslConfiguration);
			propertyMapper.from(rabbitLogProperties::getCache).to(logHandlerFactoryBean::setCache);
			propertyMapper.from(rabbitLogProperties::getTemplate).to(logHandlerFactoryBean::setTemplate);
			propertyMapper.from(rabbitLogProperties::getCache).to(logHandlerFactoryBean::setCache);

			return logHandlerFactoryBean;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "rest.enabled", havingValue = "true")
	@ConditionalOnClass(RestLogHandlerFactoryBean.class)
	@ConditionalOnMissingBean(LogHandler.class)
	static class RestLogHandler extends AbstractLogHandler<RestLogHandlerFactoryBean> {

		public RestLogHandler(CasLoggingConfigurationProperties casLoggingConfigurationProperties) {
			super(casLoggingConfigurationProperties);
		}

		@Bean
		@Override
		public RestLogHandlerFactoryBean logHandler() {
			final RestLogHandlerFactoryBean logHandlerFactoryBean = new RestLogHandlerFactoryBean();
			final HistoryRestLogProperties restLogProperties = historyProperties.getRest();

			propertyMapper.from(restLogProperties::getUrl).to(logHandlerFactoryBean::setUrl);
			propertyMapper.from(restLogProperties::getRequestBodyBuilder).as(BeanUtils::instantiateClass)
					.to(logHandlerFactoryBean::setRequestBodyBuilder);
			propertyMapper.from(restLogProperties::getRequestMethod).to(logHandlerFactoryBean::setRequestMethod);

			return logHandlerFactoryBean;
		}

	}

}
