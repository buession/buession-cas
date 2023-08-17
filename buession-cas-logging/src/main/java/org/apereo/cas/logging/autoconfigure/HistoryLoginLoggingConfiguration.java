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
package org.apereo.cas.logging.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.geoip.Resolver;
import com.buession.logging.core.handler.DefaultLogHandler;
import com.buession.logging.core.handler.DefaultPrincipalHandler;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.core.handler.PrincipalHandler;
import com.buession.logging.core.mgt.LogManager;
import com.buession.logging.core.request.ServletRequestContext;
import com.buession.logging.spring.LogManagerFactoryBean;
import org.apereo.cas.core.CasCoreConfigurationProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.DefaultHistoryLoginLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasCoreConfigurationProperties.class, CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
public class HistoryLoginLoggingConfiguration {

	private CasCoreConfigurationProperties casCoreConfigurationProperties;

	public HistoryLoginLoggingConfiguration(
			CasCoreConfigurationProperties casCoreConfigurationProperties) {
		this.casCoreConfigurationProperties = casCoreConfigurationProperties;
	}

	@Bean
	public LogManagerFactoryBean logManagerFactoryBean(ObjectProvider<PrincipalHandler<?>> principalHandler,
													   ObjectProvider<LogHandler> logHandler,
													   ObjectProvider<Resolver> geoResolver) {
		final LogManagerFactoryBean logManagerFactoryBean = new LogManagerFactoryBean();

		logManagerFactoryBean.setRequestContext(new ServletRequestContext());

		geoResolver.ifUnique(logManagerFactoryBean::setGeoResolver);
		principalHandler.ifUnique(logManagerFactoryBean::setPrincipalHandler);

		PrincipalHandler<?> principalHandlerInstance = principalHandler.getIfAvailable();
		if(principalHandlerInstance == null){
			logManagerFactoryBean.setPrincipalHandler(new DefaultPrincipalHandler());
		}else{
			logManagerFactoryBean.setPrincipalHandler(principalHandlerInstance);
		}

		LogHandler logHandlerInstance = logHandler.getIfAvailable();
		if(logHandlerInstance == null){
			logManagerFactoryBean.setLogHandler(new DefaultLogHandler());
		}else{
			logManagerFactoryBean.setLogHandler(logHandlerInstance);
		}

		if(Validate.isNotBlank(casCoreConfigurationProperties.getClientRealIpHeaderName())){
			logManagerFactoryBean.setClientIpHeaderName(casCoreConfigurationProperties.getClientRealIpHeaderName());
		}

		return logManagerFactoryBean;
	}

	@Bean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	public HistoryLoginLoggingManager historyLoginLoggingManager(ObjectProvider<LogManager> logManager) {
		return new DefaultHistoryLoginLoggingManager(logManager.getIfAvailable());
	}

}
