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
import org.apereo.cas.services.RegisteredServiceContact;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 3.0.0
 */
@JacksonStdImpl
public class JsonCollectionSerializer extends StdScalarSerializer<Collection<?>> implements ContextualSerializer {

	private final static long serialVersionUID = 2235740940895178474L;

	public JsonCollectionSerializer() {
		super(RegisteredServiceContact.class, false);
	}

	public JsonCollectionSerializer(Class<? extends Collection<?>> v) {
		super(v, false);
	}

	@Override
	public void serialize(Collection<?> value, JsonGenerator generator, SerializerProvider provider)
			throws IOException {
		generator.writeStartArray();
		generator.writeString(value.getClass().getName());
		generator.writeStartArray();

		value.forEach((v)->{
			try{
				generator.writeObject(v);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
		});

		generator.writeEndArray();
		generator.writeEndArray();
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property)
			throws JsonMappingException {
		JsonFormat.Value format = findFormatOverrides(provider, property, provider.getClass());

		if(format != null){
			return new JsonCollectionSerializer((Class<Collection<?>>) property.getType().getRawClass());
		}

		return this;
	}

}
