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

import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import org.apereo.cas.configuration.model.support.logging.ConsoleLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.Constants;
import org.apereo.cas.logging.autoconfigure.BaseHandlerConfiguration;
import org.apereo.cas.util.spring.beans.BeanCondition;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * 控制台日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(ConsoleLogHandlerFactoryBean.class)
public class ConsoleLoggingConfiguration {

	protected static ConsoleLogHandlerFactoryBean createConsoleLogHandlerFactoryBean(
			final ConfigurableApplicationContext applicationContext,
			final ConsoleLoggingProperties consoleLoggingProperties, final String condition) {
		final BeanCondition beanCondition = BeanCondition.on(condition).evenIfMissing();
		return BeanSupplier.of(ConsoleLogHandlerFactoryBean.class)
				.when(beanCondition.given(applicationContext.getEnvironment()))
				.supply(()->{
					final ConsoleLogHandlerFactoryBean logHandlerFactoryBean = new ConsoleLogHandlerFactoryBean();

					ConsoleLogDataFormatter<String> consoleLogDataFormatter =
							BeanUtils.instantiateClass(consoleLoggingProperties.getFormatter());
					logHandlerFactoryBean.setFormatter(consoleLogDataFormatter);

					logHandlerFactoryBean.setTemplate(consoleLoggingProperties.getTemplate());

					return logHandlerFactoryBean;
				})
				.otherwiseProxy()
				.get();
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnMissingBean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
	static class Basic extends BaseHandlerConfiguration.BaseAdapterHandlerConfiguration<ConsoleLoggingProperties> {

		public Basic(final ConfigurableApplicationContext applicationContext, final LoggingProperties properties) {
			super(applicationContext, properties.getBasic().getConsole());
		}

		@Bean(name = Constants.BASIC_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConsoleLogHandlerFactoryBean basicConsoleLogHandlerFactoryBean() {
			final String condition = LoggingProperties.PREFIX + ".basic.console.template";
			return createConsoleLogHandlerFactoryBean(applicationContext, properties, condition);
		}

	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	@ConditionalOnMissingBean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
	static class History extends BaseHandlerConfiguration.BaseAdapterHandlerConfiguration<ConsoleLoggingProperties> {

		public History(final ConfigurableApplicationContext applicationContext, final LoggingProperties properties) {
			super(applicationContext, properties.getHistory().getConsole());
		}

		@Bean(name = Constants.HISTORY_LOG_HANDLER_BEAN_NAME)
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public ConsoleLogHandlerFactoryBean historyConsoleLogHandlerFactoryBean() {
			final String condition = LoggingProperties.PREFIX + ".history.console.template";
			return createConsoleLogHandlerFactoryBean(applicationContext, properties, condition);
		}

	}

}
