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
import com.buession.core.validator.Validate;
import com.buession.logging.elasticsearch.RestClientBuilderCustomizer;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchConfiguration;
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchLogHandlerConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchConfigurer;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.ElasticsearchLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.elasticsearch.DefaultRestClientBuilderCustomizer;
import org.apereo.cas.util.spring.DirectObjectProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.callback.EntityCallbacks;

import java.util.List;
import java.util.stream.Collectors;

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

	public ElasticsearchLoggingConfiguration(final ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	static class ElasticsearchLogHandlerConfiguration extends AbstractElasticsearchLogHandlerConfiguration {

		private final List<ElasticsearchLoggingProperties> elasticsearchLoggingProperties;

		public ElasticsearchLogHandlerConfiguration(final LoggingProperties properties) {
			this.elasticsearchLoggingProperties = properties.getElasticsearch();
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<ElasticsearchLogHandlerFactoryBean> logHandlerFactoryBean() {
			return elasticsearchLoggingProperties.stream().map((properties)->{
				final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = logHandlerFactoryBeanConfigurer(
						properties);
				final ElasticsearchTemplate elasticsearchTemplate = elasticsearchTemplate(properties);

				return super.logHandlerFactoryBean(elasticsearchTemplate, configurer);
			}).collect(Collectors.toList());
		}

		private ElasticsearchLogHandlerFactoryBeanConfigurer logHandlerFactoryBeanConfigurer(
				final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = new ElasticsearchLogHandlerFactoryBeanConfigurer();

			configurer.setIndexName(elasticsearchLoggingProperties.getIndexName());
			configurer.setAutoCreateIndex(elasticsearchLoggingProperties.getAutoCreateIndex());

			return configurer;
		}

		private ElasticsearchTemplate elasticsearchTemplate(
				final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			final ElasticsearchConfiguration configuration = new ElasticsearchConfiguration(
					elasticsearchLoggingProperties);

			return configuration.elasticsearchTemplate();
		}

	}

	private final static class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

		private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

		public ElasticsearchConfiguration(final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			this.elasticsearchLoggingProperties = elasticsearchLoggingProperties;
		}

		@Override
		public RestClientBuilderCustomizer restClientBuilderCustomizer() {
			return new DefaultRestClientBuilderCustomizer(elasticsearchLoggingProperties);
		}

		public ElasticsearchTemplate elasticsearchTemplate() {
			final ElasticsearchConfigurer configurer = elasticsearchConfigurer(elasticsearchLoggingProperties);

			final RestClientBuilderCustomizer restClientBuilderCustomizer = restClientBuilderCustomizer();
			final ElasticsearchCustomConversions elasticsearchCustomConversions = elasticsearchCustomConversions();
			final SimpleElasticsearchMappingContext elasticsearchMappingContext = elasticsearchMappingContext(
					elasticsearchCustomConversions);
			final ElasticsearchConverter elasticsearchConverter = elasticsearchEntityMapper(elasticsearchMappingContext,
					elasticsearchCustomConversions);

			final RestClient restClient = restClient(configurer,
					new DirectObjectProvider<>(restClientBuilderCustomizer));
			final ElasticsearchClient elasticsearchClient = elasticsearchClient(configurer, restClient,
					new DirectObjectProvider<>((options)->{

					}));

			return elasticsearchTemplate(configurer, elasticsearchClient,
					new DirectObjectProvider<>(elasticsearchConverter));
		}

		@Override
		protected RefreshPolicy refreshPolicy() {
			return elasticsearchLoggingProperties.getRefreshPolicy();
		}

		private ElasticsearchConfigurer elasticsearchConfigurer(
				final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			final ElasticsearchConfigurer configurer = new ElasticsearchConfigurer();

			configurer.setUrls(elasticsearchLoggingProperties.getUrls());
			configurer.setPathPrefix(elasticsearchLoggingProperties.getPathPrefix());
			configurer.setHeaders(elasticsearchLoggingProperties.getHeaders());
			configurer.setParameters(elasticsearchLoggingProperties.getParameters());

			if(Validate.hasText(elasticsearchLoggingProperties.getEntityCallbacksClass())){
				try{
					EntityCallbacks entityCallbacks = (EntityCallbacks) BeanUtils.instantiateClass(
							Class.forName(elasticsearchLoggingProperties.getEntityCallbacksClass()));
					configurer.setEntityCallbacks(entityCallbacks);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(EntityCallbacks.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

	}

}
