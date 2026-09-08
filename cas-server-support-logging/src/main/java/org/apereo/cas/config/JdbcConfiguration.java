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

import com.buession.logging.jdbc.spring.config.AbstractJdbcConfiguration;
import com.buession.logging.jdbc.spring.config.JdbcConfigurer;
import org.apereo.cas.configuration.model.support.logging.JdbcLoggingProperties;
import org.apereo.cas.configuration.support.JpaBeans;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public class JdbcConfiguration extends AbstractJdbcConfiguration {

	private final JdbcLoggingProperties jdbcLoggingProperties;

	public JdbcConfiguration(final JdbcLoggingProperties jdbcLoggingProperties) {
		this.jdbcLoggingProperties = jdbcLoggingProperties;
	}

	@Override
	public DataSource dataSource(JdbcConfigurer configurer) {
		return JpaBeans.newDataSource(jdbcLoggingProperties);
	}

	public JdbcTemplate jdbcTemplate() {
		final DataSource dataSource = dataSource(jdbcConfigurer());
		return jdbcTemplate(dataSource);
	}

	private JdbcConfigurer jdbcConfigurer() {
		final JdbcConfigurer configurer = new JdbcConfigurer();

		propertyMapper.from(jdbcLoggingProperties::getDriverClass).to(configurer::setDriverClassName);
		propertyMapper.from(jdbcLoggingProperties::getUrl).to(configurer::setUrl);
		propertyMapper.from(jdbcLoggingProperties::getUser).to(configurer::setUsername);
		propertyMapper.from(jdbcLoggingProperties::getPassword).to(configurer::setPassword);

		return configurer;
	}

}
