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

import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.BasicLoginLoggingManager;
import org.apereo.cas.logging.manager.ConsoleBasicLoginLoggingManager;
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.apereo.cas.logging.manager.ConsoleHistoryLoginLoggingManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
@AutoConfigureAfter({JdbcLoginLoggingConfiguration.class, MongoDbLoginLoggingConfiguration.class})
public class ConsoleLoginLoggingConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class BasicConsoleLoginLoggingManagerConfiguration {

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		public BasicLoginLoggingManager basicLoginLoggingManager(){
			return new ConsoleBasicLoginLoggingManager();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class HistoryConsoleLoginLoggingManagerConfiguration {

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		public HistoryLoginLoggingManager historyLoginLoggingManager(){
			return new ConsoleHistoryLoginLoggingManager();
		}

	}

}
