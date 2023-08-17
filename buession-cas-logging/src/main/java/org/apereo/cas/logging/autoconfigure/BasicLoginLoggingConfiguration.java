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

import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.manager.BasicLoginLoggingManager;
import org.apereo.cas.logging.manager.ConsoleBasicLoginLoggingManager;
import org.apereo.cas.logging.manager.JdbcBasicLoginLoggingManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
public class BasicLoginLoggingConfiguration {

	private final static String PREFIX = CasLoggingConfigurationProperties.PREFIX + ".basic";

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class BasicJdbcLoginLoggingManagerConfiguration {

		protected final CasLoggingConfigurationProperties loggingConfigurationProperties;

		protected final AbstractJpaProperties jpaProperties;

		public BasicJdbcLoginLoggingManagerConfiguration(
				CasLoggingConfigurationProperties loggingConfigurationProperties) {
			this.loggingConfigurationProperties = loggingConfigurationProperties;
			this.jpaProperties = loggingConfigurationProperties.getBasic().getJdbc();
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		public DataSource basicLoginLoggingDataSource() {
			return JpaBeans.newDataSource(jpaProperties);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		public PlatformTransactionManager basicLoginLoggingTransactionManager(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) DataSource loginLogDataSource) {
			return new DataSourceTransactionManager(loginLogDataSource);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public TransactionTemplate basicLoginLoggingTransactionTemplate(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager loginLogTransactionManager) {
			return createTransactionTemplate(loginLogTransactionManager);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		@ConditionalOnBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public BasicLoginLoggingManager basicLoginLoggingManager(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) ObjectProvider<DataSource> dataSource,
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME) ObjectProvider<TransactionTemplate> transactionTemplate) {
			return new JdbcBasicLoginLoggingManager(dataSource.getIfAvailable(),
					transactionTemplate.getIfAvailable(), loggingConfigurationProperties.getBasic().getJdbc());
		}

		protected TransactionTemplate createTransactionTemplate(final PlatformTransactionManager transactionManager) {
			final TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

			transactionTemplate.setIsolationLevelName(jpaProperties.getIsolationLevelName());
			transactionTemplate.setPropagationBehaviorName(jpaProperties.getPropagationBehaviorName());

			return transactionTemplate;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class BasicConsoleLoginLoggingManagerConfiguration {

		protected final CasLoggingConfigurationProperties loggingConfigurationProperties;

		public BasicConsoleLoginLoggingManagerConfiguration(
				CasLoggingConfigurationProperties loggingConfigurationProperties) {
			this.loggingConfigurationProperties = loggingConfigurationProperties;
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		public BasicLoginLoggingManager basicLoginLoggingManager() {
			return new ConsoleBasicLoginLoggingManager(
					loggingConfigurationProperties.getBasic().getConsole().getMessage());
		}

	}

}
