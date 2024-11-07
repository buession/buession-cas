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
package org.apereo.cas.web.flow.config;

import com.buession.core.id.IdGenerator;
import com.buession.core.validator.Validate;
import com.buession.httpclient.HttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.logging.console.formatter.ConsoleLogDataFormatter;
import com.buession.logging.console.handler.ConsoleLogHandler;
import com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean;
import com.buession.logging.console.spring.config.ConsoleLogHandlerFactoryBeanConfigurer;
import com.buession.logging.core.formatter.GeoFormatter;
import com.buession.logging.core.formatter.LogDataFormatter;
import com.buession.logging.core.formatter.MapFormatter;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.elasticsearch.handler.ElasticsearchLogHandler;
import com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean;
import com.buession.logging.elasticsearch.spring.config.ElasticsearchLogHandlerFactoryBeanConfigurer;
import com.buession.logging.file.handler.FileLogHandler;
import com.buession.logging.file.spring.FileLogHandlerFactoryBean;
import com.buession.logging.file.spring.config.FileLogHandlerFactoryBeanConfigurer;
import com.buession.logging.jdbc.converter.LogDataConverter;
import com.buession.logging.jdbc.handler.JdbcLogHandler;
import com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean;
import com.buession.logging.jdbc.spring.config.JdbcLogHandlerFactoryBeanConfigurer;
import com.buession.logging.kafka.handler.KafkaLogHandler;
import com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean;
import com.buession.logging.mongodb.handler.MongoLogHandler;
import com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean;
import com.buession.logging.rabbitmq.handler.RabbitLogHandler;
import com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean;
import com.buession.logging.rabbitmq.spring.config.RabbitLogHandlerFactoryBeanConfigurer;
import com.buession.logging.rest.core.RequestBodyBuilder;
import com.buession.logging.rest.handler.RestLogHandler;
import com.buession.logging.rest.spring.RestLogHandlerFactoryBean;
import com.buession.logging.rest.spring.config.RestLogHandlerFactoryBeanConfigurer;
import org.apereo.cas.authentication.CasSSLContext;
import org.apereo.cas.config.HttpClientConfiguration;
import org.apereo.cas.config.JdbcConfiguration;
import org.apereo.cas.config.KafkaConfiguration;
import org.apereo.cas.config.MongoConfiguration;
import org.apereo.cas.config.RabbitConfiguration;
import org.apereo.cas.configuration.model.support.logging.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
public class LogHandlerConfiguration {

	static abstract class AbstractLogHandlerConfiguration<PROPS extends AdapterLoggingProperties> {

		protected List<PROPS> properties;

