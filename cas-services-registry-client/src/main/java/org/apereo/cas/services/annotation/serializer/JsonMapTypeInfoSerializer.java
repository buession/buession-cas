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
package org.apereo.cas.services.annotation.serializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
@JacksonStdImpl
public class JsonMapTypeInfoSerializer extends StdScalarSerializer<Map<?, ?>> implements ContextualSerializer {

	private final static long serialVersionUID = 4663427982574364968L;

	public JsonMapTypeInfoSerializer() {
		super(Map.class, false);
	}

	public JsonMapTypeInfoSerializer(Class<? extends Map<?, ?>> v) {
		super(v, false);
	}

	@Override
	public void serialize(Map<?, ?> value, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeStartObject();
		generator.writeFieldName("@class");
		generator.writeString(value.getClass().getName());

		for(Map.Entry<?, ?> entry : value.entrySet()){
			generator.writeFieldName(entry.getKey().toString());
			generator.writeObject(entry.getValue());
		}

		generator.writeEndObject();
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property)
			throws JsonMappingException {
		JsonFormat.Value format = findFormatOverrides(provider, property, provider.getClass());

		if(format != null){
			return new JsonMapTypeInfoSerializer((Class<Map<?, ?>>) property.getType().getRawClass());
		}

		return this;
	}

}
