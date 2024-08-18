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
package org.apereo.cas.logging.autoconfigure;

import com.buession.core.converter.mapper.PropertyMapper;
import org.apereo.cas.configuration.model.support.logging.LoggingProperties;
import org.apereo.cas.configuration.model.support.logging.AdapterLoggingProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
public abstract class BaseHandlerConfiguration {

	protected final ConfigurableApplicationContext applicationContext;

	protected final LoggingProperties loggingProperties;

	protected final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

	public BaseHandlerConfiguration(final ConfigurableApplicationContext applicationContext,
									final LoggingProperties loggingProperties) {
		this.applicationContext = applicationContext;
		this.loggingProperties = loggingProperties;
	}

	public abstract static class BaseAdapterHandlerConfiguration<PROPS extends AdapterLoggingProperties> {

		protected final ConfigurableApplicationContext applicationContext;

		protected PROPS properties;

		protected final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

		public BaseAdapterHandlerConfiguration(final ConfigurableApplicationContext applicationContext,
											   final PROPS properties) {
			this.applicationContext = applicationContext;
			this.properties = properties;
		}

	}

}
