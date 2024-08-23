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

import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchLogHandlerConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.ElasticsearchLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(ElasticsearchLogHandlerFactoryBean.class)
public class ElasticsearchLoggingConfiguration extends AbstractLogHandlerConfiguration {

	public ElasticsearchLoggingConfiguration(ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	protected static ElasticsearchLogHandlerFactoryBeanConfigurer elasticsearchLogHandlerFactoryBeanConfigurer(
			final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
		final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = new ElasticsearchLogHandlerFactoryBeanConfigurer();

		configurer.setIndexName(elasticsearchLoggingProperties.getIndexName());
		configurer.setAutoCreateIndex(elasticsearchLoggingProperties.getAutoCreateIndex());

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractElasticsearchLogHandlerConfiguration {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.elasticsearchLoggingProperties = properties.getBasic().getElasticsearch();
		}

		@Bean(name = "casBasicLoggingElasticsearchLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ElasticsearchLogHandlerFactoryBeanConfigurer elasticsearchLogHandlerFactoryBeanConfigurer() {
			return ElasticsearchLoggingConfiguration.elasticsearchLogHandlerFactoryBeanConfigurer(
					elasticsearchLoggingProperties);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casBasicLoggingElasticsearchTemplate") ElasticsearchTemplate elasticsearchTemplate,
				@Qualifier("casBasicLoggingElasticsearchLogHandlerFactoryBeanConfigurer") ElasticsearchLogHandlerFactoryBeanConfigurer configurer) {
			return super.logHandlerFactoryBean(elasticsearchTemplate, configurer);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractElasticsearchLogHandlerConfiguration {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		public History(final LoggingProperties properties) {
			this.elasticsearchLoggingProperties = properties.getHistory().getElasticsearch();
		}

		@Bean(name = "casHistoryLoggingElasticsearchLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ElasticsearchLogHandlerFactoryBeanConfigurer elasticsearchLogHandlerFactoryBeanConfigurer() {
			return ElasticsearchLoggingConfiguration.elasticsearchLogHandlerFactoryBeanConfigurer(
					elasticsearchLoggingProperties);
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casHistoryLoggingElasticsearchTemplate") ElasticsearchTemplate elasticsearchTemplate,
				@Qualifier("casHistoryLoggingElasticsearchLogHandlerFactoryBeanConfigurer") ElasticsearchLogHandlerFactoryBeanConfigurer configurer) {
			return super.logHandlerFactoryBean(elasticsearchTemplate, configurer);
		}

	}

}
