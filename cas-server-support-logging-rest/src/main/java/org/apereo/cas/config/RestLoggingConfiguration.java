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
import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;
import com.buession.logging.rest.spring.config.AbstractRestLogHandlerConfiguration;
import com.buession.logging.rest.spring.config.RestLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.RestLoggingProperties;
import org.apereo.cas.logging.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * REST 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(RestLogHandlerFactoryBean.class)
public class RestLoggingConfiguration {

	protected static RestLogHandlerFactoryBeanConfigurer restLogHandlerFactoryBeanConfigurer(
			final RestLoggingProperties restLoggingProperties) {
		final RestLogHandlerFactoryBeanConfigurer configurer = new RestLogHandlerFactoryBeanConfigurer();
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		configurer.setUrl(restLoggingProperties.getUrl());
		configurer.setRequestMethod(restLoggingProperties.getRequestMethod());
		propertyMapper.from(restLoggingProperties::getRequestBodyBuilder).as(BeanUtils::instantiateClass)
				.to(configurer::setRequestBodyBuilder);

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractRestLogHandlerConfiguration {

		private final RestLoggingProperties restLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.restLoggingProperties = properties.getHistory().getRest();
		}

		@Bean(name = "casBasicLoggingRestLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RestLogHandlerFactoryBeanConfigurer restLogHandlerFactoryBeanConfigurer() {
			return RestLoggingConfiguration.restLogHandlerFactoryBeanConfigurer(restLoggingProperties);
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casBasicLoggingRestLogHandlerFactoryBeanConfigurer") RestLogHandlerFactoryBeanConfigurer configurer,
				@Qualifier("casBasicLoggingHttpClient") ObjectProvider<HttpClient> httpClient,
				@Qualifier("casBasicLoggingHttpAsyncClient") ObjectProvider<HttpAsyncClient> httpAsyncClient) {
			return super.logHandlerFactoryBean(configurer, httpClient, httpAsyncClient);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractRestLogHandlerConfiguration {

		private final RestLoggingProperties restLoggingProperties;

		public History(final LoggingProperties properties) {
			this.restLoggingProperties = properties.getHistory().getRest();
		}

		@Bean(name = "casHistoryLoggingRestLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public RestLogHandlerFactoryBeanConfigurer restLogHandlerFactoryBeanConfigurer() {
			return RestLoggingConfiguration.restLogHandlerFactoryBeanConfigurer(restLoggingProperties);
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casHistoryLoggingRestLogHandlerFactoryBeanConfigurer") RestLogHandlerFactoryBeanConfigurer configurer,
				@Qualifier("casHistoryLoggingHttpClient") ObjectProvider<HttpClient> httpClient,
				@Qualifier("casHistoryLoggingHttpAsyncClient") ObjectProvider<HttpAsyncClient> httpAsyncClient) {
			return super.logHandlerFactoryBean(configurer, httpClient, httpAsyncClient);
		}

	}

}
