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
package org.apereo.cas.configuration.model.support.logging;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * 日志配置基类
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
public abstract class BaseLoggingProperties implements Serializable {

	private final static long serialVersionUID = 6984845990182654725L;

	/**
	 * 控制台日志配置
	 */
	@NestedConfigurationProperty
	private ConsoleLoggingProperties console = new ConsoleLoggingProperties();

	/**
	 * Elasticsearch 日志配置
	 */
	@NestedConfigurationProperty
	private ElasticsearchLoggingProperties elasticsearch = new ElasticsearchLoggingProperties();

	/**
	 * 文件日志配置
	 */
	@NestedConfigurationProperty
	private FileLoggingProperties file = new FileLoggingProperties();

	/**
	 * JDBC 日志配置
	 */
	@NestedConfigurationProperty
	private JdbcLoggingProperties jdbc = new JdbcLoggingProperties();

	/**
	 * Kafka 日志配置
	 */
	@NestedConfigurationProperty
	private KafkaLoggingProperties kafka = new KafkaLoggingProperties();

	/**
	 * MongoDB 日志配置
	 */
	@NestedConfigurationProperty
	private MongoLoggingProperties mongo = new MongoLoggingProperties();

	/**
	 * RabbitMQ 日志配置
	 */
	@NestedConfigurationProperty
	private RabbitLoggingProperties rabbit = new RabbitLoggingProperties();

	/**
	 * Rest 日志配置
	 */
	@NestedConfigurationProperty
	private RestLoggingProperties rest = new RestLoggingProperties();

	/**
	 * 返回控制台日志配置
	 *
	 * @return 控制台日志配置
	 */
	public ConsoleLoggingProperties getConsole() {
		return console;
	}

	/**
	 * 设置控制台日志配置
	 *
	 * @param console
	 * 		控制台日志配置
	 */
	public void setConsole(ConsoleLoggingProperties console) {
		this.console = console;
	}

	/**
	 * 返回 Elasticsearch 日志配置
	 *
	 * @return Elasticsearch 日志配置
	 */
	public ElasticsearchLoggingProperties getElasticsearch() {
		return elasticsearch;
	}

	/**
	 * 设置 Elasticsearch 日志配置
	 *
	 * @param elasticsearch
	 * 		Elasticsearch 日志配置
	 */
	public void setElasticsearch(ElasticsearchLoggingProperties elasticsearch) {
		this.elasticsearch = elasticsearch;
	}

	/**
	 * 返回文件日志配置
	 *
	 * @return 文件日志配置
	 */
	public FileLoggingProperties getFile() {
		return file;
	}

	/**
	 * 设置文件日志配置
	 *
	 * @param file
	 * 		文件日志配置
	 */
	public void setFile(FileLoggingProperties file) {
		this.file = file;
	}

	/**
	 * 返回 JDBC 日志配置
	 *
	 * @return JDBC 日志配置
	 */
	public JdbcLoggingProperties getJdbc() {
		return jdbc;
	}

	/**
	 * 设置 JDBC 日志配置
	 *
	 * @param jdbc
	 * 		JDBC 日志配置
	 */
	public void setJdbc(JdbcLoggingProperties jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 返回 Kafka 日志配置
	 *
	 * @return Kafka 日志配置
	 */
	public KafkaLoggingProperties getKafka() {
		return kafka;
	}

	/**
	 * 设置 Kafka 日志配置
	 *
	 * @param kafka
	 * 		Kafka 日志配置
	 */
	public void setKafka(KafkaLoggingProperties kafka) {
		this.kafka = kafka;
	}

	/**
	 * 返回 MongoDB 日志配置
	 *
	 * @return MongoDB 日志配置
	 */
	public MongoLoggingProperties getMongo() {
		return mongo;
	}

	/**
	 * 设置 MongoDB 日志配置
	 *
	 * @param mongo
	 * 		MongoDB 日志配置
	 */
	public void setMongo(MongoLoggingProperties mongo) {
		this.mongo = mongo;
	}

	/**
	 * 返回 RabbitMQ 日志配置
	 *
	 * @return RabbitMQ 日志配置
	 */
	public RabbitLoggingProperties getRabbit() {
		return rabbit;
	}

	/**
	 * 设置 RabbitMQ 日志配置
	 *
	 * @param rabbit
	 * 		RabbitMQ 日志配置
	 */
	public void setRabbit(RabbitLoggingProperties rabbit) {
		this.rabbit = rabbit;
	}

	/**
	 * 返回 Rest 日志配置
	 *
	 * @return Rest 日志配置
	 */
	public RestLoggingProperties getRest() {
		return rest;
	}

	/**
	 * 设置 Rest 日志配置
	 *
	 * @param rest
	 * 		Rest 日志配置
	 */
	public void setRest(RestLoggingProperties rest) {
		this.rest = rest;
	}

}
