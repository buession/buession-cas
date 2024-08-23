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

import com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean;
import com.buession.logging.mongodb.spring.config.AbstractMongoLogHandlerConfiguration;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.MongoLoggingProperties;
import org.apereo.cas.logging.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * MongoDB 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(MongoLogHandlerFactoryBean.class)
public class MongoLoggingConfiguration {

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "mongo.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractMongoLogHandlerConfiguration {

		private final MongoLoggingProperties mongoLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.mongoLoggingProperties = properties.getBasic().getMongo();
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public MongoLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casBasicLoggingMongoTemplate") MongoTemplate mongoTemplate) {
			return super.logHandlerFactoryBean(mongoTemplate);
		}

		@Override
		protected String getCollectionName() {
			return mongoLoggingProperties.getCollection();
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "mongo.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractMongoLogHandlerConfiguration {

		private final MongoLoggingProperties mongoLoggingProperties;

		public History(final LoggingProperties properties) {
			this.mongoLoggingProperties = properties.getHistory().getMongo();
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public MongoLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casHistoryLoggingMongoTemplate") MongoTemplate mongoTemplate) {
			return super.logHandlerFactoryBean(mongoTemplate);
		}

		@Override
		protected String getCollectionName() {
			return mongoLoggingProperties.getCollection();
		}

	}

}
