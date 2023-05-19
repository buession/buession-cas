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
import org.apereo.cas.logging.manager.HistoryLoginLoggingManager;
import org.apereo.cas.logging.manager.JdbcBasicLoginLoggingManager;
import org.apereo.cas.logging.manager.JdbcHistoryLoginLoggingManager;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "enabled", havingValue = "true")
@EnableTransactionManagement(proxyTargetClass = true)
public class JdbcLoginLoggingConfiguration {

	abstract static class AbstractJdbcConfiguration {

		protected final CasLoggingConfigurationProperties loggingConfigurationProperties;

		protected final AbstractJpaProperties jpaProperties;

		public AbstractJdbcConfiguration(CasLoggingConfigurationProperties loggingConfigurationProperties,
										 AbstractJpaProperties jpaProperties){
			this.loggingConfigurationProperties = loggingConfigurationProperties;
			this.jpaProperties = jpaProperties;
		}

		protected TransactionTemplate createTransactionTemplate(final PlatformTransactionManager transactionManager){
			TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

			transactionTemplate.setIsolationLevelName(jpaProperties.getIsolationLevelName());
			transactionTemplate.setPropagationBehaviorName(jpaProperties.getPropagationBehaviorName());

			return transactionTemplate;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "basic.jdbc.url")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class BasicJdbcLoginLoggingConfiguration extends AbstractJdbcConfiguration {

		public BasicJdbcLoginLoggingConfiguration(CasLoggingConfigurationProperties loggingConfigurationProperties){
			super(loggingConfigurationProperties, loggingConfigurationProperties.getBasic().getJdbc());
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		public DataSource basicLoginLoggingDataSource(){
			return JpaBeans.newDataSource(jpaProperties);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		public PlatformTransactionManager basicLoginLoggingTransactionManager(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) DataSource loginLogDataSource){
			return new DataSourceTransactionManager(loginLogDataSource);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public TransactionTemplate basicLoginLoggingTransactionTemplate(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager loginLogTransactionManager){
			return createTransactionTemplate(loginLogTransactionManager);
		}

		@Bean(name = Constants.BASIC_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		@ConditionalOnBean(name = Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public BasicLoginLoggingManager basicLoginLoggingManager(
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) ObjectProvider<DataSource> dataSource,
				@Qualifier(Constants.BASIC_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME) ObjectProvider<TransactionTemplate> transactionTemplate){
			return new JdbcBasicLoginLoggingManager(dataSource.getIfAvailable(),
					transactionTemplate.getIfAvailable(), loggingConfigurationProperties.getBasic().getJdbc());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties({CasLoggingConfigurationProperties.class})
	@ConditionalOnProperty(prefix = CasLoggingConfigurationProperties.PREFIX, name = "history.jdbc.url")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
	static class HistoryJdbcLoginLoggingConfiguration extends AbstractJdbcConfiguration {

		public HistoryJdbcLoginLoggingConfiguration(CasLoggingConfigurationProperties loggingConfigurationProperties){
			super(loggingConfigurationProperties, loggingConfigurationProperties.getHistory().getJdbc());
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_DATASOURCE_BEAN_NAME)
		public DataSource historyLoginLoggingDataSource(){
			return JpaBeans.newDataSource(jpaProperties);
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME)
		public PlatformTransactionManager historyLoginLoggingTransactionManager(
				@Qualifier(Constants.HISTORY_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) DataSource loginLogDataSource){
			return new DataSourceTransactionManager(loginLogDataSource);
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		@ConditionalOnMissingBean(name = Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public TransactionTemplate historyLoginLoggingTransactionTemplate(
				@Qualifier(Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_MANAGER_BEAN_NAME) PlatformTransactionManager loginLogTransactionManager){
			return createTransactionTemplate(loginLogTransactionManager);
		}

		@Bean(name = Constants.HISTORY_LOGIN_LOGGING_MANAGER_BEAN_NAME)
		@ConditionalOnBean(name = Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME)
		public HistoryLoginLoggingManager historyLoginLoggingManager(
				@Qualifier(Constants.HISTORY_LOGIN_LOGGING_DATASOURCE_BEAN_NAME) ObjectProvider<DataSource> dataSource,
				@Qualifier(Constants.HISTORY_LOGIN_LOGGING_JDBC_TRANSACTION_TEMPLATE_BEAN_NAME) ObjectProvider<TransactionTemplate> transactionTemplate){
			return new JdbcHistoryLoginLoggingManager(dataSource.getIfAvailable(),
					transactionTemplate.getIfAvailable(), loggingConfigurationProperties.getHistory().getJdbc());
		}

	}

}
