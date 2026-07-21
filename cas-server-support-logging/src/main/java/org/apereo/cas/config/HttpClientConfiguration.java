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

import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.logging.rest.spring.config.AbstractHttpClientConfiguration;
import com.buession.logging.rest.spring.config.HttpClientConfigurer;
import org.apereo.cas.configuration.model.support.logging.RestLoggingProperties;
import org.springframework.beans.BeanUtils;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public class HttpClientConfiguration extends AbstractHttpClientConfiguration {

	private final RestLoggingProperties restLoggingProperties;

	public HttpClientConfiguration(final RestLoggingProperties restLoggingProperties) {
		this.restLoggingProperties = restLoggingProperties;
	}

	public HttpClient httpClient() {
		final HttpClientConfigurer httpClientConfigurer = httpClientConfigurer();
		final org.apereo.cas.logging.rest.httpclient.HttpClient httpClient =
				new org.apereo.cas.logging.rest.httpclient.HttpClient(httpClientConfigurer);

		return httpClient.build();
	}

	public HttpAsyncClient httpAsyncClient() {
		final HttpClientConfigurer httpClientConfigurer = httpClientConfigurer();
		final org.apereo.cas.logging.rest.httpclient.HttpAsyncClient httpAsyncClient =
				new org.apereo.cas.logging.rest.httpclient.HttpAsyncClient(httpClientConfigurer);

		return httpAsyncClient.build();
	}

	public HttpClientConfigurer httpClientConfigurer() {
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
		propertyMapper.from(httpClientProperties::getExpectContinueEnabled)
				.to(configurer::setExpectContinueEnabled);
		propertyMapper.from(httpClientProperties::getAllowRedirects).to(configurer::setAllowRedirects);
		propertyMapper.from(httpClientProperties::getRelativeRedirectsAllowed)
				.to(configurer::setRelativeRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getCircularRedirectsAllowed)
				.to(configurer::setCircularRedirectsAllowed);
		propertyMapper.from(httpClientProperties::getMaxRedirects).to(configurer::setMaxRedirects);
		propertyMapper.from(httpClientProperties::getHardCancellationEnabled)
				.to(configurer::setHardCancellationEnabled);
		propertyMapper.from(httpClientProperties::getAuthenticationEnabled)
				.to(configurer::setAuthenticationEnabled);
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

			propertyMapper.from(httpClientProperties.getApacheClient()::getIoReactor)
					.to(apacheClient::setIoReactor);
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

}
