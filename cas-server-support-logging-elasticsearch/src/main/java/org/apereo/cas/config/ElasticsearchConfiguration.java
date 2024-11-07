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
import com.buession.logging.elasticsearch.spring.config.AbstractElasticsearchConfiguration;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchConfigurer;
import org.apereo.cas.configuration.model.support.logging.ElasticsearchLoggingProperties;
import org.apereo.cas.logging.elasticsearch.DefaultRestClientBuilderCustomizer;
import org.apereo.cas.util.spring.DirectObjectProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.mapping.callback.EntityCallbacks;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public class ElasticsearchConfiguration extends AbstractElasticsearchConfiguration {

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
