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
import org.apereo.cas.configuration.model.support.logging.ConsoleLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 控制台日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(ConsoleLogHandlerFactoryBean.class)
public class ConsoleLoggingConfiguration extends AbstractLogHandlerConfiguration {

	public ConsoleLoggingConfiguration(final ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	static class ConsoleLogHandlerConfiguration extends AbstractConsoleLogHandlerConfiguration {

		private final List<ConsoleLoggingProperties> consoleLoggingProperties;

		public ConsoleLogHandlerConfiguration(final LoggingProperties properties) {
			this.consoleLoggingProperties = properties.getConsole();
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<ConsoleLogHandlerFactoryBean> logHandlerFactoryBean() {
			return consoleLoggingProperties.stream().map((properties)->{
				final ConsoleLogHandlerFactoryBeanConfigurer configurer = logHandlerFactoryBeanConfigurer(properties);

				return super.logHandlerFactoryBean(configurer);
			}).collect(Collectors.toList());
		}

		private ConsoleLogHandlerFactoryBeanConfigurer logHandlerFactoryBeanConfigurer(
				final ConsoleLoggingProperties consoleLoggingProperties) {
			final ConsoleLogHandlerFactoryBeanConfigurer configurer = new ConsoleLogHandlerFactoryBeanConfigurer();

			configurer.setTemplate(consoleLoggingProperties.getTemplate());
			if(Validate.hasText(consoleLoggingProperties.getFormatterClass())){
				try{
					ConsoleLogDataFormatter consoleLogDataFormatter = (ConsoleLogDataFormatter) BeanUtils.instantiateClass(
							Class.forName(consoleLoggingProperties.getFormatterClass()));
					configurer.setFormatter(consoleLogDataFormatter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(ConsoleLogDataFormatter.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

	}

}
