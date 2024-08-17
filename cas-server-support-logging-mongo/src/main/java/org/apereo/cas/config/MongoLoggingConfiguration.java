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

import com.buession.core.utils.ClassUtils;
import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.basic.BasicConsoleLogProperties;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(CasLoggingProperties.class)
@ConditionalOnClass(name = {"com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean"})
public class KafkaLoggingConfiguration {

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
	@ConditionalOnProperty(prefix = Basic.PREFIX, name = "console.enabled", havingValue = "true")
	@ConditionalOnMissingBean(name = Basic.LOG_HANDLER_BEAN_NAME)
	static class Basic extends AbstractBasicLogHandlerConfiguration<BasicConsoleLogProperties> {

		public Basic(CasLoggingConfigurationProperties logProperties) {
			super(logProperties.getBasic().getConsole());
		}

		@Bean(name = Basic.LOG_HANDLER_BEAN_NAME)
		public ConsoleLogHandlerFactoryBean logHandlerFactoryBean() {
			final ConsoleLogHandlerFactoryBean logHandlerFactoryBean = new ConsoleLogHandlerFactoryBean();

			try{
				ConsoleLogDataFormatter<String> consoleLogDataFormatter =
						BeanUtils.instantiateClass((Class<ConsoleLogDataFormatter<String>>) ClassUtils.getClass(
								handlerProperties.getFormatterName(), false));
				logHandlerFactoryBean.setFormatter(consoleLogDataFormatter);
			}catch(ClassNotFoundException e){
			}catch(BeanInstantiationException e){
			}

			AbstractLogHandlerConfiguration.propertyMapper.from(handlerProperties.getTemplate())
					.to(logHandlerFactoryBean::setTemplate);

			return logHandlerFactoryBean;
		}

	}

}
