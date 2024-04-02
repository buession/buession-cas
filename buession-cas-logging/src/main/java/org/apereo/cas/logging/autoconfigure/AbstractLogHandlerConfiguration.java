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
package org.apereo.cas.logging.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.logging.support.config.HandlerProperties;
import org.apereo.cas.logging.config.CasLoggingConfigurationProperties;

/**
 * @author Yong.Teng
 * @since 2.3.0
 */
public abstract class AbstractLogHandlerConfiguration {

	protected final static PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

	public static class AbstractBasicLogHandlerConfiguration<P extends HandlerProperties>
			extends AbstractLogHandlerConfiguration {

		public final static String PREFIX = CasLoggingConfigurationProperties.PREFIX + ".basic";

		public final static String LOG_HANDLER_BEAN_NAME = "basicLoggingLogHandler";

		protected final P handlerProperties;

		public AbstractBasicLogHandlerConfiguration(final P handlerProperties) {
			this.handlerProperties = handlerProperties;
		}

	}

	public static class AbstractHistoryLogHandlerConfiguration<P extends HandlerProperties>
			extends AbstractLogHandlerConfiguration {

		public final static String PREFIX = CasLoggingConfigurationProperties.PREFIX + ".history";

		public final static String LOG_HANDLER_BEAN_NAME = "historyLoggingLogHandler";

		protected final P handlerProperties;

		public AbstractHistoryLogHandlerConfiguration(final P handlerProperties) {
			this.handlerProperties = handlerProperties;
		}

	}

}