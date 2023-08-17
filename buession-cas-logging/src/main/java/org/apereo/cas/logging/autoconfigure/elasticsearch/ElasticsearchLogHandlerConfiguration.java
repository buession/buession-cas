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
package org.apereo.cas.logging.autoconfigure.elasticsearch;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.ElasticsearchRestTemplateFactoryBean;
import com.buession.logging.elasticsearch.spring.RestHighLevelClientFactoryBean;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.HistoryElasticsearchLogProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * Elasticsearch 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({ElasticsearchLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = ElasticsearchLogHandlerConfiguration.PREFIX, name = "elasticsearch.enabled", havingValue = "true")
public class ElasticsearchLogHandlerConfiguration
		extends AbstractLogHandlerConfiguration<HistoryElasticsearchLogProperties> {

	public ElasticsearchLogHandlerConfiguration(CasLoggingConfigurationProperties logProperties) {
		super(logProperties.getHistory().getElasticsearch());
	}

	@Bean(name = "loggingElasticsearchRestHighLevelClient")
	public RestHighLevelClientFactoryBean restHighLevelClientFactoryBean() {
		final RestHighLevelClientFactoryBean restHighLevelClientFactoryBean = new RestHighLevelClientFactoryBean();

		propertyMapper.from(handlerProperties::getUrls).to(restHighLevelClientFactoryBean::setUrls);
		propertyMapper.from(handlerProperties::getHost).to(restHighLevelClientFactoryBean::setHost);
		propertyMapper.from(handlerProperties::getPort).to(restHighLevelClientFactoryBean::setPort);
		propertyMapper.from(handlerProperties::getUsername).to(restHighLevelClientFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(restHighLevelClientFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getConnectionTimeout)
				.to(restHighLevelClientFactoryBean::setConnectionTimeout);
		propertyMapper.from(handlerProperties::getReadTimeout).to(restHighLevelClientFactoryBean::setReadTimeout);

		return restHighLevelClientFactoryBean;
	}

	@Bean(name = "loggingElasticsearchElasticsearchRestTemplate")
	public ElasticsearchRestTemplateFactoryBean elasticsearchRestTemplateFactoryBean(
			@Qualifier("loggingElasticsearchRestHighLevelClient") ObjectProvider<RestHighLevelClient> restHighLevelClient) {
		final ElasticsearchRestTemplateFactoryBean elasticsearchRestTemplateFactoryBean =
				new ElasticsearchRestTemplateFactoryBean();

		restHighLevelClient.ifUnique(elasticsearchRestTemplateFactoryBean::setClient);

		return elasticsearchRestTemplateFactoryBean;
	}

	@Bean
	public ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingElasticsearchElasticsearchRestTemplate") ObjectProvider<ElasticsearchRestTemplate> restTemplateFactory) {
		final ElasticsearchLogHandlerFactoryBean logHandlerFactoryBean = new ElasticsearchLogHandlerFactoryBean();

		restTemplateFactory.ifUnique(logHandlerFactoryBean::setRestTemplate);
		propertyMapper.from(handlerProperties::getIndexName).to(logHandlerFactoryBean::setIndexName);

		return logHandlerFactoryBean;
	}

}
