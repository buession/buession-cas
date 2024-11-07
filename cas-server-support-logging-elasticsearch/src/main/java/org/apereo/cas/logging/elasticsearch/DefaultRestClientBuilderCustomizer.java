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
package org.apereo.cas.logging.elasticsearch;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import com.buession.logging.elasticsearch.ElasticsearchCredentialsProvider;
import com.buession.logging.elasticsearch.RestClientBuilderCustomizer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apereo.cas.configuration.model.support.logging.ElasticsearchLoggingProperties;
import org.elasticsearch.client.RestClientBuilder;

import java.net.URI;
import java.time.Duration;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
public class DefaultRestClientBuilderCustomizer implements RestClientBuilderCustomizer,
		org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer {

	private final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	private final ElasticsearchLoggingProperties elasticsearchLoggingProperties;

	public DefaultRestClientBuilderCustomizer(ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
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