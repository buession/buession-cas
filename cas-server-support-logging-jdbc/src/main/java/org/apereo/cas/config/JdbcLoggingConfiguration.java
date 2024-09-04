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

import com.buession.core.id.IdGenerator;
import com.buession.core.validator.Validate;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.config.AbstractJdbcLogHandlerConfiguration;
import com.buession.logging.jdbc.spring.config.JdbcLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.JdbcLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(JdbcLogHandlerFactoryBean.class)
public class JdbcLoggingConfiguration extends AbstractLogHandlerConfiguration {

	public JdbcLoggingConfiguration(ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	protected static JdbcLogHandlerFactoryBeanConfigurer jdbcLogHandlerFactoryBeanConfigurer(
			final JdbcLoggingProperties jdbcLoggingProperties) {
		final JdbcLogHandlerFactoryBeanConfigurer configurer = new JdbcLogHandlerFactoryBeanConfigurer();

		configurer.setSql(jdbcLoggingProperties.getSql());
		configurer.setDateTimeFormat(jdbcLoggingProperties.getDateTimeFormat());

		if(Validate.hasText(jdbcLoggingProperties.getIdGeneratorClass())){
			try{
				configurer.setIdGenerator((IdGenerator<?>) BeanUtils.instantiateClass(
						Class.forName(jdbcLoggingProperties.getIdGeneratorClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		if(Validate.hasText(jdbcLoggingProperties.getRequestParametersFormatterClass())){
			try{
				configurer.setRequestParametersFormatter((MapFormatter<Object>) BeanUtils.instantiateClass(
						Class.forName(jdbcLoggingProperties.getRequestParametersFormatterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		if(Validate.hasText(jdbcLoggingProperties.getGeoFormatterClass())){
			try{
				configurer.setGeoFormatter((GeoFormatter) BeanUtils.instantiateClass(
						Class.forName(jdbcLoggingProperties.getGeoFormatterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		if(Validate.hasText(jdbcLoggingProperties.getExtraFormatterClass())){
			try{
				configurer.setExtraFormatter((MapFormatter<Object>) BeanUtils.instantiateClass(
						Class.forName(jdbcLoggingProperties.getExtraFormatterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		if(Validate.hasText(jdbcLoggingProperties.getDataConverterClass())){
			try{
				configurer.setDataConverter((LogDataConverter) BeanUtils.instantiateClass(
						Class.forName(jdbcLoggingProperties.getDataConverterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractJdbcLogHandlerConfiguration {

		private final JdbcLoggingProperties jdbcLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.jdbcLoggingProperties = properties.getBasic().getJdbc();
		}

		@Bean(name = "casBasicLoggingJdbcLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public JdbcLogHandlerFactoryBeanConfigurer jdbcLogHandlerFactoryBeanConfigurer() {
			return JdbcLoggingConfiguration.jdbcLogHandlerFactoryBeanConfigurer(jdbcLoggingProperties);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casBasicLoggingJdbcLogHandlerFactoryBeanConfigurer") JdbcLogHandlerFactoryBeanConfigurer configurer,
				@Qualifier("casBasicLoggingJdbcTemplate") JdbcTemplate jdbcTemplate) {
			return super.logHandlerFactoryBean(configurer, jdbcTemplate);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "jdbc.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractJdbcLogHandlerConfiguration {

		private final JdbcLoggingProperties jdbcLoggingProperties;

		public History(final LoggingProperties properties) {
			this.jdbcLoggingProperties = properties.getBasic().getJdbc();
		}

		@Bean(name = "casHistoryLoggingJdbcLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public JdbcLogHandlerFactoryBeanConfigurer jdbcLogHandlerFactoryBeanConfigurer() {
			return JdbcLoggingConfiguration.jdbcLogHandlerFactoryBeanConfigurer(jdbcLoggingProperties);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casHistoryLoggingJdbcLogHandlerFactoryBeanConfigurer") JdbcLogHandlerFactoryBeanConfigurer configurer,
				@Qualifier("casHistoryLoggingJdbcTemplate") JdbcTemplate jdbcTemplate) {
			return super.logHandlerFactoryBean(configurer, jdbcTemplate);
		}

	}

}
