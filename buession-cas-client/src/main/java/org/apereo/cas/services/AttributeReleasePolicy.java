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
package org.apereo.cas.services;

import org.apereo.cas.entity.Entity;
import org.apereo.cas.authentication.principal.PrincipalAttributesRepository;

/**
 * The release policy that decides how attributes are to be released for a given service.
 * Each policy has the ability to apply an optional filter.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public interface AttributeReleasePolicy extends Entity {

	/**
	 * Abstract release policy for attributes,
	 * provides common shared settings such as loggers and attribute filter config.
	 * Subclasses are to provide the behavior for attribute retrieval.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	abstract class AbstractAttributeReleasePolicy implements AttributeReleasePolicy {

		private final static long serialVersionUID = -3328079607703733349L;

		/**
		 * Is authorized to release authentication attributes boolean.
		 */
		private boolean authorizedToReleaseAuthenticationAttributes = true;

		/**
		 * Is authorized to release credential password.
		 */
		private boolean authorizedToReleaseCredentialPassword;

		/**
		 * Is authorized to release proxy granting ticket.
		 */
		private boolean authorizedToReleaseProxyGrantingTicket;

		private boolean excludeDefaultAttributes;

		private String principalIdAttribute;

		/**
		 * The principal attribute repository.
		 */
		private PrincipalAttributesRepository principalAttributesRepository;

		/**
		 * The consent policy.
		 */
		private ConsentPolicy consentPolicy;

		private AttributeFilter attributeFilter;

		private int order;

		/**
		 * Return is authorized to release authentication attributes boolean.
		 *
		 * @return true/false
		 */
		public boolean isAuthorizedToReleaseAuthenticationAttributes(){
			return authorizedToReleaseAuthenticationAttributes;
		}

		/**
		 * Sets is authorized to release authentication attributes boolean.
		 *
		 * @param authorizedToReleaseAuthenticationAttributes
		 * 		true/false
		 */
		public void setAuthorizedToReleaseAuthenticationAttributes(boolean authorizedToReleaseAuthenticationAttributes){
			this.authorizedToReleaseAuthenticationAttributes = authorizedToReleaseAuthenticationAttributes;
		}

		/**
		 * Return is authorized to release credential password?
		 *
		 * @return true/false
		 */
		public boolean isAuthorizedToReleaseCredentialPassword(){
			return authorizedToReleaseCredentialPassword;
		}

		/**
		 * Sets is authorized to release credential password
		 *
		 * @param authorizedToReleaseCredentialPassword
		 * 		true/false
		 */
		public void setAuthorizedToReleaseCredentialPassword(boolean authorizedToReleaseCredentialPassword){
			this.authorizedToReleaseCredentialPassword = authorizedToReleaseCredentialPassword;
		}

		/**
		 * Return is authorized to release proxy granting ticket?
		 *
		 * @return true/false
		 */
		public boolean isAuthorizedToReleaseProxyGrantingTicket(){
			return authorizedToReleaseProxyGrantingTicket;
		}

		/**
		 * Sets is authorized to release proxy granting ticket?
		 *
		 * @param authorizedToReleaseProxyGrantingTicket
		 * 		true/false
		 */
		public void setAuthorizedToReleaseProxyGrantingTicket(boolean authorizedToReleaseProxyGrantingTicket){
			this.authorizedToReleaseProxyGrantingTicket = authorizedToReleaseProxyGrantingTicket;
		}

		public boolean isExcludeDefaultAttributes(){
			return this.excludeDefaultAttributes;
		}

		public void setExcludeDefaultAttributes(final boolean excludeDefaultAttributes){
			this.excludeDefaultAttributes = excludeDefaultAttributes;
		}

		public String getPrincipalIdAttribute(){
			return principalIdAttribute;
		}

		public void setPrincipalIdAttribute(String principalIdAttribute){
			this.principalIdAttribute = principalIdAttribute;
		}

		/**
		 * Return principal attribute repository that may control the fetching
		 * and caching of attributes at release time from attribute repository sources.
		 *
		 * @return The principal attribute repository.
		 */
		public PrincipalAttributesRepository getPrincipalAttributesRepository(){
			return principalAttributesRepository;
		}

		/**
		 * Set principal attribute repository that may control the fetching
		 * and caching of attributes at release time from attribute repository sources.
		 *
		 * @param principalAttributesRepository
		 * 		The principal attribute repository.
		 */
		public void setPrincipalAttributesRepository(PrincipalAttributesRepository principalAttributesRepository){
			this.principalAttributesRepository = principalAttributesRepository;
		}

		/**
		 * Return consent policy.
		 *
		 * @return The consent policy.
		 */
		public ConsentPolicy getConsentPolicy(){
			return consentPolicy;
		}

		/**
		 * Sets consent policy.
		 *
		 * @param consentPolicy
		 * 		The consent policy.
		 */
		public void setConsentPolicy(ConsentPolicy consentPolicy){
			this.consentPolicy = consentPolicy;
		}

		public AttributeFilter getAttributeFilter(){
			return attributeFilter;
		}

		public void setAttributeFilter(AttributeFilter attributeFilter){
			this.attributeFilter = attributeFilter;
		}

		public int getOrder(){
			return order;
		}

		public void setOrder(int order){
			this.order = order;
		}

		@Override
		public String toString(){
			return StringBuilder.getInstance(this)
					.add("authorizedToReleaseAuthenticationAttributes", authorizedToReleaseAuthenticationAttributes)
					.add("authorizedToReleaseCredentialPassword", authorizedToReleaseCredentialPassword)
					.add("authorizedToReleaseProxyGrantingTicket", authorizedToReleaseProxyGrantingTicket)
					.add("excludeDefaultAttributes", excludeDefaultAttributes)
					.add("principalIdAttribute", principalIdAttribute)
					.add("principalAttributesRepository", principalAttributesRepository)
					.add("consentPolicy", consentPolicy)
					.add("attributeFilter", attributeFilter)
					.add("order", order)
					.asString();
		}

	}

}
