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
package org.apereo.cas.logging.config;

import com.buession.logging.core.BusinessType;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.core.ThreadPoolProperties;
import org.apereo.cas.logging.config.basic.BasicJdbcLogProperties;
import org.apereo.cas.logging.config.basic.BasicConsoleLogProperties;
import org.apereo.cas.logging.config.history.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * 日志配置
 *
 * @author Yong.Teng
 * @since 2.1.0
 */
@ConfigurationProperties(prefix = CasLoggingConfigurationProperties.PREFIX)
public class CasLoggingConfigurationProperties implements Serializable {

	private final static long serialVersionUID = 1667933943101773003L;

	public final static String PREFIX = CasConfigurationProperties.PREFIX + ".support.logging";

	/**
	 * {@link BusinessType} 值
	 */
	@RequiredProperty
	private String businessType;

	/**
	 * {@link com.buession.logging.core.Event} 值
	 */
	@RequiredProperty
	private String event;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 基本日志配置
	 */
	@NestedConfigurationProperty
	private Basic basic = new Basic();

	/**
	 * 历史日志配置
	 */
	@NestedConfigurationProperty
	private History history = new History();

	/**
	 * 线程池配置
	 */
	@NestedConfigurationProperty
	private ThreadPoolProperties threadPool = new ThreadPoolProperties();

	/**
	 * 返回 {@link BusinessType} 值
	 *
	 * @return {@link BusinessType} 值
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * 设置 {@link BusinessType} 值
	 *
	 * @param businessType
	 *        {@link BusinessType} 值
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
	 * 返回基本日志配置
	 *
	 * @return 基本日志配置
	 */
	public Basic getBasic() {
		return basic;
	}

	/**
	 * 设置基本日志配置
	 *
	 * @param basic
	 * 		基本日志配置
	 */
	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	/**
	 * 返回历史日志配置
	 *
	 * @return 历史日志配置
	 */
	public History getHistory() {
		return history;
	}

	/**
	 * 设置历史日志配置
	 *
	 * @param history
	 * 		历史日志配置
	 */
	public void setHistory(History history) {
		this.history = history;
	}

	/**
	 * 返回线程池配置
	 *
	 * @return 线程池配置
	 */
	public ThreadPoolProperties getThreadPool() {
		return threadPool;
	}

	/**
	 * 设置线程池配置
	 *
	 * @param threadPool
	 * 		线程池配置
	 */
	public void setThreadPool(ThreadPoolProperties threadPool) {
		this.threadPool = threadPool;
	}

	/**
	 * 基本日志配置
	 */
	public final static class Basic implements Serializable {

		private final static long serialVersionUID = -3470252849013406076L;

		/**
		 * 控制台日志配置
		 */
		private BasicConsoleLogProperties console = new BasicConsoleLogProperties();

		/**
		 * JDBC 日志配置
		 */
		private BasicJdbcLogProperties jdbc = new BasicJdbcLogProperties();

		/**
		 * 返回控制台日志配置
		 *
		 * @return 控制台日志配置
		 */
		public BasicConsoleLogProperties getConsole() {
			return console;
		}

		/**
		 * 设置控制台日志配置
		 *
		 * @param console
		 * 		控制台日志配置
		 */
		public void setConsole(BasicConsoleLogProperties console) {
			this.console = console;
		}

		/**
		 * 返回 JDBC 日志配置
		 *
		 * @return JDBC 日志配置
		 */
		public BasicJdbcLogProperties getJdbc() {
			return jdbc;
		}

		/**
		 * 设置 JDBC 日志配置
		 *
		 * @param jdbc
		 * 		JDBC 日志配置
		 */
		public void setJdbc(BasicJdbcLogProperties jdbc) {
			this.jdbc = jdbc;
		}

	}

	/**
	 * 历史日志配置
	 */
	public final static class History implements Serializable {

		private final static long serialVersionUID = -8608612324312905183L;

		/**
		 * Elasticsearch 日志配置
		 */
		private HistoryElasticsearchLogProperties elasticsearch;

		/**
		 * JDBC 日志配置
		 */
		private HistoryJdbcLogProperties jdbc;

		/**
		 * Kafka 日志配置
		 */
		private HistoryKafkaLogProperties kafka;

		/**
		 * MongoDB 日志配置
		 */
		private HistoryMongoLogProperties mongo;

		/**
		 * RabbitMQ 日志配置
		 */
		private HistoryRabbitLogProperties rabbit;

		/**
		 * Rest 日志配置
		 */
		private HistoryRestLogProperties rest;

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

}
