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
package org.apereo.cas.logging.autoconfigure.mongo;

import com.buession.core.validator.Validate;
import com.buession.dao.mongodb.core.ReadConcern;
import com.buession.dao.mongodb.core.ReadPreference;
import com.buession.dao.mongodb.core.WriteConcern;
import com.buession.logging.core.handler.LogHandler;
import com.buession.logging.mongodb.spring.MongoClientFactoryBean;
import com.buession.logging.mongodb.spring.MongoDatabaseFactoryBean;
import com.buession.logging.mongodb.spring.MongoHandlerFactoryBean;
import com.buession.logging.mongodb.spring.MongoMappingContextFactoryBean;
import com.buession.logging.mongodb.spring.MongoTemplateFactoryBean;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import org.apereo.cas.logging.autoconfigure.AbstractLogHandlerConfiguration;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;
import org.apereo.cas.logging.config.history.HistoryMongoLogProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.concurrent.TimeUnit;

/**
 * MongoDb 日志处理器自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CasLoggingConfigurationProperties.class)
@ConditionalOnMissingBean(LogHandler.class)
@ConditionalOnClass({MongoHandlerFactoryBean.class})
@ConditionalOnProperty(prefix = MongoLogHandlerConfiguration.PREFIX, name = "mongo.enabled", havingValue = "true")
public class MongoLogHandlerConfiguration extends AbstractLogHandlerConfiguration<HistoryMongoLogProperties> {

	public MongoLogHandlerConfiguration(CasLoggingConfigurationProperties logProperties) {
		super(logProperties.getHistory().getMongo());
	}

	@Bean(name = "logMongoDbMongoClient")
	public MongoClientFactoryBean mongoClientFactoryBean() {
		final MongoClientFactoryBean mongoClientFactoryBean = new MongoClientFactoryBean();

		if(Validate.hasText(handlerProperties.getUrl())){
			propertyMapper.from(handlerProperties::getUrl).as(ConnectionString::new)
					.to(mongoClientFactoryBean::setConnectionString);
		}else{
			propertyMapper.from(handlerProperties::getHost).to(mongoClientFactoryBean::setHost);
			propertyMapper.from(handlerProperties::getPort).to(mongoClientFactoryBean::setPort);
		}
		propertyMapper.from(handlerProperties::getReplicaSetName).to(mongoClientFactoryBean::setReplicaSet);

		if(handlerProperties.getUsername() != null && handlerProperties.getPassword() != null){
			final String database =
					handlerProperties.getAuthenticationDatabase() ==
							null ? handlerProperties.getDatabaseName() : handlerProperties.getAuthenticationDatabase();
			final MongoCredential credential = MongoCredential.createCredential(handlerProperties.getUsername(),
					database, handlerProperties.getPassword().toCharArray());

			mongoClientFactoryBean.setCredential(new MongoCredential[]{credential});
		}

		final MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();

		propertyMapper.from(handlerProperties::getReadPreference).as(ReadPreference::getValue)
				.to(mongoClientSettingsBuilder::readPreference);
		propertyMapper.from(handlerProperties::getReadConcern).as(ReadConcern::getValue)
				.to(mongoClientSettingsBuilder::readConcern);
		propertyMapper.from(handlerProperties::getWriteConcern).as(WriteConcern::getValue)
				.to(mongoClientSettingsBuilder::writeConcern);
		propertyMapper.from(handlerProperties::getUuidRepresentation)
				.to(mongoClientSettingsBuilder::uuidRepresentation);

		mongoClientSettingsBuilder.applyToSocketSettings(($builder)->{
			final SocketSettings.Builder socketBuilder = SocketSettings.builder();

			if(handlerProperties.getConnectionTimeout() != null){
				socketBuilder.connectTimeout((int) handlerProperties.getConnectionTimeout().toMillis(),
						TimeUnit.MILLISECONDS);
			}
			if(handlerProperties.getReadTimeout() != null){
				socketBuilder.readTimeout((int) handlerProperties.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS);
			}

			$builder.applySettings(socketBuilder.build());
		}).applyToConnectionPoolSettings(($builder)->{
			if(handlerProperties.getPool() != null){
				final ConnectionPoolSettings.Builder poolBuilder = ConnectionPoolSettings.builder();

				if(handlerProperties.getPool().getMinSize() > 0){
					poolBuilder.minSize(handlerProperties.getPool().getMinSize());
				}
				if(handlerProperties.getPool().getMaxSize() > 0){
					poolBuilder.minSize(handlerProperties.getPool().getMaxSize());
				}
				if(handlerProperties.getPool().getMaxWaitTime() != null){
					poolBuilder.maxWaitTime(handlerProperties.getPool().getMaxWaitTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(handlerProperties.getPool().getMaxConnectionLifeTime() != null){
					poolBuilder.maxConnectionLifeTime(handlerProperties.getPool().getMaxConnectionLifeTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(handlerProperties.getPool().getMaxConnectionIdleTime() != null){
					poolBuilder.maxConnectionIdleTime(handlerProperties.getPool().getMaxConnectionIdleTime().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(handlerProperties.getPool().getMaintenanceInitialDelay() != null){
					poolBuilder.maintenanceInitialDelay(
							handlerProperties.getPool().getMaintenanceInitialDelay().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(handlerProperties.getPool().getMaintenanceFrequency() != null){
					poolBuilder.maintenanceFrequency(handlerProperties.getPool().getMaintenanceFrequency().toMillis(),
							TimeUnit.MILLISECONDS);
				}
				if(handlerProperties.getPool().getMaxConnecting() > 0){
					poolBuilder.maxConnecting(handlerProperties.getPool().getMaxConnecting());
				}

				$builder.applySettings(poolBuilder.build());
			}
		});

		return mongoClientFactoryBean;
	}

	@Bean(name = "logMongoDbMongoDatabase")
	public MongoDatabaseFactoryBean mongoDatabaseFactoryBean(
			@Qualifier("logMongoDbMongoClient") ObjectProvider<MongoClient> mongoClient) {
		final MongoDatabaseFactoryBean mongoDatabaseFactoryBean = new MongoDatabaseFactoryBean();

		mongoClient.ifUnique(mongoDatabaseFactoryBean::setMongoClient);

		if(Validate.hasText(handlerProperties.getDatabaseName())){
			mongoDatabaseFactoryBean.setDatabaseName(handlerProperties.getDatabaseName());
		}else{
			String database = new ConnectionString(handlerProperties.getUrl()).getDatabase();
			mongoDatabaseFactoryBean.setDatabaseName(database);
		}

		return mongoDatabaseFactoryBean;
	}

	@Bean(name = "logMongoDbMongoMappingContext")
	public MongoMappingContextFactoryBean mongoMappingContextFactoryBean() {
		final MongoMappingContextFactoryBean mongoMappingContextFactoryBean = new MongoMappingContextFactoryBean();

		propertyMapper.from(handlerProperties::getAutoIndexCreation)
				.to(mongoMappingContextFactoryBean::setAutoIndexCreation);
		propertyMapper.from(handlerProperties::getFieldNamingStrategy).as(BeanUtils::instantiateClass)
				.to(mongoMappingContextFactoryBean::setFieldNamingStrategy);

		return mongoMappingContextFactoryBean;
	}

	@Bean(name = "logMongoDbMongoTemplate")
	public MongoTemplateFactoryBean mongoTemplateFactoryBean(
			@Qualifier("logMongoDbMongoDatabase") ObjectProvider<MongoDatabaseFactory> mongoDatabaseFactory,
			@Qualifier("logMongoDbMongoMappingContext") ObjectProvider<MongoMappingContext> mongoMappingContext) {
		final MongoTemplateFactoryBean mongoTemplateFactoryBean = new MongoTemplateFactoryBean();

		mongoDatabaseFactory.ifUnique(mongoTemplateFactoryBean::setMongoDatabaseFactory);
		mongoMappingContext.ifUnique(mongoTemplateFactoryBean::setMongoMappingContext);

		return mongoTemplateFactoryBean;
	}

	@Bean
	public MongoHandlerFactoryBean logHandlerFactoryBean(
			@Qualifier("logMongoDbMongoTemplate") ObjectProvider<MongoTemplate> mongoTemplate) {
		final MongoHandlerFactoryBean logHandlerFactoryBean = new MongoHandlerFactoryBean();

		mongoTemplate.ifUnique(logHandlerFactoryBean::setMongoTemplate);

		propertyMapper.from(handlerProperties::getCollectionName).to(logHandlerFactoryBean::setCollectionName);

		return logHandlerFactoryBean;
	}

}
