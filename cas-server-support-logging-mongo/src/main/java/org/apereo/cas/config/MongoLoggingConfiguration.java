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
import com.buession.logging.mongodb.spring.config.AbstractMongoConfiguration;
import com.buession.logging.mongodb.spring.config.AbstractMongoLogHandlerConfiguration;
import com.buession.logging.mongodb.spring.config.MongoConfigurer;
import org.apereo.cas.authentication.CasSSLContext;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.MongoLoggingProperties;
import org.apereo.cas.mongo.MongoDbConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Collectors;

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
	static class MongoLogHandlerConfiguration extends AbstractMongoLogHandlerConfiguration {

		private final List<MongoLoggingProperties> mongoLoggingProperties;

		private final CasSSLContext casSslContext;

		public MongoLogHandlerConfiguration(final LoggingProperties properties,
											@Qualifier("casSslContext") final CasSSLContext casSslContext) {
			this.mongoLoggingProperties = properties.getMongo();
			this.casSslContext = casSslContext;
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<MongoLogHandlerFactoryBean> logHandlerFactoryBean() {
			return mongoLoggingProperties.stream().map((properties)->{
				final MongoConfiguration mongoConfiguration = new MongoConfiguration(properties, casSslContext);
				final MongoTemplate mongoTemplate = mongoConfiguration.mongoTemplate();

				final MongoLogHandlerFactoryBean mongoLogHandlerFactoryBean =
						super.logHandlerFactoryBean(mongoTemplate);

				mongoLogHandlerFactoryBean.setCollectionName(properties.getCollection());

				return mongoLogHandlerFactoryBean;
			}).collect(Collectors.toList());
		}

		@Override
		protected String getCollectionName() {
			return "";
		}

	}

	static class MongoConfiguration extends AbstractMongoConfiguration {

		private final MongoLoggingProperties mongoLoggingProperties;

		private final CasSSLContext casSslContext;

		public MongoConfiguration(final MongoLoggingProperties mongoLoggingProperties,
								  final CasSSLContext casSslContext) {
			super(new MongoConfigurer());
			this.mongoLoggingProperties = mongoLoggingProperties;
			this.casSslContext = casSslContext;
		}

		public MongoDbConnectionFactory mongoDbConnectionFactory() {
			return new MongoDbConnectionFactory(casSslContext.getSslContext());
		}

		public MongoTemplate mongoTemplate() {
			return (MongoTemplate) mongoDbConnectionFactory().buildMongoTemplate(mongoLoggingProperties);
		}

	}

}
