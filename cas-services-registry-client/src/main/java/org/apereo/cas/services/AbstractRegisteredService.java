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
package org.apereo.cas.services;

import org.apereo.cas.services.annotation.JsonCollection;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Base class for mutable, persistable registered services.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public abstract class AbstractRegisteredService implements RegisteredService {

	private final static long serialVersionUID = 7399254599664082034L;

	/**
	 * The numeric identifier for this service.
	 */
	private int id;

	/**
	 * The name of the service.
	 */
	private String name;

	/**
	 * The unique identifier for this service.
	 */
	private String serviceId;

	/**
	 * The URL of the logo image.
	 */
	private String logo;

	/**
	 * The description of the service.
	 */
	private String description;

	/**
	 * Optional locale name that may be used to customize the CAS UI when the service requests a ticket.
	 */
	private String locale;

	/**
	 * The theme name associated with this service
	 */
	private String theme;

	/**
	 * Name of the template service definition to use as a blueprint, when constructing this service definition.
	 */
	private String templateName;

	/**
	 * The info url.
	 */
	private String informationUrl;

	/**
	 * The link to privacy policy.
	 */
	private String privacyUrl;

	/**
	 * The redirect url for this service.
	 */
	private String redirectUrl;

	/**
	 * The logout url for this service.
	 */
	private String logoutUrl;

	/**
	 * The list of Contacts.
	 */
	@JsonCollection
	private List<RegisteredServiceContact> contacts;

	/**
	 * The response type.
	 */
	private ResponseType responseType;

	/**
	 * The logout type of the service.
	 */
	private LogoutType logoutType;

	/**
	 * Non -null set of environment names.
	 */
	@JsonCollection
	private Set<String> environments;

	/**
	 * 代理策略
	 */
	private ProxyPolicy proxyPolicy;

	/**
	 * 参数返回策略
	 */
	private AttributeReleasePolicy attributeReleasePolicy;

	/**
	 * The expiration policy.
	 */
	private ExpirationPolicy expirationPolicy;

	/**
	 * The authentication policy.
	 */
	private AuthenticationPolicy authenticationPolicy;

	/**
	 * he instance of {@link AcceptableUsagePolicy} .
	 */
	private AcceptableUsagePolicy acceptableUsagePolicy;

	/**
	 * The multifactor authentication policy.
	 */
	private MultifactorPolicy multifactorPolicy;

	/**
	 * The proxy ticket expiration policy.
	 */
	private ProxyTicketExpirationPolicy proxyTicketExpirationPolicy;

	/**
	 * The ticket granting ticket expiration policy.
	 */
	private TicketGrantingTicketExpirationPolicy ticketGrantingTicketExpirationPolicy;

	/**
	 * The proxy granting ticket expiration policy.
	 */
	private ProxyGrantingTicketExpirationPolicy proxyGrantingTicketExpirationPolicy;

	/**
	 * The service ticket expiration policy.
	 */
	private ServiceTicketExpirationPolicy serviceTicketExpirationPolicy;

	/**
	 * The service ticket expiration policy.
	 */
	private SingleSignOnParticipationPolicy singleSignOnParticipationPolicy;

	/**
	 * The matching strategy.
	 */
	private MatchingStrategy matchingStrategy;

	/**
	 * The access strategy.
	 */
	private AccessStrategy accessStrategy;

	/**
	 * 用户名属性提供者
	 */
	private UsernameAttributeProvider usernameAttributeProvider;

	/**
	 * The public key instance used to authorize the request.
	 */
	private PublicKey publicKey;

	/**
	 * Specify supported and allowed protocols for this service.
	 */
	private Set<Protocol> supportedProtocols;

	/**
	 * The map of custom metadata.
	 */
	private Map<String, Property> properties;

	/**
	 * The service evaluation order.
	 */
	private int evaluationOrder;

	/**
	 * Return the numeric identifier for this service.
	 *
	 * @return The numeric identifier for this service.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the identifier for this service.
	 *
	 * @param id
	 * 		The numeric identifier for the service.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return the name of the service.
	 *
	 * @return The name of the service.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for this service.
	 *
	 * @param name
	 * 		The name of the service.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the unique identifier for this service.
	 *
	 * @return The unique identifier for this service.
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * Sets unique identifier for this service.
	 *
	 * @param serviceId
	 * 		The unique identifier for this service.
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * Return the logo image associated with this service.
	 *
	 * @return The URL of the logo image.
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Sets the URL of the logo image.
	 *
	 * @param logo
	 * 		The URL of the logo image.
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Return the description of the service.
	 *
	 * @return The description of the service.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the service.
	 *
	 * @param description
	 * 		The description of the service.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return optional locale name that may be used to customize the CAS UI when the service requests a ticket.
	 *
	 * @return Optional locale name that may be used to customize the CAS UI when the service requests a ticket.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Sets optional locale name that may be used to customize the CAS UI when the service requests a ticket.
	 *
	 * @param locale
	 * 		Optional locale name that may be used to customize the CAS UI when the service requests a ticket.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Return a short theme name. Services do not need to have unique theme names.
	 *
	 * @return The theme name associated with this service.
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * Sets the theme name associated with this service.
	 *
	 * @param theme
	 * 		The theme name associated with this service.
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Return name of the template service definition to use as a blueprint, when constructing this service definition.
	 *
	 * @return Name of the template service definition to use as a blueprint, when constructing this service definition.
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * Sets name of the template service definition to use as a blueprint, when constructing this service definition.
	 *
	 * @param templateName
	 * 		Name of the template service definition to use as a blueprint, when constructing this service definition.
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * Describes the canonical information url where this service is advertised and may provide help/guidance.
	 *
	 * @return The info url.
	 */
	public String getInformationUrl() {
		return informationUrl;
	}

	/**
	 * Sets the canonical information url where this service is advertised and may provide help/guidance.
	 *
	 * @param informationUrl
	 * 		The info url.
	 */
	public void setInformationUrl(String informationUrl) {
		this.informationUrl = informationUrl;
	}

	/**
	 * Return links to the privacy policy of this service, if any.
	 *
	 * @return The link to privacy policy.
	 */
	public String getPrivacyUrl() {
		return privacyUrl;
	}

	/**
	 * Sets the link to privacy policy.
	 *
	 * @param privacyUrl
	 * 		The link to privacy policy.
	 */
	public void setPrivacyUrl(String privacyUrl) {
		this.privacyUrl = privacyUrl;
	}

	/**
	 * Return the redirect url that will be used when building a response to authentication requests.
	 *
	 * @return The redirect url for this service.
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * Sets the redirect url that will be used when building a response to authentication requests.
	 *
	 * @param redirectUrl
	 * 		The redirect url for this service.
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/**
	 * Return the logout url that will be invoked upon sending single-logout callback notifications.
	 *
	 * @return The logout url for this service.
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Sets the logout url for this service.
	 *
	 * @param logoutUrl
	 * 		The logout url for this service.
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * Return a list of contacts that are responsible for the clients that use this service.
	 *
	 * @return The list of Contacts.
	 */
	public List<RegisteredServiceContact> getContacts() {
		return contacts;
	}

	/**
	 * Sets the list of Contacts.
	 *
	 * @param contacts
	 * 		The list of Contacts.
	 */
	public void setContacts(List<RegisteredServiceContact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Return response determines how CAS should contact the matching service
	 * typically with a ticket id. By default, the strategy is a 302 redirect.
	 *
	 * @return The response type.
	 */
	public ResponseType getResponseType() {
		return responseType;
	}

	/**
	 * Sets the response type.
	 *
	 * @param responseType
	 * 		The response type.
	 */
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	/**
	 * Returns the logout type of the service.
	 *
	 * @return The logout type of the service.
	 */
	public LogoutType getLogoutType() {
		return logoutType;
	}

	/**
	 * Sets the logout type of the service.
	 *
	 * @param logoutType
	 * 		The logout type of the service.
	 */
	public void setLogoutType(LogoutType logoutType) {
		this.logoutType = logoutType;
	}

	/**
	 * Return the set of names that correspond to the environment to which this service belongs.
	 *
	 * @return Non -null set of environment names.
	 */
	public Set<String> getEnvironments() {
		return environments;
	}

	/**
	 * Sets the set of names that correspond to the environment to which this service belongs.
	 *
	 * @param environments
	 * 		Non -null set of environment names.
	 */
	public void setEnvironments(Set<String> environments) {
		this.environments = environments;
	}

	/**
	 * 返回 Service 代理策略
	 *
	 * @return Service 代理策略
	 */
	public ProxyPolicy getProxyPolicy() {
		return proxyPolicy;
	}

	/**
	 * 设置 Service 代理策略
	 *
	 * @param proxyPolicy
	 * 		Service 代理策略
	 */
	public void setProxyPolicy(ProxyPolicy proxyPolicy) {
		this.proxyPolicy = proxyPolicy;
	}

	/**
	 * 返回参数返回策略
	 *
	 * @return 参数返回策略
	 */
	public AttributeReleasePolicy getAttributeReleasePolicy() {
		return attributeReleasePolicy;
	}

	/**
	 * 设置参数返回策略
	 *
	 * @param attributeReleasePolicy
	 * 		参数返回策略
	 */
	public void setAttributeReleasePolicy(AttributeReleasePolicy attributeReleasePolicy) {
		this.attributeReleasePolicy = attributeReleasePolicy;
	}

	/**
	 * Return the expiration policy rules for this service.
	 *
	 * @return The expiration policy..
	 */
	public ExpirationPolicy getExpirationPolicy() {
		return expirationPolicy;
	}

	/**
	 * Sets the expiration policy rules for this service.
	 *
	 * @param expirationPolicy
	 * 		The expiration policy.
	 */
	public void setExpirationPolicy(ExpirationPolicy expirationPolicy) {
		this.expirationPolicy = expirationPolicy;
	}

	/**
	 * Return the authentication policy assigned to this service.
	 *
	 * @return The authentication policy.
	 */
	public AuthenticationPolicy getAuthenticationPolicy() {
		return authenticationPolicy;
	}

	/**
	 * Sets the authentication policy assigned to this service.
	 *
	 * @param authenticationPolicy
	 * 		The authentication policy.
	 */
	public void setAuthenticationPolicy(AuthenticationPolicy authenticationPolicy) {
		this.authenticationPolicy = authenticationPolicy;
	}

	/**
	 * Return the acceptable usage policy linked to this application.
	 *
	 * @return The instance of {@link AcceptableUsagePolicy} .
	 */
	public AcceptableUsagePolicy getAcceptableUsagePolicy() {
		return acceptableUsagePolicy;
	}

	/**
	 * Sets the acceptable usage policy linked to this application.
	 *
	 * @param acceptableUsagePolicy
	 * 		The instance of {@link AcceptableUsagePolicy} .
	 */
	public void setAcceptableUsagePolicy(AcceptableUsagePolicy acceptableUsagePolicy) {
		this.acceptableUsagePolicy = acceptableUsagePolicy;
	}

	/**
	 * Return multifactor authentication policy.
	 *
	 * @return The multifactor authentication policy.
	 */
	public MultifactorPolicy getMultifactorPolicy() {
		return multifactorPolicy;
	}

	/**
	 * Sets multifactor authentication policy.
	 *
	 * @param multifactorPolicy
	 * 		The multifactor authentication policy.
	 */
	public void setMultifactorPolicy(MultifactorPolicy multifactorPolicy) {
		this.multifactorPolicy = multifactorPolicy;
	}

	/**
	 * Return proxy ticket expiration policy.
	 *
	 * @return The proxy ticket expiration policy.
	 */
	public ProxyTicketExpirationPolicy getProxyTicketExpirationPolicy() {
		return proxyTicketExpirationPolicy;
	}

	/**
	 * Sets proxy ticket expiration policy.
	 *
	 * @param proxyTicketExpirationPolicy
	 * 		The proxy ticket expiration policy.
	 */
	public void setProxyTicketExpirationPolicy(
			ProxyTicketExpirationPolicy proxyTicketExpirationPolicy) {
		this.proxyTicketExpirationPolicy = proxyTicketExpirationPolicy;
	}

	/**
	 * Return ticket granting ticket expiration policy.
	 *
	 * @return The ticket granting ticket expiration policy.
	 */
	public TicketGrantingTicketExpirationPolicy getTicketGrantingTicketExpirationPolicy() {
		return ticketGrantingTicketExpirationPolicy;
	}

	/**
	 * Sets ticket granting ticket expiration policy.
	 *
	 * @param ticketGrantingTicketExpirationPolicy
	 * 		The ticket granting ticket expiration policy.
	 */
	public void setTicketGrantingTicketExpirationPolicy(
			TicketGrantingTicketExpirationPolicy ticketGrantingTicketExpirationPolicy) {
		this.ticketGrantingTicketExpirationPolicy = ticketGrantingTicketExpirationPolicy;
	}

	/**
	 * Return proxy granting ticket expiration policy.
	 *
	 * @return The proxy granting ticket expiration policy.
	 */
	public ProxyGrantingTicketExpirationPolicy getProxyGrantingTicketExpirationPolicy() {
		return proxyGrantingTicketExpirationPolicy;
	}

	/**
	 * Sets proxy granting ticket expiration policy.
	 *
	 * @param proxyGrantingTicketExpirationPolicy
	 * 		The proxy granting ticket expiration policy.
	 */
	public void setProxyGrantingTicketExpirationPolicy(
			ProxyGrantingTicketExpirationPolicy proxyGrantingTicketExpirationPolicy) {
		this.proxyGrantingTicketExpirationPolicy = proxyGrantingTicketExpirationPolicy;
	}

	/**
	 * Return service ticket expiration policy.
	 *
	 * @return The service ticket expiration policy.
	 */
	public ServiceTicketExpirationPolicy getServiceTicketExpirationPolicy() {
		return serviceTicketExpirationPolicy;
	}

	/**
	 * Sets service ticket expiration policy.
	 *
	 * @param serviceTicketExpirationPolicy
	 * 		The service ticket expiration policy.
	 */
	public void setServiceTicketExpirationPolicy(ServiceTicketExpirationPolicy serviceTicketExpirationPolicy) {
		this.serviceTicketExpirationPolicy = serviceTicketExpirationPolicy;
	}

	/**
	 * Return SSO participation strategy.
	 *
	 * @return The service ticket expiration policy.
	 */
	public SingleSignOnParticipationPolicy getSingleSignOnParticipationPolicy() {
		return singleSignOnParticipationPolicy;
	}

	/**
	 * Sets SSO participation strategy.
	 *
	 * @param singleSignOnParticipationPolicy
	 * 		The service ticket expiration policy.
	 */
	public void setSingleSignOnParticipationPolicy(SingleSignOnParticipationPolicy singleSignOnParticipationPolicy) {
		this.singleSignOnParticipationPolicy = singleSignOnParticipationPolicy;
	}

	/**
	 * Return service matching strategy used to evaluate given service identifiers against this service.
	 *
	 * @return The matching strategy.
	 */
	public MatchingStrategy getMatchingStrategy() {
		return matchingStrategy;
	}

	/**
	 * Sets service matching strategy used to evaluate given service identifiers against this service.
	 *
	 * @param matchingStrategy
	 * 		The matching strategy.
	 */
	public void setMatchingStrategy(MatchingStrategy matchingStrategy) {
		this.matchingStrategy = matchingStrategy;
	}

	/**
	 * Return the access strategy that decides whether this registered service is able to proceed with authentication
	 * requests.
	 *
	 * @return The access strategy.
	 */
	public AccessStrategy getAccessStrategy() {
		return accessStrategy;
	}

	/**
	 * Sets the access strategy that decides whether this registered service is able to proceed with authentication
	 * requests.
	 *
	 * @param accessStrategy
	 * 		The access strategy.
	 */
	public void setAccessStrategy(AccessStrategy accessStrategy) {
		this.accessStrategy = accessStrategy;
	}

	/**
	 * 返回用户名属性提供者
	 *
	 * @return 用户名属性提供者
	 */
	public UsernameAttributeProvider getUsernameAttributeProvider() {
		return usernameAttributeProvider;
	}

	/**
	 * 设置用户名属性提供者
	 *
	 * @param usernameAttributeProvider
	 * 		用户名属性提供者
	 */
	public void setUsernameAttributeProvider(UsernameAttributeProvider usernameAttributeProvider) {
		this.usernameAttributeProvider = usernameAttributeProvider;
	}

	/**
	 * Return the public key associated with this service that is used to authorize the request by
	 * encrypting certain elements and attributes in the CAS validation protocol response, such as the PGT.
	 *
	 * @return The public key instance used to authorize the request.
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Sets the public key associated with this service that is used to authorize the request by
	 * encrypting certain elements and attributes in the CAS validation protocol response, such as the PGT.
	 *
	 * @param publicKey
	 * 		The public key instance used to authorize the request.
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/**
	 * Return specify supported and allowed protocols for this service.
	 *
	 * @return The specify supported and allowed protocols for this service.
	 */
	public Set<Protocol> getSupportedProtocols() {
		return supportedProtocols;
	}

	/**
	 * Sets specify supported and allowed protocols for this service.
	 *
	 * @param supportedProtocols
	 * 		Specify supported and allowed protocols for this service.
	 */
	public void setSupportedProtocols(Set<Protocol> supportedProtocols) {
		this.supportedProtocols = supportedProtocols;
	}

	/**
	 * Describes extra metadata about the service; custom fields that could be used by submodules implementing
	 * additional behavior on a per-service basis.
	 *
	 * @return The map of custom metadata.
	 */
	public Map<String, Property> getProperties() {
		return properties;
	}

	/**
	 * Describes extra metadata about the service; custom fields that could be used by submodules implementing
	 * additional behavior on a per-service basis.
	 *
	 * @param properties
	 * 		The map of custom metadata.
	 */
	public void setProperties(Map<String, Property> properties) {
		this.properties = properties;
	}

	/**
	 * Return the relative evaluation order of this service when determining matches.
	 *
	 * @return Evaluation order relative to other registered services. Services with lower values will be evaluated for a match before others.
	 */
	public int getEvaluationOrder() {
		return evaluationOrder;
	}

	/**
	 * Sets the relative evaluation order of this service when determining matches.
	 *
	 * @param evaluationOrder
	 * 		The service evaluation order.
	 */
	public void setEvaluationOrder(int evaluationOrder) {
		this.evaluationOrder = evaluationOrder;
	}

	@Override
	public String toString() {
		return StringBuilder.getInstance(this)
				.add("id", id)
				.add("name", name)
				.add("serviceId", serviceId)
				.add("logo", logo)
				.add("description", description)
				.add("locale", locale)
				.add("theme", theme)
				.add("templateName", templateName)
				.add("informationUrl", informationUrl)
				.add("privacyUrl", privacyUrl)
				.add("redirectUrl", redirectUrl)
				.add("logoutUrl", logoutUrl)
				.add("contacts", contacts)
				.add("responseType", responseType)
				.add("logoutType", logoutType)
				.add("environments", environments)
				.add("proxyPolicy", proxyPolicy)
				.add("attributeReleasePolicy", attributeReleasePolicy)
				.add("expirationPolicy", expirationPolicy)
				.add("authenticationPolicy", authenticationPolicy)
				.add("acceptableUsagePolicy", acceptableUsagePolicy)
				.add("multifactorPolicy", multifactorPolicy)
				.add("proxyTicketExpirationPolicy", proxyTicketExpirationPolicy)
				.add("ticketGrantingTicketExpirationPolicy", ticketGrantingTicketExpirationPolicy)
				.add("proxyGrantingTicketExpirationPolicy", proxyGrantingTicketExpirationPolicy)
				.add("serviceTicketExpirationPolicy", serviceTicketExpirationPolicy)
				.add("singleSignOnParticipationPolicy", singleSignOnParticipationPolicy)
				.add("matchingStrategy", matchingStrategy)
				.add("accessStrategy", accessStrategy)
				.add("usernameAttributeProvider", usernameAttributeProvider)
				.add("publicKey", publicKey)
				.add("supportedProtocols", supportedProtocols)
				.add("properties", properties)
				.add("evaluationOrder", evaluationOrder)
				.asString();
	}

}
