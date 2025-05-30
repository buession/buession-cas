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
package org.apereo.cas.logging.autoconfigure;

import com.buession.geoip.CacheDatabaseResolver;
import com.buession.geoip.Resolver;
import com.buession.logging.core.handler.DefaultPrincipalHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.request.RequestContext;
import com.buession.logging.core.request.ServletRequestContext;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties({CasConfigurationProperties.class, LoggingProperties.class})
public class LoggingConfiguration {

	private final CasConfigurationProperties casConfigurationProperties;

	public LoggingConfiguration(final CasConfigurationProperties casConfigurationProperties) {
		this.casConfigurationProperties = casConfigurationProperties;
	}

	@Bean
	@ConditionalOnMissingBean({RequestContext.class})
	public RequestContext requestContext() {
		return new ServletRequestContext();
	}

	@Bean
	@ConditionalOnMissingBean({PrincipalHandler.class})
	public PrincipalHandler<?> principalHandler() {
		return new DefaultPrincipalHandler();
	}

	@Bean
	@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
	public Resolver geoResolver() throws IOException {
		final Resource database = casConfigurationProperties.getGeoLocation().getMaxmind().getCityDatabase();
		return new CacheDatabaseResolver(database.getInputStream());
	}

}
