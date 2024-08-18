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
package org.apereo.cas.configuration.model.support.logging;

import com.buession.logging.kafka.config.SecurityConfiguration;
import com.buession.logging.kafka.config.SslConfiguration;
import com.buession.logging.kafka.spring.ProducerFactory;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.springframework.util.unit.DataSize;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kafka 日志适配器配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
@JsonFilter("HistoryKafkaLogProperties")
public class KafkaLogProperties implements Serializable {

	private final static long serialVersionUID = -1795139796444219186L;

	/**
	 * Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster. Applies to all components unless overridden.
	 */
	@RequiredProperty
	private List<String> bootstrapServers = ProducerFactory.DEFAULT_BOOTSTRAP_SERVERS;

	/**
	 * ID to pass to the server when making requests. Used for server-side logging.
	 */
	private String clientId;

	/**
	 * Topic 名称
	 */
	@RequiredProperty
	private String topic;

	/**
	 * 事务 ID 前缀
	 */
	private String transactionIdPrefix;

	/**
	 * Number of acknowledgments the producer requires the leader to have received
	 * before considering a request complete.
	 */
	private String acks;

	/**
	 * Default batch size.
	 * <p>
	 * A small batch size will make batching less common and may reduce throughput (a batch size of zero disables batching entirely).
	 * </p>
	 */
	private DataSize batchSize;

	/**
	 * Total memory size the producer can use to buffer records waiting to be sent to
	 * the server.
	 */
	private DataSize bufferMemory;

	/**
	 * Compression type for all data generated by the producer.
	 */
	private String compressionType;

	/**
	 * 重试次数
	 */
	private Integer retries = 3;

	/**
	 * SSL 配置 {@link com.buession.logging.kafka.config.SslConfiguration}
	 */
	private com.buession.logging.kafka.config.SslConfiguration sslConfiguration = new com.buession.logging.kafka.config.SslConfiguration();

	/**
	 * 安全配置 {@link SecurityConfiguration}
	 */
	private SecurityConfiguration securityConfiguration = new SecurityConfiguration();

	/**
	 * Additional properties, common to producers and consumers, used to configure the client.
	 */
	private Map<String, String> properties = new HashMap<>();

	/**
	 * Return Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 *
	 * @return Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 */
	public List<String> getBootstrapServers() {
		return bootstrapServers;
	}

	/**
	 * Sets Comma-delimited list of host:port pairs to use for establishing the initial
	 * connections to the Kafka cluster.
	 *
	 * @param bootstrapServers
	 * 		Comma-delimited list of host:port pairs to use for establishing the initial
	 * 		connections to the Kafka cluster.
	 */
	public void setBootstrapServers(List<String> bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	/**
	 * Return ID to pass to the server when making requests. Used for server-side logging.
	 *
	 * @return ID to pass to the server when making requests.
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets ID to pass to the server when making requests. Used for server-side logging.
	 *
	 * @param clientId
	 * 		ID to pass to the server when making requests.
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * 返回 Topic 名称
	 *
	 * @return Topic 名称
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 设置 Topic 名称
	 *
	 * @param topic
	 * 		Topic 名称
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * 返回事务 ID 前缀
	 *
	 * @return 事务 ID 前缀
	 */
	public String getTransactionIdPrefix() {
		return transactionIdPrefix;
	}

	/**
	 * 设置事务 ID 前缀
	 *
	 * @param transactionIdPrefix
	 * 		事务 ID 前缀
	 */
	public void setTransactionIdPrefix(String transactionIdPrefix) {
		this.transactionIdPrefix = transactionIdPrefix;
	}

	/**
	 * Return number of acknowledgments the producer requires the leader to have received before considering a
	 * request complete.
	 *
	 * @return Number of acknowledgments the producer requires the leader to have received before considering a
	 * request complete.
	 */
	public String getAcks() {
		return acks;
	}

	/**
	 * Sets number of acknowledgments the producer requires the leader to have received before considering a request complete.
	 *
	 * @param acks
	 * 		Number of acknowledgments the producer requires the leader to have received before considering a request complete.
	 */
	public void setAcks(String acks) {
		this.acks = acks;
	}

	/**
	 * Return default batch size.
	 * <p>
	 * A small batch size will make batching less common and may reduce throughput (a batch size of zero disables batching entirely).
	 * </p>
	 *
	 * @return Default batch size.
	 */
	public DataSize getBatchSize() {
		return batchSize;
	}

	/**
	 * Sets default batch size.
	 * <p>
	 * A small batch size will make batching less common and may reduce throughput (a batch size of zero disables batching entirely).
	 * </p>
	 *
	 * @param batchSize
	 * 		Default batch size.
	 */
	public void setBatchSize(DataSize batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * Return total memory size the producer can use to buffer records waiting to be sent to the server.
	 *
	 * @return Total memory size the producer can use to buffer records waiting to be sent to the server.
	 */
	public DataSize getBufferMemory() {
		return bufferMemory;
	}

	/**
	 * Sets total memory size the producer can use to buffer records waiting to be sent to the server.
	 *
	 * @param bufferMemory
	 * 		Total memory size the producer can use to buffer records waiting to be sent to the server.
	 */
	public void setBufferMemory(DataSize bufferMemory) {
		this.bufferMemory = bufferMemory;
	}

	/**
	 * Return compression type for all data generated by the producer.
	 *
	 * @return Compression type for all data generated by the producer.
	 */
	public String getCompressionType() {
		return compressionType;
	}

	/**
	 * Sets compression type for all data generated by the producer.
	 *
	 * @param compressionType
	 * 		Compression type for all data generated by the producer.
	 */
	public void setCompressionType(String compressionType) {
		this.compressionType = compressionType;
	}

	/**
	 * 返回重试次数
	 *
	 * @return 重试次数
	 */
	public Integer getRetries() {
		return retries;
	}

	/**
	 * 设置重试次数
	 *
	 * @param retries
	 * 		重试次数
	 */
	public void setRetries(Integer retries) {
		this.retries = retries;
	}

	/**
	 * 返回 SSL 配置 {@link com.buession.logging.kafka.config.SslConfiguration}
	 *
	 * @return SSL 配置 {@link com.buession.logging.kafka.config.SslConfiguration}
	 */
	public com.buession.logging.kafka.config.SslConfiguration getSslConfiguration() {
		return sslConfiguration;
	}

	/**
	 * 设置 SSL 配置 {@link com.buession.logging.kafka.config.SslConfiguration}
	 *
	 * @param sslConfiguration
	 * 		SSL 配置 {@link com.buession.logging.kafka.config.SslConfiguration}
	 */
	public void setSslConfiguration(SslConfiguration sslConfiguration) {
		this.sslConfiguration = sslConfiguration;
	}

	/**
	 * 返回安全配置 {@link SecurityConfiguration}
	 *
	 * @return 安全配置 {@link SecurityConfiguration}
	 */
	public SecurityConfiguration getSecurityConfiguration() {
		return securityConfiguration;
	}

	/**
	 * 设置安全配置 {@link SecurityConfiguration}
	 *
	 * @param securityConfiguration
	 * 		安全配置 {@link SecurityConfiguration}
	 */
	public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
		this.securityConfiguration = securityConfiguration;
	}

	/**
	 * Return additional properties, common to producers and consumers, used to configure the client.
	 *
	 * @return Additional properties, common to producers and consumers, used to configure the client.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Sets additional properties, common to producers and consumers, used to configure the client.
	 *
	 * @param properties
	 * 		Additional properties, common to producers and consumers, used to configure the client.
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}