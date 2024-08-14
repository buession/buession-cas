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
package org.apereo.cas.logging.autoconfigure.jdbc;

import com.buession.logging.jdbc.converter.DefaultLogDataConverter;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.BaseJdbcLogProperties;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.basic.BasicJdbcLogProperties;
import org.apereo.cas.logging.config.history.HistoryJdbcLogProperties;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnClass(name = {"com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean"})
public class JdbcLogHandlerConfiguration extends AbstractLogHandlerConfiguration {

	protected static JdbcLogHandlerFactoryBean logHandlerFactoryBean(final BaseJdbcLogProperties jdbcLogProperties,
																	 final LogDataConverter logDataConverter,
																	 final JdbcTemplate jdbcTemplate) {
		final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();

		propertyMapper.from(jdbcTemplate).to(logHandlerFactoryBean::setJdbcTemplate);
		propertyMapper.from(jdbcLogProperties::getSql).to(logHandlerFactoryBean::setSql);
		propertyMapper.from(jdbcLogProperties::getIdGenerator).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setIdGenerator);
		propertyMapper.from(jdbcLogProperties::getDateTimeFormat).to(logHandlerFactoryBean::setDateTimeFormat);
		propertyMapper.from(jdbcLogProperties::getRequestParametersFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setRequestParametersFormatter);
		propertyMapper.from(jdbcLogProperties::getExtraFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setExtraFormatter);
		propertyMapper.from(logDataConverter).to(logHandlerFactoryBean::setLogDataConverter);

		return logHandlerFactoryBean;
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
	@ConditionalOnProperty(prefix = Basic.PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Basic.LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractBasicLogHandlerConfiguration<BasicJdbcLogProperties> {

		private static final BeanCondition CONDITION = BeanCondition.on(CasLoggingConfigurationProperties.PREFIX +
				".basic.jdbc.url").evenIfMissing();

		public Basic(CasLoggingConfigurationProperties logProperties) {
			super(logProperties.getBasic().getJdbc());
		}

		@ConditionalOnMissingBean(name = {"basicLoggingDataSource"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Bean(name = "basicLoggingDataSource")
		public DataSource dataSource(final ConfigurableApplicationContext applicationContext) {
			return BeanSupplier.of(DataSource.class).when(CONDITION.given(applicationContext.getEnvironment()))
					.supply(()->JpaBeans.newDataSource(handlerProperties)).otherwiseProxy().get();
		}

		@ConditionalOnMissingBean(name = {"basicLoggingJdbcTemplate"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Bean(name = "basicLoggingJdbcTemplate")
		public JdbcTemplate jdbcTemplate(@Qualifier("basicLoggingDataSource") ObjectProvider<DataSource> dataSource) {
			return new JdbcTemplate(dataSource.getIfAvailable());
		}

		@Bean(name = "basicLoggingJdbcDataConverter")
		@ConditionalOnMissingBean(name = "basicLoggingJdbcDataConverter")
		public LogDataConverter logDataConverter() {
			return new DefaultLogDataConverter();
		}

		@Bean(name = Basic.LOG_HANDLER_BEAN_NAME)
		public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("basicLoggingJdbcTemplate") ObjectProvider<JdbcTemplate> jdbcTemplate,
				@Qualifier("basicLoggingJdbcDataConverter") ObjectProvider<LogDataConverter> logDataConverter) {
			return JdbcLogHandlerConfiguration.logHandlerFactoryBean(handlerProperties,
					logDataConverter.getIfAvailable(), jdbcTemplate.getIfAvailable());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
	@ConditionalOnProperty(prefix = History.PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = History.LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractHistoryLogHandlerConfiguration<HistoryJdbcLogProperties> {

		private static final BeanCondition CONDITION = BeanCondition.on(CasLoggingConfigurationProperties.PREFIX +
				".history.jdbc.url").evenIfMissing();

		public History(CasLoggingConfigurationProperties logProperties) {
			super(logProperties.getHistory().getJdbc());
		}

		@ConditionalOnMissingBean(name = {"historyLoggingDataSource"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Bean(name = "historyLoggingDataSource")
		public DataSource dataSource(final ConfigurableApplicationContext applicationContext) {
			return BeanSupplier.of(DataSource.class).when(CONDITION.given(applicationContext.getEnvironment()))
					.supply(()->JpaBeans.newDataSource(handlerProperties)).otherwiseProxy().get();
		}

		@Bean(name = "historyLoggingJdbcTemplate")
		public JdbcTemplate jdbcTemplate(@Qualifier("historyLoggingDataSource") ObjectProvider<DataSource> dataSource) {
			return new JdbcTemplate(dataSource.getIfAvailable());
		}

		@Bean(name = "historyLoggingJdbcDataConverter")
		@ConditionalOnMissingBean(name = "historyLoggingJdbcDataConverter")
		public LogDataConverter logDataConverter() {
			return new DefaultLogDataConverter();
		}

		@Bean(name = History.LOG_HANDLER_BEAN_NAME)
		public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("historyLoggingJdbcTemplate") ObjectProvider<JdbcTemplate> jdbcTemplate,
				@Qualifier("historyLoggingJdbcDataConverter") ObjectProvider<LogDataConverter> logDataConverter) {
			return JdbcLogHandlerConfiguration.logHandlerFactoryBean(handlerProperties,
					logDataConverter.getIfAvailable(), jdbcTemplate.getIfAvailable());
		}

	}

}
