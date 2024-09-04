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

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.logging.elasticsearch.ElasticsearchCredentialsProvider;
import com.buession.logging.elasticsearch.RestClientBuilderCustomizer;
import com.buession.logging.elasticsearch.TransportOptionsCustomizer;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchConfigurer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.ElasticsearchLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.callback.EntityCallbacks;

import java.net.URI;
import java.time.Duration;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
public class ElasticsearchConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

	protected static ElasticsearchConfigurer elasticsearchConfigurer(
			final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
		final ElasticsearchConfigurer configurer = new ElasticsearchConfigurer();

		configurer.setUrls(elasticsearchLoggingProperties.getUrls());
		configurer.setPathPrefix(elasticsearchLoggingProperties.getPathPrefix());
		configurer.setHeaders(elasticsearchLoggingProperties.getHeaders());
		configurer.setParameters(elasticsearchLoggingProperties.getParameters());

		if(Validate.hasText(elasticsearchLoggingProperties.getEntityCallbacksClass())){
			try{
				configurer.setEntityCallbacks((EntityCallbacks) BeanUtils.instantiateClass(
						Class.forName(elasticsearchLoggingProperties.getEntityCallbacksClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractElasticsearchConfiguration {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.elasticsearchLoggingProperties = properties.getBasic().getElasticsearch();
		}

		@Bean(name = "casBasicLoggingElasticsearchConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ElasticsearchConfigurer elasticsearchConfigurer() {
			return ElasticsearchConfiguration.elasticsearchConfigurer(elasticsearchLoggingProperties);
		}

		@Bean(name = "casBasicLoggingElasticsearchConverter")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchConverter elasticsearchEntityMapper(
				SimpleElasticsearchMappingContext elasticsearchMappingContext,
				ElasticsearchCustomConversions elasticsearchCustomConversions) {
			return super.elasticsearchEntityMapper(elasticsearchMappingContext, elasticsearchCustomConversions);
		}

		@Bean(name = "casBasicLoggingElasticsearchMappingContext")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public SimpleElasticsearchMappingContext elasticsearchMappingContext(
				ElasticsearchCustomConversions elasticsearchCustomConversions) {
			return super.elasticsearchMappingContext(elasticsearchCustomConversions);
		}

		@Bean(name = "casBasicLoggingElasticsearchCustomConversions")
		@Override
		public ElasticsearchCustomConversions elasticsearchCustomConversions() {
			return super.elasticsearchCustomConversions();
		}

		@Bean(name = "casBasicLoggingElasticsearchRestClientBuilderCustomizer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestClientBuilderCustomizer restClientBuilderCustomizer() {
			return new DefaultRestClientBuilderCustomizer(elasticsearchLoggingProperties);
		}

		@Bean(name = "casBasicLoggingElasticsearchRestClient")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestClient restClient(
				@Qualifier("casBasicLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casBasicLoggingElasticsearchRestClientBuilderCustomizer") ObjectProvider<RestClientBuilderCustomizer> restClientBuilderCustomizer) {
			return super.restClient(configurer, restClientBuilderCustomizer);
		}

		@Bean(name = "casBasicLoggingElasticsearchClient")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchClient elasticsearchClient(
				@Qualifier("casBasicLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casBasicLoggingElasticsearchRestClient") RestClient restClient,
				@Qualifier("casBasicLoggingElasticsearchTransportOptionsCustomizer") ObjectProvider<TransportOptionsCustomizer> transportOptionsCustomizer) {
			return super.elasticsearchClient(configurer, restClient, transportOptionsCustomizer);
		}

		@Bean(name = "casBasicLoggingElasticsearchTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchTemplate elasticsearchTemplate(
				@Qualifier("casBasicLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casBasicLoggingElasticsearchClient") ElasticsearchClient elasticsearchClient,
				@Qualifier("casBasicLoggingElasticsearchConverter") ObjectProvider<ElasticsearchConverter> elasticsearchConverter) {
			return super.elasticsearchTemplate(configurer, elasticsearchClient, elasticsearchConverter);
		}

		@Override
		protected RefreshPolicy refreshPolicy() {
			return elasticsearchLoggingProperties.getRefreshPolicy();
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractElasticsearchConfiguration {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		public History(final LoggingProperties properties) {
			this.elasticsearchLoggingProperties = properties.getHistory().getElasticsearch();
		}

		@Bean(name = "casHistoryLoggingElasticsearchConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ElasticsearchConfigurer elasticsearchConfigurer() {
			return ElasticsearchConfiguration.elasticsearchConfigurer(elasticsearchLoggingProperties);
		}

		@Bean(name = "casHistoryLoggingElasticsearchConverter")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchConverter elasticsearchEntityMapper(
				SimpleElasticsearchMappingContext elasticsearchMappingContext,
				ElasticsearchCustomConversions elasticsearchCustomConversions) {
			return super.elasticsearchEntityMapper(elasticsearchMappingContext, elasticsearchCustomConversions);
		}

		@Bean(name = "casHistoryLoggingElasticsearchMappingContext")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public SimpleElasticsearchMappingContext elasticsearchMappingContext(
				ElasticsearchCustomConversions elasticsearchCustomConversions) {
			return super.elasticsearchMappingContext(elasticsearchCustomConversions);
		}

		@Bean(name = "casHistoryLoggingElasticsearchCustomConversions")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchCustomConversions elasticsearchCustomConversions() {
			return super.elasticsearchCustomConversions();
		}

		@Bean(name = "casHistoryLoggingElasticsearchRestClientBuilderCustomizer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestClientBuilderCustomizer restClientBuilderCustomizer() {
			return new DefaultRestClientBuilderCustomizer(elasticsearchLoggingProperties);
		}

		@Bean(name = "casHistoryLoggingElasticsearchRestClient")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public RestClient restClient(
				@Qualifier("casHistoryLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casHistoryLoggingElasticsearchRestClientBuilderCustomizer") ObjectProvider<RestClientBuilderCustomizer> restClientBuilderCustomizer) {
			return super.restClient(configurer, restClientBuilderCustomizer);
		}

		@Bean(name = "casHistoryLoggingElasticsearchClient")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchClient elasticsearchClient(
				@Qualifier("casHistoryLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casHistoryLoggingElasticsearchRestClient") RestClient restClient,
				@Qualifier("casHistoryLoggingElasticsearchTransportOptionsCustomizer") ObjectProvider<TransportOptionsCustomizer> transportOptionsCustomizer) {
			return super.elasticsearchClient(configurer, restClient, transportOptionsCustomizer);
		}

		@Bean(name = "casHistoryLoggingElasticsearchTemplate")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ElasticsearchTemplate elasticsearchTemplate(
				@Qualifier("casHistoryLoggingElasticsearchConfigurer") ElasticsearchConfigurer configurer,
				@Qualifier("casHistoryLoggingElasticsearchClient") ElasticsearchClient elasticsearchClient,
				@Qualifier("casHistoryLoggingElasticsearchConverter") ObjectProvider<ElasticsearchConverter> elasticsearchConverter) {
			return super.elasticsearchTemplate(configurer, elasticsearchClient, elasticsearchConverter);
		}

		@Override
		protected RefreshPolicy refreshPolicy() {
			return elasticsearchLoggingProperties.getRefreshPolicy();
		}

	}

	static class DefaultRestClientBuilderCustomizer implements RestClientBuilderCustomizer,
			org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		DefaultRestClientBuilderCustomizer(ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			this.elasticsearchLoggingProperties = elasticsearchLoggingProperties;
		}

		@Override
		public void customize(RestClientBuilder builder) {
		}

		@Override
		public void customize(HttpAsyncClientBuilder builder) {
			final CredentialsProvider credentialsProvider = new ElasticsearchCredentialsProvider(
					elasticsearchLoggingProperties.getUsername(), elasticsearchLoggingProperties.getPassword());

			builder.setDefaultCredentialsProvider(credentialsProvider);

			elasticsearchLoggingProperties.getUrls()
					.stream()
					.map(this::toUri)
					.filter(this::hasUserInfo)
					.forEach((uri)->this.addUserInfoCredentials(uri, credentialsProvider));
		}

		@Override
		public void customize(RequestConfig.Builder builder) {
			propertyMapper.from(elasticsearchLoggingProperties::getConnectionTimeout).asInt(Duration::toMillis)
					.to(builder::setConnectTimeout);
			propertyMapper.from(elasticsearchLoggingProperties::getReadTimeout).asInt(Duration::toMillis)
					.to(builder::setSocketTimeout);
		}

		private URI toUri(final String uri) {
			try{
				return URI.create(uri);
			}catch(IllegalArgumentException ex){
				return null;
			}
		}

		private boolean hasUserInfo(final URI uri) {
			return uri != null && Validate.hasText(uri.getUserInfo());
		}

		private void addUserInfoCredentials(final URI uri, final CredentialsProvider credentialsProvider) {
			AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
			Credentials credentials = createUserInfoCredentials(uri.getUserInfo());
			credentialsProvider.setCredentials(authScope, credentials);
		}

		private Credentials createUserInfoCredentials(final String userInfo) {
			int delimiter = userInfo.indexOf(":");
			if(delimiter == -1){
				return new UsernamePasswordCredentials(userInfo, null);
			}
			String username = userInfo.substring(0, delimiter);
			String password = userInfo.substring(delimiter + 1);
			return new UsernamePasswordCredentials(username, password);
		}

	}

}
