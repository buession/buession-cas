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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package org.apereo.cas.configuration.model.support.logging;

import org.springframework.util.unit.DataSize;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * RocketMQ 日志配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class RocketMQProperties implements AdapterLoggingProperties {

	/**
	 * The name server for rocketMQ, formats: `host:port;host:port`.
	 */
	private String nameServer = "localhost:9876";

	/**
	 * Topic 名称
	 */
	private String topic;

	/**
	 * Group name of producer.
	 */
	private String groupName;

	/**
	 * Namespace for this MQ Producer instance.
	 */
	private String namespace;

	/**
	 * The namespace v2 version of producer, it can not be used in combination with namespace.
	 */
	private String namespaceV2;

	/**
	 * The property of "instanceName".
	 */
	private String instanceName = "DEFAULT";

	/**
	 * The property of "access-key".
	 */
	private String accessKey;

	/**
	 * The property of "secret-key".
	 */
	private String secretKey;

	private AccessChannel accessChannel;

	private Charset charset = StandardCharsets.UTF_8;

	/**
	 * Millis of send message timeout.
	 */
	private Duration sendMessageTimeout = Duration.ofMillis(3000);

	/**
	 * Maximum allowed message size.
	 */
	private DataSize maxMessageSize = DataSize.ofBytes(1024 * 1024 * 4);

	/**
	 * Compress message body threshold, namely, message body larger than 4k will be compressed on default.
	 */
	private DataSize compressMessageBodyThreshold = DataSize.ofBytes(1024 * 4);

	/**
	 * Indicate whether to retry another broker on sending failure internally.
	 */
	private boolean retryNextServer = false;

	/**
	 * Maximum number of retry to perform internally before claiming sending failure in synchronous mode.
	 * This may potentially cause message duplication which is up to application developers to resolve.
	 */
	private int retryTimesWhenSendFailed = 2;

	/**
	 * <p> Maximum number of retry to perform internally before claiming sending failure in asynchronous mode. </p>
	 * This may potentially cause message duplication which is up to application developers to resolve.
	 */
	private int retryTimesWhenSendAsyncFailed = 2;

	/**
	 * Switch flag instance for message trace.
	 */
	private boolean enableMsgTrace = false;

	/**
	 * The name value of message trace topic.If you don't config,you can use the default trace topic name.
	 */
	private String customizedTraceTopic = "RMQ_SYS_TRACE_TOPIC";

	/**
	 * 是否同步
	 */
	private boolean sync = false;

	/**
	 * The property of "tlsEnable".
	 */
	private boolean tlsEnable = false;

	public String getNameServer() {
		return nameServer;
	}

	public void setNameServer(String nameServer) {
		this.nameServer = nameServer;
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
	 * 获取生产者组名
	 *
	 * @return 生产者组名
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 设置生产者组名
	 *
	 * @param groupName
	 * 		生产者组名
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 获取命名空间
	 *
	 * @return 命名空间
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 设置命名空间
	 *
	 * @param namespace
	 * 		命名空间
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 获取 V2 版本命名空间
	 *
	 * @return V2 版本命名空间
	 */
	public String getNamespaceV2() {
		return namespaceV2;
	}

	/**
	 * 设置 V2 版本命名空间
	 *
	 * @param namespaceV2
	 * 		V2 版本命名空间
	 */
	public void setNamespaceV2(String namespaceV2) {
		this.namespaceV2 = namespaceV2;
	}

	/**
	 * 获取实例名称
	 *
	 * @return 实例名称
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * 设置实例名称
	 *
	 * @param instanceName
	 * 		实例名称
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * 获取 Access Key
	 *
	 * @return Access Key
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * 设置 Access Key
	 *
	 * @param accessKey
	 * 		Access Key
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * 获取 Secret Key
	 *
	 * @return Secret Key
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * 设置 Secret Key
	 *
	 * @param secretKey
	 * 		Secret Key
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public AccessChannel getAccessChannel() {
		return accessChannel;
	}

	public void setAccessChannel(AccessChannel accessChannel) {
		this.accessChannel = accessChannel;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * 获取发送消息超时时间
	 *
	 * @return 发送消息超时时间
	 */
	public Duration getSendMessageTimeout() {
		return sendMessageTimeout;
	}

	/**
	 * 设置发送消息超时时间（
	 *
	 * @param sendMessageTimeout
	 * 		发送消息超时时间
	 */
	public void setSendMessageTimeout(Duration sendMessageTimeout) {
		this.sendMessageTimeout = sendMessageTimeout;
	}

	/**
	 * 获取允许的最大消息大小
	 *
	 * @return 允许的最大消息大小
	 */
	public DataSize getMaxMessageSize() {
		return maxMessageSize;
	}

	/**
	 * 设置允许的最大消息大小
	 *
	 * @param maxMessageSize
	 * 		允许的最大消息大小
	 */
	public void setMaxMessageSize(DataSize maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}

	/**
	 * 获取消息体压缩阈值
	 *
	 * @return 消息体压缩阈值
	 */
	public DataSize getCompressMessageBodyThreshold() {
		return compressMessageBodyThreshold;
	}

	/**
	 * 设置消息体压缩阈值
	 *
	 * @param compressMessageBodyThreshold
	 * 		消息体压缩阈值
	 */
	public void setCompressMessageBodyThreshold(DataSize compressMessageBodyThreshold) {
		this.compressMessageBodyThreshold = compressMessageBodyThreshold;
	}

	/**
	 * 获取是否在发送失败时重试下一个服务器
	 *
	 * @return 是否重试下一个服务器
	 */
	public boolean isRetryNextServer() {
		return retryNextServer;
	}

	/**
	 * 设置是否在发送失败时重试下一个服务器
	 *
	 * @param retryNextServer
	 * 		是否重试下一个服务器
	 */
	public void setRetryNextServer(boolean retryNextServer) {
		this.retryNextServer = retryNextServer;
	}

	/**
	 * 获取同步发送失败时的最大重试次数
	 *
	 * @return 同步发送失败时的最大重试次数
	 */
	public int getRetryTimesWhenSendFailed() {
		return retryTimesWhenSendFailed;
	}

	/**
	 * 设置同步发送失败时的最大重试次数
	 *
	 * @param retryTimesWhenSendFailed
	 * 		同步发送失败时的最大重试次数
	 */
	public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
		this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
	}

	/**
	 * 获取异步发送失败时的最大重试次数
	 *
	 * @return 异步发送失败时的最大重试次数
	 */
	public int getRetryTimesWhenSendAsyncFailed() {
		return retryTimesWhenSendAsyncFailed;
	}

	/**
	 * 设置异步发送失败时的最大重试次数
	 *
	 * @param retryTimesWhenSendAsyncFailed
	 * 		异步发送失败时的最大重试次数
	 */
	public void setRetryTimesWhenSendAsyncFailed(int retryTimesWhenSendAsyncFailed) {
		this.retryTimesWhenSendAsyncFailed = retryTimesWhenSendAsyncFailed;
	}

	/**
	 * 获取是否启用消息轨迹
	 *
	 * @return 是否启用消息轨迹
	 */
	public boolean isEnableMsgTrace() {
		return enableMsgTrace;
	}

	/**
	 * 设置是否启用消息轨迹
	 *
	 * @param enableMsgTrace
	 * 		是否启用消息轨迹
	 */
	public void setEnableMsgTrace(boolean enableMsgTrace) {
		this.enableMsgTrace = enableMsgTrace;
	}

	/**
	 * 获取自定义消息轨迹主题名称
	 *
	 * @return 自定义消息轨迹主题名称
	 */
	public String getCustomizedTraceTopic() {
		return customizedTraceTopic;
	}

	/**
	 * 设置自定义消息轨迹主题名称
	 *
	 * @param customizedTraceTopic
	 * 		自定义消息轨迹主题名称
	 */
	public void setCustomizedTraceTopic(String customizedTraceTopic) {
		this.customizedTraceTopic = customizedTraceTopic;
	}

	/**
	 * 返回是否同步
	 *
	 * @return 是否同步
	 */
	public boolean isSync() {
		return sync;
	}

	/**
	 * 设置是否同步
	 *
	 * @param sync
	 * 		是否同步
	 */
	public void setSync(boolean sync) {
		this.sync = sync;
	}

	/**
	 * 获取是否启用 TLS
	 *
	 * @return 是否启用 TLS
	 */
	public boolean isTlsEnable() {
		return tlsEnable;
	}

	/**
	 * 设置是否启用 TLS
	 *
	 * @param tlsEnable
	 * 		是否启用 TLS
	 */
	public void setTlsEnable(boolean tlsEnable) {
		this.tlsEnable = tlsEnable;
	}

	public enum AccessChannel {
		/**
		 * Means connect to private IDC cluster.
		 */
		LOCAL,

		/**
		 * Means connect to Cloud service.
		 */
		CLOUD,
	}

}
