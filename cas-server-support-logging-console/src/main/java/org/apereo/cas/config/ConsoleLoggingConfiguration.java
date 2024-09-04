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

import com.buession.core.validator.Validate;
import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import com.buession.logging.console.spring.config.AbstractConsoleLogHandlerConfiguration;
import com.buession.logging.console.spring.config.ConsoleLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.BasicLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.ConsoleLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.HistoryLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * 控制台日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
public class ConsoleLoggingConfiguration extends AbstractLogHandlerConfiguration {

	public ConsoleLoggingConfiguration(ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	protected static ConsoleLogHandlerFactoryBeanConfigurer consoleLogHandlerFactoryBeanConfigurer(
			final ConsoleLoggingProperties consoleLoggingProperties) {
		final ConsoleLogHandlerFactoryBeanConfigurer configurer = new ConsoleLogHandlerFactoryBeanConfigurer();

		configurer.setTemplate(consoleLoggingProperties.getTemplate());
		if(Validate.hasText(consoleLoggingProperties.getFormatterClass())){
			try{
				configurer.setFormatter(
						(ConsoleLogDataFormatter) BeanUtils.instantiateClass(
								Class.forName(consoleLoggingProperties.getFormatterClass())));
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e);
			}
		}

		return configurer;
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = BasicLoggingProperties.PREFIX, name = "console.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractConsoleLogHandlerConfiguration {

		private final ConsoleLoggingProperties consoleLoggingProperties;

		public Basic(final LoggingProperties properties) {
			this.consoleLoggingProperties = properties.getBasic().getConsole();
		}

		@Bean(name = "casBasicConsoleLoggingLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConsoleLogHandlerFactoryBeanConfigurer consoleLogHandlerFactoryBeanConfigurer() {
			return ConsoleLoggingConfiguration.consoleLogHandlerFactoryBeanConfigurer(consoleLoggingProperties);
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ConsoleLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casBasicConsoleLoggingLogHandlerFactoryBeanConfigurer") ConsoleLogHandlerFactoryBeanConfigurer configurer) {
			return super.logHandlerFactoryBean(configurer);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnProperty(prefix = HistoryLoggingProperties.PREFIX, name = "console.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends AbstractConsoleLogHandlerConfiguration {

		private final ConsoleLoggingProperties consoleLoggingProperties;

		public History(final LoggingProperties properties) {
			this.consoleLoggingProperties = properties.getHistory().getConsole();
		}

		@Bean(name = "casHistoryConsoleLoggingLogHandlerFactoryBeanConfigurer")
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConsoleLogHandlerFactoryBeanConfigurer consoleLogHandlerFactoryBeanConfigurer() {
			return ConsoleLoggingConfiguration.consoleLogHandlerFactoryBeanConfigurer(consoleLoggingProperties);
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		@Override
		public ConsoleLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("casHistoryConsoleLoggingLogHandlerFactoryBeanConfigurer") ConsoleLogHandlerFactoryBeanConfigurer configurer) {
			return super.logHandlerFactoryBean(configurer);
		}

	}

}
