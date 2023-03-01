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

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The release policy that decides how attributes are to be released for a given service.
 * Each policy has the ability to apply an optional filter.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class AttributeReleasePolicy implements Entity {

	private final static long serialVersionUID = -674753050375803851L;

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
	public void setPrincipalAttributesRepository(
			PrincipalAttributesRepository principalAttributesRepository){
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

	public int getOrder(){
		return order;
	}

	public void setOrder(int order){
		this.order = order;
	}

	@Override
	public String toString(){
		return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
				.add("authorizedToReleaseAuthenticationAttributes", authorizedToReleaseAuthenticationAttributes)
				.add("authorizedToReleaseCredentialPassword", authorizedToReleaseCredentialPassword)
				.add("authorizedToReleaseProxyGrantingTicket", authorizedToReleaseProxyGrantingTicket)
				.add("excludeDefaultAttributes", excludeDefaultAttributes)
				.add("principalIdAttribute", principalIdAttribute)
				.add("principalAttributesRepository", principalAttributesRepository)
				.add("consentPolicy", consentPolicy)
				.add("order", order)
				.asString();
	}

	/**
	 * Defines operations required for retrieving principal attributes.
	 * Acts as a proxy between the external attribute source and CAS,
	 * executing such as additional processing or caching on the set
	 * of retrieved attributes. Implementations may simply decide to
	 * do nothing on the set of attributes that the principal carries
	 * or they may attempt to refresh them from the source, etc.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public abstract static class PrincipalAttributesRepository implements Entity {

		private final static long serialVersionUID = -309642305271488575L;

		/**
		 * The merging strategy that deals with existing principal attributes
		 * and those that are retrieved from the source. By default, existing attributes
		 * are ignored and the source is always consulted.
		 */
		private AttributeMergingStrategy mergingStrategy = AttributeMergingStrategy.MULTIVALUED;

		/**
		 * The attribute repository ids.
		 */
		private Set<String> attributeRepositoryIds;

		private boolean ignoreResolvedAttributes;

		public AttributeMergingStrategy getMergingStrategy(){
			return this.mergingStrategy;
		}

		public void setMergingStrategy(final AttributeMergingStrategy mergingStrategy){
			this.mergingStrategy = mergingStrategy;
		}

		/**
		 * Return attribute repository ids that should be used to fetch attributes.
		 * An empty collection indicates that all sources available and defined should be used.
		 *
		 * @return The attribute repository ids.
		 */
		public Set<String> getAttributeRepositoryIds(){
			return this.attributeRepositoryIds;
		}

		/**
		 * Sets attribute repository ids that should be used to fetch attributes.
		 *
		 * @param attributeRepositoryIds
		 * 		The attribute repository ids.
		 */
		public void setAttributeRepositoryIds(final Set<String> attributeRepositoryIds){
			this.attributeRepositoryIds = attributeRepositoryIds;
		}

		public boolean isIgnoreResolvedAttributes(){
			return this.ignoreResolvedAttributes;
		}

		public void setIgnoreResolvedAttributes(final boolean ignoreResolvedAttributes){
			this.ignoreResolvedAttributes = ignoreResolvedAttributes;
		}

		@Override
		public String toString(){
			return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
					.add("mergingStrategy", mergingStrategy)
					.add("attributeRepositoryIds", attributeRepositoryIds)
					.add("ignoreResolvedAttributes", ignoreResolvedAttributes)
					.asString();
		}

		/**
		 * Default implementation of {@link PrincipalAttributesRepository}
		 * that just returns the attributes as it receives them.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public final static class DefaultPrincipalAttributesRepository extends PrincipalAttributesRepository {

			private final static long serialVersionUID = 2946822355047319380L;

			@Override
			public String toString(){
				return super.toString();
			}

		}

		/**
		 * Wrapper around an attribute repository where attributes cached for a configurable period
		 * based on google guava's caching library.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public final static class CachingPrincipalAttributesRepository extends PrincipalAttributesRepository {

			private final static long serialVersionUID = 635669154384144165L;

			/**
			 * The expiration time.
			 */
			private long expiration;

			/**
			 * Expiration time unit.
			 */
			private String timeUnit;

			public long getExpiration(){
				return this.expiration;
			}

			public void setExpiration(final long expiration){
				this.expiration = expiration;
			}

			public String getTimeUnit(){
				return this.timeUnit;
			}

			public void setTimeUnit(final String timeUnit){
				this.timeUnit = timeUnit;
			}

			@Override
			public String toString(){
				return new StringJoiner(", ")
						.add(super.toString())
						.add("expiration=" + expiration)
						.add("timeUnit=" + timeUnit)
						.toString();
			}

		}

	}

	/**
	 * This is {@link ConsentPolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	public abstract static class ConsentPolicy implements Entity {

		private final static long serialVersionUID = 2255930130377726557L;

		/**
		 * This is {@link DefaultRegisteredServiceConsentPolicy}.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public final static class DefaultRegisteredServiceConsentPolicy extends ConsentPolicy {

			private final static long serialVersionUID = -3309241770227298790L;

			/**
			 * Indicate whether consent is enabled.
			 */
			private TriStateBoolean status;

			/**
			 * The excluded attributes.
			 */
			private Set<String> excludedAttributes;

			/**
			 * The include-only attributes.
			 */
			private Set<String> includeOnlyAttributes;

			private int order;

			/**
			 * Indicate whether consent is enabled.
			 *
			 * @return true/false/undefined
			 */
			public TriStateBoolean getStatus(){
				return this.status;
			}

			/**
			 * Sets indicate whether consent is enabled.
			 *
			 * @param status
			 * 		true/false/undefined
			 */
			public void setStatus(TriStateBoolean status){
				this.status = status;
			}

			/**
			 * Return excluded attributes. Excludes the set of specified attributes from consent.
			 *
			 * @return The excluded attributes.
			 */
			public Set<String> getExcludedAttributes(){
				return excludedAttributes;
			}

			/**
			 * Sets excluded attributes.
			 *
			 * @param excludedAttributes
			 * 		The excluded attributes.
			 */
			public void setExcludedAttributes(Set<String> excludedAttributes){
				this.excludedAttributes = excludedAttributes;
			}

			/**
			 * Return include-only attributes.
			 * If specified, consent should only be applied to the listed attributes
			 * and not everything the attribute release policy may indicate.
			 *
			 * @return The include-only attributes.
			 */
			public Set<String> getIncludeOnlyAttributes(){
				return includeOnlyAttributes;
			}

			/**
			 * Sets include-only attributes.
			 *
			 * @param includeOnlyAttributes
			 * 		The include-only attributes.
			 */
			public void setIncludeOnlyAttributes(Set<String> includeOnlyAttributes){
				this.includeOnlyAttributes = includeOnlyAttributes;
			}

			public int getOrder(){
				return this.order;
			}

			public void setOrder(int order){
				this.order = order;
			}

			@Override
			public String toString(){
				return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
						.add("status", status)
						.add("excludedAttributes", excludedAttributes)
						.add("includeOnlyAttributes", includeOnlyAttributes)
						.add("order", order)
						.asString();
			}

		}

		/**
		 * This is {@link ChainingRegisteredServiceConsentPolicy}.
		 *
		 * @author Yong.Teng
		 * @since 2.2.0
		 */
		public final static class ChainingRegisteredServiceConsentPolicy extends ConsentPolicy {

			private final static long serialVersionUID = 3426994820483370591L;

			private List<ConsentPolicy> policies;

			public List<ConsentPolicy> getPolicies(){
				return policies;
			}

			public void setPolicies(List<ConsentPolicy> policies){
				this.policies = policies;
			}

			@Override
			public String toString(){
				return ToStringBuilder.BaseEntityToStringBuilder.getInstance(this)
						.add("policies", policies)
						.asString();
			}

		}

	}

	/**
	 * A deny rule to refuse all service from receiving attributes, whether default or not.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class DenyAllAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = -982993369711200326L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

	/**
	 * A deny rule to refuse all service from receiving attributes, whether default or not.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class GroovyScriptAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = -8327471511863649000L;

		private String groovyScript;

		public String getGroovyScript(){
			return groovyScript;
		}

		public void setGroovyScript(String groovyScript){
			this.groovyScript = groovyScript;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("groovyScript=" + groovyScript)
					.toString();
		}

	}

	/**
	 * Return all attributes for the service, regardless of service settings.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class ReturnAllAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = 8532191617133542127L;

		@Override
		public String toString(){
			return super.toString();
		}

	}

	/**
	 * Return only the collection of allowed attributes out of what's resolved for the principal.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class ReturnAllowedAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = 526227455320301888L;

		private Set<String> allowedAttributes;

		public Set<String> getAllowedAttributes(){
			return allowedAttributes;
		}

		public void setAllowedAttributes(Set<String> allowedAttributes){
			this.allowedAttributes = allowedAttributes;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("allowedAttributes=" + allowedAttributes)
					.toString();
		}

	}

	/**
	 * Return only the collection of allowed attributes out of what's resolved for the principal.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class ReturnEncryptedAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = -3892481828451957682L;

		private Set<String> allowedAttributes;

		public Set<String> getAllowedAttributes(){
			return allowedAttributes;
		}

		public void setAllowedAttributes(Set<String> allowedAttributes){
			this.allowedAttributes = allowedAttributes;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("allowedAttributes=" + allowedAttributes)
					.toString();
		}

	}

	/**
	 * Return a collection of allowed attributes for the principal, but additionally, offers the ability to rename
	 * attributes on a per-service level.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class ReturnMappedAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = 1670060040353066962L;

		private Set<String> allowedAttributes;

		public Set<String> getAllowedAttributes(){
			return allowedAttributes;
		}

		public void setAllowedAttributes(Set<String> allowedAttributes){
			this.allowedAttributes = allowedAttributes;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("allowedAttributes=" + allowedAttributes)
					.toString();
		}

	}

	/**
	 * Return a collection of allowed attributes for the principal based on an external REST endpoint.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	public final static class ReturnRestfulAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = 612277202929266535L;

		private String endpoint;

		public String getEndpoint(){
			return endpoint;
		}

		public void setEndpoint(String endpoint){
			this.endpoint = endpoint;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("endpoint=" + endpoint)
					.toString();
		}

	}

	/**
	 * This is {@link ScriptedRegisteredServiceAttributeReleasePolicy}.
	 *
	 * @author Yong.Teng
	 * @since 2.2.0
	 */
	@Deprecated
	public final static class ScriptedRegisteredServiceAttributeReleasePolicy extends AttributeReleasePolicy {

		private final static long serialVersionUID = -8257604978544917554L;

		private String scriptFile;

		public String getScriptFile(){
			return scriptFile;
		}

		public void setScriptFile(String scriptFile){
			this.scriptFile = scriptFile;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ")
					.add(super.toString())
					.add("scriptFile=" + scriptFile)
					.toString();
		}

	}

}
