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
package org.apereo.cas.logging.autoconfigure.console;

import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.formatter.DefaultConsoleLogDataFormatter;
import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.basic.BasicConsoleLogProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 控制台日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.3
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnClass(name = {"com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean"})
public class ConsoleLogHandlerConfiguration extends AbstractLogHandlerConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
	@ConditionalOnProperty(prefix = Basic.PREFIX, name = "console.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Basic.LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractBasicLogHandlerConfiguration<BasicConsoleLogProperties> {

		public Basic(CasLoggingConfigurationProperties logProperties) {
			super(logProperties.getBasic().getConsole());
		}

		@Bean(name = "basicLoggingConsoleLogDataFormatter")
		@ConditionalOnMissingBean(name = "basicLoggingConsoleLogDataFormatter")
		public ConsoleLogDataFormatter<String> consoleLogDataFormatter() {
			return new DefaultConsoleLogDataFormatter();
		}

		@Bean(name = Basic.LOG_HANDLER_BEAN_NAME)
		public ConsoleLogHandlerFactoryBean logHandlerFactoryBean(
				@Qualifier("basicLoggingConsoleLogDataFormatter") ObjectProvider<ConsoleLogDataFormatter<String>> consoleLogDataFormatter) {
			final ConsoleLogHandlerFactoryBean logHandlerFactoryBean = new ConsoleLogHandlerFactoryBean();

			consoleLogDataFormatter.ifAvailable(logHandlerFactoryBean::setFormatter);
			propertyMapper.from(handlerProperties.getTemplate()).to(logHandlerFactoryBean::setTemplate);

			return logHandlerFactoryBean;
		}

	}

}
