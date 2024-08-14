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
package org.apereo.cas.logging.autoconfigure.mongo;

import com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean;
import org.apereo.cas.authentication.CasSSLContext;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.HistoryMongoLogProperties;
import org.apereo.cas.mongo.CasMongoOperations;
import org.apereo.cas.mongo.MongoDbConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnClass(name = {"com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean"})
public class MongoLogHandlerConfiguration extends AbstractLogHandlerConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
	@ConditionalOnProperty(prefix = History.PREFIX, name = "mongo.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = History.LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractHistoryLogHandlerConfiguration<HistoryMongoLogProperties> {

		public History(CasLoggingConfigurationProperties logProperties) {
			super(logProperties.getHistory().getMongo());
		}

		@Bean(name = "historyLoggingMongoDbConnectionFactory")
		public MongoDbConnectionFactory mongoDbConnectionFactory(
				@Qualifier("casSslContext") final CasSSLContext casSslContext) {
			return new MongoDbConnectionFactory(casSslContext.getSslContext());
		}

		@Bean(name = "historyLoggingMongoTemplate")
		public CasMongoOperations mongoTemplateFactoryBean(
				@Qualifier("historyLoggingMongoDbConnectionFactory") ObjectProvider<MongoDbConnectionFactory> mongoDbConnectionFactory) {
			CasMongoOperations mongoTemplate = mongoDbConnectionFactory.getIfAvailable()
					.buildMongoTemplate(handlerProperties);

			MongoDbConnectionFactory.createCollection(mongoTemplate, handlerProperties.getCollection(),
					handlerProperties.isDropCollection());

			return mongoTemplate;
		}

		@Bean(name = History.LOG_HANDLER_BEAN_NAME)
		public MongoLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("historyLoggingMongoTemplate") ObjectProvider<MongoTemplate> mongoTemplate) {
			final MongoLogHandlerFactoryBean logHandlerFactoryBean = new MongoLogHandlerFactoryBean();

			mongoTemplate.ifAvailable(logHandlerFactoryBean::setMongoTemplate);

			propertyMapper.from(handlerProperties::getCollection).to(logHandlerFactoryBean::setCollectionName);

			return logHandlerFactoryBean;
		}

	}

}
