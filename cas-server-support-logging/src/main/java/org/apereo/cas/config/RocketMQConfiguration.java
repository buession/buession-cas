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
package org.apereo.cas.config;

import com.buession.logging.rocketmq.spring.config.AbstractRocketMQConfiguration;
import com.buession.logging.rocketmq.spring.config.RocketMQConfigurer;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketTemplate;
import org.apereo.cas.configuration.model.support.logging.RocketMQLoggingProperties;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public class RocketMQConfiguration extends AbstractRocketMQConfiguration {

	private final RocketMQLoggingProperties rocketMQLoggingProperties;

	public RocketMQConfiguration(final RocketMQLoggingProperties rocketMQLoggingProperties) {
		this.rocketMQLoggingProperties = rocketMQLoggingProperties;
	}

	public RocketTemplate rocketTemplate() throws Exception {
		final RocketMQConfigurer configurer = rocketMQConfigurer();
		final DefaultMQProducer producer = defaultMQProducer(configurer);
		final RocketTemplate rocketTemplate = super.rocketTemplate(configurer);

		rocketTemplate.setProducer(producer);

		return rocketTemplate;
	}

	private RocketMQConfigurer rocketMQConfigurer() {
		final RocketMQConfigurer configurer = new RocketMQConfigurer();

		configurer.setNameServer(rocketMQLoggingProperties.getNameServer());
		configurer.setGroup(rocketMQLoggingProperties.getGroupName());
		configurer.setNamespace(rocketMQLoggingProperties.getNamespace());
		configurer.setNamespaceV2(rocketMQLoggingProperties.getNamespaceV2());
		configurer.setInstanceName(rocketMQLoggingProperties.getInstanceName());
		configurer.setAccessKey(rocketMQLoggingProperties.getAccessKey());
		configurer.setSecretKey(rocketMQLoggingProperties.getSecretKey());
		if(rocketMQLoggingProperties.getAccessChannel() == RocketMQLoggingProperties.AccessChannel.CLOUD){
			configurer.setAccessChannel(AccessChannel.CLOUD);
		}else if(rocketMQLoggingProperties.getAccessChannel() == RocketMQLoggingProperties.AccessChannel.LOCAL){
			configurer.setAccessChannel(AccessChannel.LOCAL);
		}
		configurer.setCharset(rocketMQLoggingProperties.getCharset());
		configurer.setSendMessageTimeout((int) rocketMQLoggingProperties.getSendMessageTimeout().toMillis());
		configurer.setMaxMessageSize((int) rocketMQLoggingProperties.getMaxMessageSize().toBytes());
		configurer.setCompressMessageBodyThreshold(
				(int) rocketMQLoggingProperties.getCompressMessageBodyThreshold().toBytes());
		configurer.setRetryNextServer(rocketMQLoggingProperties.isRetryNextServer());
		configurer.setRetryTimesWhenSendFailed(rocketMQLoggingProperties.getRetryTimesWhenSendFailed());
		configurer.setRetryTimesWhenSendAsyncFailed(rocketMQLoggingProperties.getRetryTimesWhenSendAsyncFailed());
		configurer.setEnableMsgTrace(rocketMQLoggingProperties.isEnableMsgTrace());
		configurer.setCustomizedTraceTopic(rocketMQLoggingProperties.getCustomizedTraceTopic());
		configurer.setTlsEnable(rocketMQLoggingProperties.isTlsEnable());

		return configurer;
	}

}
