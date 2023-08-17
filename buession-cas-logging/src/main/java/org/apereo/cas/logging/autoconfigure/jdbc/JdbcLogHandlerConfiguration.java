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
package org.apereo.cas.logging.autoconfigure.jdbc;

import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.JdbcTemplateFactoryBean;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.HistoryJdbcLogProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({JdbcLogHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = JdbcLogHandlerConfiguration.PREFIX, name = "jdbc.enabled", havingValue = "true")
public class JdbcLogHandlerConfiguration extends AbstractLogHandlerConfiguration<HistoryJdbcLogProperties> {

	public JdbcLogHandlerConfiguration(CasLoggingConfigurationProperties logProperties) {
		super(logProperties.getHistory().getJdbc());
	}

	@Bean(name = "loggingJdbcJdbcTemplate")
	public JdbcTemplateFactoryBean jdbcTemplateFactoryBean() {
		final JdbcTemplateFactoryBean jdbcTemplateFactoryBean = new JdbcTemplateFactoryBean();

		propertyMapper.from(handlerProperties::getDriverClassName).to(jdbcTemplateFactoryBean::setDriverClassName);
		propertyMapper.from(handlerProperties::getUrl).to(jdbcTemplateFactoryBean::setUrl);
		propertyMapper.from(handlerProperties::getUser).to(jdbcTemplateFactoryBean::setUsername);
		propertyMapper.from(handlerProperties::getPassword).to(jdbcTemplateFactoryBean::setPassword);
		propertyMapper.from(handlerProperties::getPoolConfiguration).to(jdbcTemplateFactoryBean::setPoolConfiguration);

		return jdbcTemplateFactoryBean;
	}

	@Bean
	public JdbcLogHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("loggingJdbcJdbcTemplate") ObjectProvider<JdbcTemplate> jdbcTemplate) {
		final JdbcLogHandlerFactoryBean logHandlerFactoryBean = new JdbcLogHandlerFactoryBean();

		jdbcTemplate.ifUnique(logHandlerFactoryBean::setJdbcTemplate);

		propertyMapper.from(handlerProperties::getSql).to(logHandlerFactoryBean::setSql);
		propertyMapper.from(handlerProperties::getIdGenerator).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setIdGenerator);
		propertyMapper.from(handlerProperties::getDateTimeFormat).to(logHandlerFactoryBean::setDateTimeFormat);
		propertyMapper.from(handlerProperties::getRequestParametersFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setRequestParametersFormatter);
		propertyMapper.from(handlerProperties::getExtraFormatter).as(BeanUtils::instantiateClass)
				.to(logHandlerFactoryBean::setExtraFormatter);

		return logHandlerFactoryBean;
	}

}
