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
package org.apereo.cas.logging.config;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.logging.config.history.HistoryElasticsearchLogProperties;
import org.apereo.cas.logging.config.history.HistoryJdbcLogProperties;
import org.apereo.cas.logging.config.history.HistoryKafkaLogProperties;
import org.apereo.cas.logging.config.history.HistoryMongoLogProperties;
import org.apereo.cas.logging.config.history.HistoryRabbitLogProperties;
import org.apereo.cas.logging.config.history.HistoryRestLogProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 基本日志配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("BasicLoggingProperties")
public class BasicLoggingProperties {

	/**
	 * Elasticsearch 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryElasticsearchLogProperties elasticsearch = new HistoryElasticsearchLogProperties();

	/**
	 * JDBC 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryJdbcLogProperties jdbc = new HistoryJdbcLogProperties();

	/**
	 * Kafka 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryKafkaLogProperties kafka = new HistoryKafkaLogProperties();

	/**
	 * MongoDB 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryMongoLogProperties mongo = new HistoryMongoLogProperties();

	/**
	 * RabbitMQ 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryRabbitLogProperties rabbit = new HistoryRabbitLogProperties();

	/**
	 * Rest 日志配置
	 */
	@NestedConfigurationProperty
	private HistoryRestLogProperties rest = new HistoryRestLogProperties();

	/**
	 * 返回 Elasticsearch 日志配置
	 *
	 * @return Elasticsearch 日志配置
	 */
	public HistoryElasticsearchLogProperties getElasticsearch() {
		return elasticsearch;
	}

	/**
	 * 设置 Elasticsearch 日志配置
	 *
	 * @param elasticsearch
	 * 		Elasticsearch 日志配置
	 */
	public void setElasticsearch(HistoryElasticsearchLogProperties elasticsearch) {
		this.elasticsearch = elasticsearch;
	}

	/**
	 * 返回 JDBC 日志配置
	 *
	 * @return JDBC 日志配置
	 */
	public HistoryJdbcLogProperties getJdbc() {
		return jdbc;
	}

	/**
	 * 设置 JDBC 日志配置
	 *
	 * @param jdbc
	 * 		JDBC 日志配置
	 */
	public void setJdbc(HistoryJdbcLogProperties jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 返回 Kafka 日志配置
	 *
	 * @return Kafka 日志配置
	 */
	public HistoryKafkaLogProperties getKafka() {
		return kafka;
	}

	/**
	 * 设置 Kafka 日志配置
	 *
	 * @param kafka
	 * 		Kafka 日志配置
	 */
	public void setKafka(HistoryKafkaLogProperties kafka) {
		this.kafka = kafka;
	}

	/**
	 * 返回 MongoDB 日志配置
	 *
	 * @return MongoDB 日志配置
	 */
	public HistoryMongoLogProperties getMongo() {
		return mongo;
	}

	/**
	 * 设置 MongoDB 日志配置
	 *
	 * @param mongo
	 * 		MongoDB 日志配置
	 */
	public void setMongo(HistoryMongoLogProperties mongo) {
		this.mongo = mongo;
	}

	/**
	 * 返回 RabbitMQ 日志配置
	 *
	 * @return RabbitMQ 日志配置
	 */
	public HistoryRabbitLogProperties getRabbit() {
		return rabbit;
	}

	/**
	 * 设置 RabbitMQ 日志配置
	 *
	 * @param rabbit
	 * 		RabbitMQ 日志配置
	 */
	public void setRabbit(HistoryRabbitLogProperties rabbit) {
		this.rabbit = rabbit;
	}

	/**
	 * 返回 Rest 日志配置
	 *
	 * @return Rest 日志配置
	 */
	public HistoryRestLogProperties getRest() {
		return rest;
	}

	/**
	 * 设置 Rest 日志配置
	 *
	 * @param rest
	 * 		Rest 日志配置
	 */
	public void setRest(HistoryRestLogProperties rest) {
		this.rest = rest;
	}

}
