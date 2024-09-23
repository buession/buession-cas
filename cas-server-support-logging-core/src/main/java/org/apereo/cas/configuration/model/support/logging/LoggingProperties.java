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

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志配置
 *
 * @author Yong.Teng
 * @since 1.0.0
 */
@RequiresModule(name = "cas-server-core-authentication", automated = true)
@JsonFilter("CasLoggingProperties")
@ConfigurationProperties(prefix = LoggingProperties.PREFIX)
public class LoggingProperties implements Serializable {

	private final static long serialVersionUID = 5726256302113616711L;

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".logging";

	/**
	 * {@link com.buession.logging.core.BusinessType} 值
	 */
	@RequiredProperty
	private String businessType = "Login";

	/**
	 * {@link com.buession.logging.core.Event} 值
	 */
	@RequiredProperty
	private String event = "Login";

	/**
	 * 描述
	 */
	private String description = "Login";


	/**
	 * 控制台日志配置
	 */
	private List<ConsoleLoggingProperties> console = new ArrayList<>(0);

	/**
	 * Elasticsearch 日志配置
	 */
	private List<ElasticsearchLoggingProperties> elasticsearch = new ArrayList<>(0);

	/**
	 * 文件日志配置
	 */
	private List<FileLoggingProperties> file = new ArrayList<>(0);

	/**
	 * JDBC 日志配置
	 */
	private List<JdbcLoggingProperties> jdbc = new ArrayList<>(0);

	/**
	 * Kafka 日志配置
	 */
	private List<KafkaLoggingProperties> kafka = new ArrayList<>(0);

	/**
	 * MongoDB 日志配置
	 */
	private List<MongoLoggingProperties> mongo = new ArrayList<>(0);

	/**
	 * RabbitMQ 日志配置
	 */
	private List<RabbitLoggingProperties> rabbit = new ArrayList<>(0);

	/**
	 * Rest 日志配置
	 */
	private List<RestLoggingProperties> rest = new ArrayList<>(0);

	/**
	 * 返回 {@link com.buession.logging.core.BusinessType} 值
	 *
	 * @return {@link com.buession.logging.core.BusinessType} 值
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 {@link com.buession.logging.core.BusinessType} 值
	 *
	 * @param businessType
	 *        {@link com.buession.logging.core.BusinessType} 值
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 返回 {@link com.buession.logging.core.Event} 值
	 *
	 * @return {@link com.buession.logging.core.Event} 值
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * 设置 {@link com.buession.logging.core.Event} 值
	 *
	 * @param event
	 *        {@link com.buession.logging.core.Event} 值
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * 返回描述
	 *
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 *
	 * @param description
	 * 		描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回控制台日志配置
	 *
	 * @return 控制台日志配置
	 */
	public List<ConsoleLoggingProperties> getConsole() {
		return console;
	}

	/**
	 * 设置控制台日志配置
	 *
	 * @param console
	 * 		控制台日志配置
	 */
	public void setConsole(List<ConsoleLoggingProperties> console) {
		this.console = console;
	}

	/**
	 * 返回 Elasticsearch 日志配置
	 *
	 * @return Elasticsearch 日志配置
	 */
	public List<ElasticsearchLoggingProperties> getElasticsearch() {
		return elasticsearch;
	}

	/**
	 * 设置 Elasticsearch 日志配置
	 *
	 * @param elasticsearch
	 * 		Elasticsearch 日志配置
	 */
	public void setElasticsearch(List<ElasticsearchLoggingProperties> elasticsearch) {
		this.elasticsearch = elasticsearch;
	}

	/**
	 * 返回文件日志配置
	 *
	 * @return 文件日志配置
	 */
	public List<FileLoggingProperties> getFile() {
		return file;
	}

	/**
	 * 设置文件日志配置
	 *
	 * @param file
	 * 		文件日志配置
	 */
	public void setFile(List<FileLoggingProperties> file) {
		this.file = file;
	}

	/**
	 * 返回 JDBC 日志配置
	 *
	 * @return JDBC 日志配置
	 */
	public List<JdbcLoggingProperties> getJdbc() {
		return jdbc;
	}

	/**
	 * 设置 JDBC 日志配置
	 *
	 * @param jdbc
	 * 		JDBC 日志配置
	 */
	public void setJdbc(List<JdbcLoggingProperties> jdbc) {
		this.jdbc = jdbc;
	}

	/**
	 * 返回 Kafka 日志配置
	 *
	 * @return Kafka 日志配置
	 */
	public List<KafkaLoggingProperties> getKafka() {
		return kafka;
	}

	/**
	 * 设置 Kafka 日志配置
	 *
	 * @param kafka
	 * 		Kafka 日志配置
	 */
	public void setKafka(List<KafkaLoggingProperties> kafka) {
		this.kafka = kafka;
	}

	/**
	 * 返回 MongoDB 日志配置
	 *
	 * @return MongoDB 日志配置
	 */
	public List<MongoLoggingProperties> getMongo() {
		return mongo;
	}

	/**
	 * 设置 MongoDB 日志配置
	 *
	 * @param mongo
	 * 		MongoDB 日志配置
	 */
	public void setMongo(List<MongoLoggingProperties> mongo) {
		this.mongo = mongo;
	}

	/**
	 * 返回 RabbitMQ 日志配置
	 *
	 * @return RabbitMQ 日志配置
	 */
	public List<RabbitLoggingProperties> getRabbit() {
		return rabbit;
	}

	/**
	 * 设置 RabbitMQ 日志配置
	 *
	 * @param rabbit
	 * 		RabbitMQ 日志配置
	 */
	public void setRabbit(List<RabbitLoggingProperties> rabbit) {
		this.rabbit = rabbit;
	}

	/**
	 * 返回 Rest 日志配置
	 *
	 * @return Rest 日志配置
	 */
	public List<RestLoggingProperties> getRest() {
		return rest;
	}

	/**
	 * 设置 Rest 日志配置
	 *
	 * @param rest
	 * 		Rest 日志配置
	 */
	public void setRest(List<RestLoggingProperties> rest) {
		this.rest = rest;
	}

}
