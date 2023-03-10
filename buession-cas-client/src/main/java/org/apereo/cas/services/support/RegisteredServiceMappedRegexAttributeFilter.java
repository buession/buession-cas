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
package org.apereo.cas.services.support;

import org.apereo.cas.services.AttributeFilter;

import java.util.Map;

/**
 * A filtering policy that selectively applies patterns to attributes mapped in the config.
 * If an attribute is mapped, it's only allowed to be released if it matches the linked pattern.
 * If an attribute is not mapped, it may optionally be excluded from the released set of attributes.
 *
 * @author Yong.Teng
 * @since 2.2.0
 */
public class RegisteredServiceMappedRegexAttributeFilter implements AttributeFilter {

	private final static long serialVersionUID = -3139441647897554698L;

	private Map<String, Object> patterns;

	private boolean excludeUnmappedAttributes;

	private boolean caseInsensitive = true;

	private boolean completeMatch;

	private int order;

	public Map<String, Object> getPatterns(){
		return patterns;
	}

	public void setPatterns(Map<String, Object> patterns){
		this.patterns = patterns;
	}

	public boolean isExcludeUnmappedAttributes(){
		return excludeUnmappedAttributes;
	}

	public void setExcludeUnmappedAttributes(boolean excludeUnmappedAttributes){
		this.excludeUnmappedAttributes = excludeUnmappedAttributes;
	}

	public boolean isCaseInsensitive(){
		return caseInsensitive;
	}

	public void setCaseInsensitive(boolean caseInsensitive){
		this.caseInsensitive = caseInsensitive;
	}

	public boolean isCompleteMatch(){
		return completeMatch;
	}

	public void setCompleteMatch(boolean completeMatch){
		this.completeMatch = completeMatch;
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
				.add("patterns", patterns)
				.add("excludeUnmappedAttributes", excludeUnmappedAttributes)
				.add("caseInsensitive", caseInsensitive)
				.add("completeMatch", completeMatch)
				.add("order", order)
				.asString();
	}

}