		public AbstractLogHandlerConfiguration(final List<PROPS> properties) {
			this.properties = properties;
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.console.spring.ConsoleLogHandlerFactoryBean"})
	static class Console extends AbstractLogHandlerConfiguration<ConsoleLoggingProperties> {

		public Console(final LoggingProperties properties) {
			super(properties.getConsole());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"consoleLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<ConsoleLogHandler> consoleLogHandlers() {
			return properties.stream().map((properties)->{
				final ConsoleLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final ConsoleLogHandlerFactoryBean factoryBean = new ConsoleLogHandlerFactoryBean(configurer);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(ConsoleLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private ConsoleLogHandlerFactoryBeanConfigurer configurer(
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

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.elasticsearch.spring.ElasticsearchLogHandlerFactoryBean"})
	static class Elasticsearch extends AbstractLogHandlerConfiguration<ElasticsearchLoggingProperties> {

		public Elasticsearch(final LoggingProperties properties) {
			super(properties.getElasticsearch());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"elasticsearchLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<ElasticsearchLogHandler> elasticsearchLogHandlers() {
			return properties.stream().map((properties)->{
				final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final org.apereo.cas.config.ElasticsearchConfiguration elasticsearchConfiguration = new org.apereo.cas.config.ElasticsearchConfiguration(
						properties);
				final ElasticsearchTemplate elasticsearchTemplate = elasticsearchConfiguration.elasticsearchTemplate();
				final ElasticsearchLogHandlerFactoryBean factoryBean = new ElasticsearchLogHandlerFactoryBean(
						configurer);

				factoryBean.setElasticsearchTemplate(elasticsearchTemplate);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(ElasticsearchLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private ElasticsearchLogHandlerFactoryBeanConfigurer configurer(
				final ElasticsearchLoggingProperties elasticsearchLoggingProperties) {
			final ElasticsearchLogHandlerFactoryBeanConfigurer configurer = new ElasticsearchLogHandlerFactoryBeanConfigurer();

			configurer.setIndexName(elasticsearchLoggingProperties.getIndexName());
			configurer.setAutoCreateIndex(elasticsearchLoggingProperties.getAutoCreateIndex());

			return configurer;
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.file.spring.FileLogHandlerFactoryBean"})
	static class File extends AbstractLogHandlerConfiguration<FileLoggingProperties> {

		public File(final LoggingProperties properties) {
			super(properties.getFile());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"fileLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<FileLogHandler> fileLogHandlers() {
			return properties.stream().map((properties)->{
				final FileLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final FileLogHandlerFactoryBean factoryBean = new FileLogHandlerFactoryBean(configurer);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(FileLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private FileLogHandlerFactoryBeanConfigurer configurer(final FileLoggingProperties fileLoggingProperties) {
			final FileLogHandlerFactoryBeanConfigurer configurer = new FileLogHandlerFactoryBeanConfigurer();

			configurer.setPath(fileLoggingProperties.getPath());
			if(Validate.hasText(fileLoggingProperties.getFormatterClass())){
				try{
					LogDataFormatter<String> logDataFormatter = (LogDataFormatter<String>)
							BeanUtils.instantiateClass(Class.forName(fileLoggingProperties.getFormatterClass()));
					configurer.setFormatter(logDataFormatter);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(LogDataFormatter.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.jdbc.spring.JdbcLogHandlerFactoryBean"})
	static class Jdbc extends AbstractLogHandlerConfiguration<JdbcLoggingProperties> {

		public Jdbc(final LoggingProperties properties) {
			super(properties.getJdbc());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"jdbcLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<LogHandler> jdbcLogHandlers() {
			return properties.stream().map((properties)->{
				final JdbcLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final JdbcConfiguration jdbcConfiguration = new JdbcConfiguration(properties);
				final JdbcTemplate jdbcTemplate = jdbcConfiguration.jdbcTemplate();
				final JdbcLogHandlerFactoryBean factoryBean = new JdbcLogHandlerFactoryBean(configurer);

				factoryBean.setJdbcTemplate(jdbcTemplate);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(JdbcLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private JdbcLogHandlerFactoryBeanConfigurer configurer(final JdbcLoggingProperties jdbcLoggingProperties) {
			final JdbcLogHandlerFactoryBeanConfigurer configurer = new JdbcLogHandlerFactoryBeanConfigurer();

			configurer.setSql(jdbcLoggingProperties.getSql());

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

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.kafka.spring.KafkaLogHandlerFactoryBean"})
	static class Kafka extends AbstractLogHandlerConfiguration<KafkaLoggingProperties> {

		public Kafka(final LoggingProperties properties) {
			super(properties.getKafka());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"kafkaLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<KafkaLogHandler> kafkaLogHandlers() {
			return properties.stream().map((properties)->{
				final KafkaConfiguration kafkaConfiguration = new KafkaConfiguration(properties);
				final KafkaTemplate<String, Object> kafkaTemplate = kafkaConfiguration.kafkaTemplate();
				final KafkaLogHandlerFactoryBean factoryBean = new KafkaLogHandlerFactoryBean();

				factoryBean.setKafkaTemplate(kafkaTemplate);
				factoryBean.setTopic(properties.getTopic());

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(KafkaLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.mongodb.spring.MongoLogHandlerFactoryBean"})
	static class Mongo extends AbstractLogHandlerConfiguration<MongoLoggingProperties> {

		private final CasSSLContext casSslContext;

		public Mongo(final LoggingProperties properties,
					 @Qualifier(CasSSLContext.BEAN_NAME) final CasSSLContext casSslContext) {
			super(properties.getMongo());
			this.casSslContext = casSslContext;
		}

		@Bean
		@ConditionalOnMissingBean(name = {"mongoLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<LogHandler> mongoLogHandlers() {
			return properties.stream().map((properties)->{
				final MongoConfiguration mongoConfiguration = new MongoConfiguration(properties, casSslContext);
				final MongoTemplate mongoTemplate = mongoConfiguration.mongoTemplate();
				final MongoLogHandlerFactoryBean factoryBean = new MongoLogHandlerFactoryBean();

				factoryBean.setMongoTemplate(mongoTemplate);
				factoryBean.setCollectionName(properties.getCollection());

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(MongoLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.rabbitmq.spring.RabbitLogHandlerFactoryBean"})
	static class Rabbit extends AbstractLogHandlerConfiguration<RabbitLoggingProperties> {

		public Rabbit(final LoggingProperties properties) {
			super(properties.getRabbit());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"rabbitLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<RabbitLogHandler> rabbitLogHandlers() {
			return properties.stream().map((properties)->{
				final RabbitLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final RabbitConfiguration rabbitConfiguration = new RabbitConfiguration(properties);
				final RabbitTemplate rabbitTemplate;

				try{
					rabbitTemplate = rabbitConfiguration.rabbitTemplate();
				}catch(Exception e){
					throw new BeanInstantiationException(RabbitTemplate.class, e.getMessage(), e);
				}

				final RabbitLogHandlerFactoryBean factoryBean = new RabbitLogHandlerFactoryBean(configurer);

				factoryBean.setRabbitTemplate(rabbitTemplate);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(RabbitLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private RabbitLogHandlerFactoryBeanConfigurer configurer(
				final RabbitLoggingProperties rabbitLoggingProperties) {
			final RabbitLogHandlerFactoryBeanConfigurer configurer = new RabbitLogHandlerFactoryBeanConfigurer();

			configurer.setExchange(rabbitLoggingProperties.getExchange());
			configurer.setRoutingKey(rabbitLoggingProperties.getRoutingKey());

			return configurer;
		}

	}

	@AutoConfiguration
	@ConditionalOnClass(name = {"com.buession.logging.rest.spring.RestLogHandlerFactoryBean"})
	static class Rest extends AbstractLogHandlerConfiguration<RestLoggingProperties> {

		public Rest(final LoggingProperties properties) {
			super(properties.getRest());
		}

		@Bean
		@ConditionalOnMissingBean(name = {"restLogHandlers"})
		@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
		public List<RestLogHandler> restLogHandlers() {
			return properties.stream().map((properties)->{
				final RestLogHandlerFactoryBeanConfigurer configurer = configurer(properties);
				final HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration(properties);
				final HttpClient httpClient = httpClientConfiguration.httpClient();
				final HttpAsyncClient httpAsyncClient = httpClientConfiguration.httpAsyncClient();
				final RestLogHandlerFactoryBean factoryBean = new RestLogHandlerFactoryBean(configurer);

				factoryBean.setHttpClient(httpClient);
				factoryBean.setHttpAsyncClient(httpAsyncClient);

				try{
					factoryBean.afterPropertiesSet();
					return factoryBean.getObject();
				}catch(Exception e){
					throw new BeanInstantiationException(RestLogHandlerFactoryBean.class, e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		}

		private RestLogHandlerFactoryBeanConfigurer configurer(final RestLoggingProperties restLoggingProperties) {
			final RestLogHandlerFactoryBeanConfigurer configurer = new RestLogHandlerFactoryBeanConfigurer();

			configurer.setUrl(restLoggingProperties.getUrl());
			configurer.setRequestMethod(restLoggingProperties.getRequestMethod());

			if(Validate.hasText(restLoggingProperties.getRequestBodyBuilderClass())){
				try{
					RequestBodyBuilder requestBodyBuilder = (RequestBodyBuilder) BeanUtils.instantiateClass(
							Class.forName(restLoggingProperties.getRequestBodyBuilderClass()));
					configurer.setRequestBodyBuilder(requestBodyBuilder);
				}catch(ClassNotFoundException e){
					throw new BeanInstantiationException(RequestBodyBuilder.class, e.getMessage(), e);
				}
			}

			return configurer;
		}

	}

}
