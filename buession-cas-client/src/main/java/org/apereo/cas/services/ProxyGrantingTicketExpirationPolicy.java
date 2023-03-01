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
package org.apereo.cas.services.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apereo.cas.services.Entity;
import org.apereo.cas.services.utils.ToStringBuilder;

/**
 * This is {@link ProxyGrantingTicketExpirationPolicy}. This contract allows applications registered
 * with CAS to define an expiration policy for proxy granting tickets as to override
 * the default ticket granting ticket expiration policy.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class ProxyGrantingTicketExpirationPolicy implements Entity {

	private final static long serialVersionUID = 1770081580565130616L;

	/**
	 * This is {@link DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class DefaultRegisteredServiceProxyGrantingTicketExpirationPolicy
			extends ProxyGrantingTicketExpirationPolicy {

		/**
		 * The time to live of this ticket.
		 */
		private long maxTimeToLiveInSeconds;

		/**
		 * Return the TTL of this ticket, in seconds.
		 *
		 * @return The time to live of this ticket.
		 */
		public long getMaxTimeToLiveInSeconds(){
			return maxTimeToLiveInSeconds;
		}

		/**
		 * Set the TTL of this ticket, in seconds.
		 *
		 * @param maxTimeToLiveInSeconds
		 * 		The time to live of this ticket.
		 */
		public void setMaxTimeToLiveInSeconds(long maxTimeToLiveInSeconds){
			this.maxTimeToLiveInSeconds = maxTimeToLiveInSeconds;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.of("maxTimeToLiveInSeconds", maxTimeToLiveInSeconds);
		}

	}

}
