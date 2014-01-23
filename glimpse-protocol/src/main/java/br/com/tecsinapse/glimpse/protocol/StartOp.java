/*
 * Copyright 2012 Tecsinapse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.tecsinapse.glimpse.protocol;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents the start operation in Java.
 */
@XmlRootElement(name="start")
public class StartOp implements Operation {

	@XmlElement
	private String script;

	@XmlAnyElement(lax=true)
	private List<ParamValue> paramValues = new ArrayList<ParamValue>();

	@SuppressWarnings("UnusedDeclaration")
	public StartOp() {
	}
	
	public StartOp(String script) {
		this(script, Collections.<String, String>emptyMap());
	}

	public StartOp(String script, Map<String, String> params) {
		this.script = script;
		this.paramValues = Lists.newArrayList(Iterables.transform(params.entrySet(), new Function<Map.Entry<String, String>, ParamValue>() {

			@Override
			public ParamValue apply(Map.Entry<String, String> o) {
				return new ParamValue(o.getKey(), o.getValue());
			}
		}));
	}
	
	public String getScript() {
		return script;
	}

	public List<ParamValue> getParamValues() {
		return paramValues;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StartOp startOp = (StartOp) o;

		return script.equals(startOp.script) && paramValues.equals(startOp.paramValues);
	}

	@Override
	public int hashCode() {
		int result = script.hashCode();
		result = 31 * result + paramValues.hashCode();
		return result;
	}

	public Map<String, String> getParamsAsMap() {
		Map<String, ParamValue> paramValueByName = Maps.uniqueIndex(paramValues, new Function<ParamValue, String>() {
			@Override
			public String apply(ParamValue paramValue) {
				return paramValue.getName();
			}
		});
		return Maps.transformEntries(paramValueByName, new Maps.EntryTransformer<String, ParamValue, String>() {
			@Override
			public String transformEntry(@Nullable String s, @Nullable ParamValue paramValue) {
				return paramValue.getValue();
			}
		});
	}
}
