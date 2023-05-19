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

import org.apereo.cas.configuration.model.support.mongo.SingleCollectionMongoDbProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.apereo.cas.logging.manager.MongoDbHistoryLoginLoggingManager;
import org.apereo.cas.mongo.MongoDbConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.net.ssl.SSLContext;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
@EnableTransactionManagement(proxyTargetClass = true)
public class MongoDbLoginLoggingConfiguration {

	abstract static class AbstractMongoDbLoginLoggingConfiguration {

		protected final CasLoggingConfigurationProperties loggingConfigurationProperties;

		protected final SingleCollectionMongoDbProperties mongoDbProperties;

		public AbstractMongoDbLoginLoggingConfiguration(
				CasLoggingConfigurationProperties loggingConfigurationProperties,
				SingleCollectionMongoDbProperties mongoDbProperties){
			this.loggingConfigurationProperties = loggingConfigurationProperties;
			this.mongoDbProperties = mongoDbProperties;
		}

		protected MongoTemplate createMongoTemplate(final SSLContext sslContext){
			MongoDbConnectionFactory factory = new MongoDbConnectionFactory(sslContext);
			MongoTemplate mongoTemplate = factory.buildMongoTemplate(mongoDbProperties);
			MongoDbConnectionFactory.createCollection(mongoTemplate, mongoDbProperties.getCollection(),
					mongoDbProperties.isDropCollection());

			return mongoTemplate;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "basic.mongo.collection")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME)
	static class BasicMongoDbLoginLoggingConfiguration extends AbstractMongoDbLoginLoggingConfiguration {

		public BasicMongoDbLoginLoggingConfiguration(CasLoggingConfigurationProperties loggingConfigurationProperties){
			super(loggingConfigurationProperties, loggingConfigurationProperties.getBasic().getMongo());
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME)
		public MongoTemplate basicLoginLoggingMongoTemplate(@Qualifier("sslContext") SSLContext sslContext){
			return createMongoTemplate(sslContext);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "history.mongo.collection")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class HistoryMongoDbLoginLoggingConfiguration extends AbstractMongoDbLoginLoggingConfiguration {

		public HistoryMongoDbLoginLoggingConfiguration(
				CasLoggingConfigurationProperties loggingConfigurationProperties){
			super(loggingConfigurationProperties, loggingConfigurationProperties.getHistory().getMongo());
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME)
		public MongoTemplate historyLoginLoggingMongoTemplate(@Qualifier("sslContext") SSLContext sslContext){
			return createMongoTemplate(sslContext);
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		@ConditionalOnBean(name = Constants.HISTORY_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME)
		public HistoryLoginLoggingManager historyLoginLoggingManager(
				@Qualifier(Constants.HISTORY_LOGIN_LOGGING_MONGO_TEMPLATE_BEAN_NAME) ObjectProvider<MongoTemplate> mongoTemplate){
			return new MongoDbHistoryLoginLoggingManager(mongoTemplate.getIfAvailable(),
					loggingConfigurationProperties.getHistory().getMongo());
		}

	}

}
