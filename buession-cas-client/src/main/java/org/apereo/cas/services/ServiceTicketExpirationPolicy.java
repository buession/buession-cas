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
 * This is {@link ServiceTicketExpirationPolicy}. This contract allows applications registered with CAS to define
 * an expiration policy for service tickets as to override the default timeouts and settings applied globally.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class ServiceTicketExpirationPolicy implements Entity {

	private final static long serialVersionUID = 8475522497381185311L;

	/**
	 * This is {@link DefaultRegisteredServiceServiceTicketExpirationPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public static class DefaultRegisteredServiceServiceTicketExpirationPolicy
			extends ServiceTicketExpirationPolicy {

		private final static long serialVersionUID = 753310162992981920L;

		/**
		 * The number of uses.
		 */
		private long numberOfUses;

		/**
		 * The time to live of this ticket.
		 */
		private String timeToLive;

		/**
		 * Return number of times this ticket can be used.
		 *
		 * @return The number of uses.
		 */
		public long getNumberOfUses(){
			return numberOfUses;
		}

		/**
		 * Sets number of times this ticket can be used.
		 *
		 * @param numberOfUses
		 * 		The number of uses.
		 */
		public void setNumberOfUses(long numberOfUses){
			this.numberOfUses = numberOfUses;
		}

		/**
		 * Get the TTL of this ticket, in seconds.
		 *
		 * @return The time to live of this ticket.
		 */
		public String getTimeToLive(){
			return timeToLive;
		}

		/**
		 * Sets the TTL of this ticket, in seconds.
		 *
		 * @param timeToLive
		 * 		The time to live of this ticket.
		 */
		public void setTimeToLive(String timeToLive){
			this.timeToLive = timeToLive;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.add("numberOfUses", numberOfUses)
					.add("timeToLive", timeToLive)
					.asString();
		}

	}

}
