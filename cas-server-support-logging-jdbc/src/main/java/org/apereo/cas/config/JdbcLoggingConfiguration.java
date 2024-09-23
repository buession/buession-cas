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
import com.buession.logging.jdbc.spring.config.AbstractJdbcConfiguration;
import com.buession.logging.jdbc.spring.config.AbstractJdbcLogHandlerConfiguration;
import com.buession.logging.jdbc.spring.config.JdbcConfigurer;
import com.buession.logging.jdbc.spring.config.JdbcLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.configuration.model.support.logging.JdbcLoggingProperties;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.support.JpaBeans;
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
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

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

	public JdbcLoggingConfiguration(final ConfigurableApplicationContext applicationContext) {
		super(applicationContext);
	}

	@AutoConfiguration
	@EnableConfigurationProperties(LoggingProperties.class)
	static class JdbcLogHandlerConfiguration extends AbstractJdbcLogHandlerConfiguration {

		private final List<JdbcLoggingProperties> jdbcLoggingProperties;

		public JdbcLogHandlerConfiguration(final LoggingProperties properties) {
			this.jdbcLoggingProperties = properties.getJdbc();
		}

		@Bean
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<JdbcLogHandlerFactoryBean> logHandlerFactoryBean() {
			return jdbcLoggingProperties.stream().map((properties)->{
				final JdbcLogHandlerFactoryBeanConfigurer configurer = logHandlerFactoryBeanConfigurer(properties);
				final JdbcConfiguration jdbcConfiguration = new JdbcConfiguration(properties);
				final JdbcTemplate jdbcTemplate = jdbcConfiguration.jdbcTemplate();

				return super.logHandlerFactoryBean(configurer, jdbcTemplate);
			}).collect(Collectors.toList());
		}

		private JdbcLogHandlerFactoryBeanConfigurer logHandlerFactoryBeanConfigurer(
				final JdbcLoggingProperties jdbcLoggingProperties) {
			final JdbcLogHandlerFactoryBeanConfigurer configurer = new JdbcLogHandlerFactoryBeanConfigurer();

			configurer.setSql(jdbcLoggingProperties.getSql());
			configurer.setDateTimeFormat(jdbcLoggingProperties.getDateTimeFormat());

			if(Validate.hasText(jdbcLoggingProperties.getIdGeneratorClass())){
				try{
					IdGenerator<?> idGenerator = (IdGenerator<?>) BeanUtils.instantiateClass(
							Class.forName(jdbcLoggingProperties.getIdGeneratorClass()));
					configurer.setIdGenerator(idGenerator);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(IdGenerator.class, e.getMessage(), e);
				}
			}

			if(Validate.hasText(jdbcLoggingProperties.getRequestParametersFormatterClass())){
				try{
					MapFormatter<Object> mapFormatter = (MapFormatter<Object>) BeanUtils.instantiateClass(
							Class.forName(jdbcLoggingProperties.getRequestParametersFormatterClass()));
					configurer.setRequestParametersFormatter(mapFormatter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(MapFormatter.class, e.getMessage(), e);
				}
			}

			if(Validate.hasText(jdbcLoggingProperties.getGeoFormatterClass())){
				try{
					GeoFormatter geoFormatter = (GeoFormatter) BeanUtils.instantiateClass(
							Class.forName(jdbcLoggingProperties.getGeoFormatterClass()));
					configurer.setGeoFormatter(geoFormatter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(GeoFormatter.class, e.getMessage(), e);
				}
			}

			if(Validate.hasText(jdbcLoggingProperties.getExtraFormatterClass())){
				try{
					MapFormatter<Object> mapFormatter = (MapFormatter<Object>) BeanUtils.instantiateClass(
							Class.forName(jdbcLoggingProperties.getExtraFormatterClass()));
					configurer.setExtraFormatter(mapFormatter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(MapFormatter.class, e.getMessage(), e);
				}
			}

			if(Validate.hasText(jdbcLoggingProperties.getDataConverterClass())){
				try{
					LogDataConverter logDataConverter = (LogDataConverter) BeanUtils.instantiateClass(
							Class.forName(jdbcLoggingProperties.getDataConverterClass()));
					configurer.setDataConverter(logDataConverter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(LogDataConverter.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

	}

	private final static class JdbcConfiguration extends AbstractJdbcConfiguration {

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

}
