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

import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpAsyncClient;
import com.buession.httpclient.OkHttpHttpClient;
import com.buession.httpclient.apache.ApacheNioClientConnectionManager;
import com.buession.httpclient.conn.OkHttpClientConnectionManager;
import com.buession.httpclient.conn.OkHttpNioClientConnectionManager;
import com.buession.logging.rest.spring.config.AbstractHttpClientConfiguration;
import com.buession.logging.rest.spring.config.HttpClientConfigurer;
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
 * Rest 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 0.0.1
 */
@AutoConfiguration
public class HttpClientConfiguration extends AbstractHttpClientConfiguration {

	protected final static String LOGGING_HTTPCLIENT_CONNECTION_MANAGER = "casLoggingHttpClientConnectionManager";

	protected final static String LOGGING_HTTPCLIENT_BEAN_NAME = "casLoggingHttpClient";

	protected final static String LOGGING_ASYNC_HTTPCLIENT_BEAN_NAME = "casLoggingAsyncHttpClient";

	protected static HttpClientConfigurer httpClientConfigurer(final RestLoggingProperties restLoggingProperties) {
		final HttpClientConfigurer configurer = new HttpClientConfigurer();
		final RestLoggingProperties.HttpClientProperties httpClientProperties = restLoggingProperties.getHttpClient();

		propertyMapper.from(httpClientProperties::getConnectionManagerShared)
				.to(configurer::setConnectionManagerShared);
		propertyMapper.from(httpClientProperties::getRetryOnConnectionFailure)
				.to(configurer::setRetryOnConnectionFailure);
		propertyMapper.from(httpClientProperties::getMaxConnections).to(configurer::setMaxConnections);
		propertyMapper.from(httpClientProperties::getMaxPerRoute).to(configurer::setMaxPerRoute);
		propertyMapper.from(httpClientProperties::getMaxRequests).to(configurer::setMaxRequests);
		propertyMapper.from(httpClientProperties::getIdleConnectionTime).to(configurer::setIdleConnectionTime);
		propertyMapper.from(httpClientProperties::getConnectTimeout).to(configurer::setConnectTimeout);
		propertyMapper.from(httpClientProperties::getConnectionRequestTimeout)
				.to(configurer::setConnectionRequestTimeout);
		propertyMapper.from(httpClientProperties::getConnectionTimeToLive).to(configurer::setConnectionTimeToLive);
		propertyMapper.from(httpClientProperties::getReadTimeout).to(configurer::setReadTimeout);
		propertyMapper.from(httpClientProperties::getWriteTimeout).to(configurer::setWriteTimeout);
		propertyMapper.from(httpClientProperties::getExpectContinueEnabled).to(configurer::setExpectContinueEnabled);
		propertyMapper.from(httpClientProperties::getAllowRedirects).to(configurer::setAllowRedirects);
		propertyMapper.from(httpClientProperties::getRelativeRedirectsAllowed)
				.to(configurer::setRelativeRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getCircularRedirectsAllowed)
				.to(configurer::setCircularRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getMaxRedirects).to(configurer::setMaxRedirects);
		propertyMapper.from(httpClientProperties::getHardCancellationEnabled)
				.to(configurer::setHardCancellationEnabled);
		propertyMapper.from(httpClientProperties::getAuthenticationEnabled).to(configurer::setAuthenticationEnabled);
		propertyMapper.from(httpClientProperties::getTargetPreferredAuthSchemes)
				.to(configurer::setTargetPreferredAuthSchemes);
		propertyMapper.from(httpClientProperties::getProxyPreferredAuthSchemes)
				.to(configurer::setProxyPreferredAuthSchemes);
		propertyMapper.from(httpClientProperties::getContentCompressionEnabled)
				.to(configurer::setContentCompressionEnabled);
		propertyMapper.from(httpClientProperties::getNormalizeUri).to(configurer::setNormalizeUri);
		propertyMapper.from(httpClientProperties::getCookieSpec).to(configurer::setCookieSpec);
		propertyMapper.from(httpClientProperties::getSslConfiguration).to(configurer::setSslConfiguration);
		propertyMapper.from(httpClientProperties::getProxy).to(configurer::setProxy);

		if(httpClientProperties.getApacheClient() != null){
			final HttpClientConfigurer.ApacheClient apacheClient = new HttpClientConfigurer.ApacheClient();

			propertyMapper.from(httpClientProperties.getApacheClient()::getIoReactor).to(apacheClient::setIoReactor);
			propertyMapper.from(httpClientProperties.getApacheClient()::getThreadFactory)
					.as(BeanUtils::instantiateClass).to(apacheClient::setThreadFactory);

			configurer.setApacheClient(apacheClient);
		}

		if(httpClientProperties.getOkHttp() != null){
			final HttpClientConfigurer.OkHttp okHttp = new HttpClientConfigurer.OkHttp();
			configurer.setOkHttp(okHttp);
		}

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractHttpClientConfiguration {

		private final RestLoggingProperties restLoggingProperties;

		public Basic(final LoggingProperties loggingProperties) {
			this.restLoggingProperties = loggingProperties.getBasic().getRest();
		}

		@Bean(name = "casBasicLoggingHttpClientConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public HttpClientConfigurer httpClientConfigurer() {
			return HttpClientConfiguration.httpClientConfigurer(restLoggingProperties);
		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient",
				"org.apache.http.nio.client.HttpAsyncClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingAsyncHttpClient")
		static class AsyncApacheHttpClientConfiguration extends AbstractAsyncApacheHttpClientConfiguration {

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheNioClientConnectionManager apache5NioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apache5NioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.http.nio.client.HttpAsyncClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheNioClientConnectionManager apacheNioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apacheNioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public ApacheHttpAsyncClient httpAsyncClient(
					@Qualifier("casBasicLoggingAsyncHttpClientConnectionManager") ObjectProvider<ApacheNioClientConnectionManager> clientConnectionManager) {
				return super.httpAsyncClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient",
				"org.apache.http.client.HttpClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingHttpClient")
		static class ApacheHttpClientConfiguration extends AbstractApacheHttpClientConfiguration {

			@Bean(name = "casBasicLoggingHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheClientConnectionManager apache5ClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apache5ClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheClientConnectionManager apacheClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apacheClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.ApacheHttpClient httpClient(
					@Qualifier("casBasicLoggingHttpClientConnectionManager") ObjectProvider<com.buession.httpclient.apache.ApacheClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingAsyncHttpClient")
		static class AsyncOkHttpClientConfiguration extends AbstractAsyncOkHttpClientConfiguration {

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpNioClientConnectionManager okHttpNioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.okHttpNioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public OkHttpHttpAsyncClient httpClient(
					@Qualifier("casBasicLoggingAsyncHttpClientConnectionManager") ObjectProvider<OkHttpNioClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
		@ConditionalOnMissingBean(name = LOGGING_HTTPCLIENT_BEAN_NAME)
		static class OkHttpClientConfiguration extends AbstractOkHttpClientConfiguration {

			@Bean(name = "casBasicLoggingHttpClientConnectionManager")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpClientConnectionManager okHttpClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.okHttpClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpHttpClient httpClient(
					@Qualifier("casBasicLoggingHttpClientConnectionManager") ObjectProvider<OkHttpClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractHttpClientConfiguration {

		private final RestLoggingProperties restLoggingProperties;

		public History(final LoggingProperties loggingProperties) {
			this.restLoggingProperties = loggingProperties.getHistory().getRest();
		}

		@Bean(name = "casHistoryLoggingHttpClientConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public HttpClientConfigurer httpClientConfigurer() {
			return HttpClientConfiguration.httpClientConfigurer(restLoggingProperties);
		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient",
				"org.apache.http.nio.client.HttpAsyncClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingAsyncHttpClient")
		static class AsyncApacheHttpClientConfiguration extends AbstractAsyncApacheHttpClientConfiguration {

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.hc.client5.http.async.HttpAsyncClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheNioClientConnectionManager apache5NioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apache5NioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.http.nio.client.HttpAsyncClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheNioClientConnectionManager apacheNioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apacheNioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public ApacheHttpAsyncClient httpAsyncClient(
					@Qualifier("casBasicLoggingAsyncHttpClientConnectionManager") ObjectProvider<ApacheNioClientConnectionManager> clientConnectionManager) {
				return super.httpAsyncClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient",
				"org.apache.http.client.HttpClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingHttpClient")
		static class ApacheHttpClientConfiguration extends AbstractApacheHttpClientConfiguration {

			@Bean(name = "casBasicLoggingHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.hc.client5.http.classic.HttpClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheClientConnectionManager apache5ClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apache5ClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingHttpClientConnectionManager")
			@ConditionalOnClass(name = {"org.apache.http.client.HttpClient"})
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.apache.ApacheClientConnectionManager apacheClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.apacheClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public com.buession.httpclient.ApacheHttpClient httpClient(
					@Qualifier("casBasicLoggingHttpClientConnectionManager") ObjectProvider<com.buession.httpclient.apache.ApacheClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
		@ConditionalOnMissingBean(name = "casBasicLoggingAsyncHttpClient")
		static class AsyncOkHttpClientConfiguration extends AbstractAsyncOkHttpClientConfiguration {

			@Bean(name = "casBasicLoggingAsyncHttpClientConnectionManager")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpNioClientConnectionManager okHttpNioClientConnectionManager(
					@Qualifier("casBasicLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.okHttpNioClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casBasicLoggingAsyncHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			@Override
			public OkHttpHttpAsyncClient httpClient(
					@Qualifier("casBasicLoggingAsyncHttpClientConnectionManager") ObjectProvider<OkHttpNioClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

		@AutoConfiguration
		@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "rest.enabled", havingValue = "true")
		@ConditionalOnClass(name = {"okhttp3.OkHttpClient"})
		@ConditionalOnMissingBean(name = LOGGING_HTTPCLIENT_BEAN_NAME)
		static class OkHttpClientConfiguration extends AbstractOkHttpClientConfiguration {

			@Bean(name = "casHistoryLoggingHttpClientConnectionManager")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpClientConnectionManager okHttpClientConnectionManager(
					@Qualifier("casHistoryLoggingHttpClientConfigurer") HttpClientConfigurer httpClientConfigurer) {
				return super.okHttpClientConnectionManager(httpClientConfigurer);
			}

			@Bean(name = "casHistoryLoggingHttpClient")
			@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
			public OkHttpHttpClient httpClient(
					@Qualifier("casHistoryLoggingHttpClientConnectionManager") ObjectProvider<OkHttpClientConnectionManager> clientConnectionManager) {
				return super.httpClient(clientConnectionManager);
			}

		}

	}

}
