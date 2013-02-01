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

package br.com.tecsinapse.glimpse.server.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.tecsinapse.glimpse.server.Server;

/**
 * Represents the start operation in Java.
 */
@XmlRootElement(name="start")
public class StartOp implements Operation {

	@XmlElement
	private String script;
	
	public StartOp() {
	}
	
	public StartOp(String script) {
		this.script = script;
	}
	
	public String getScript() {
		return script;
	}

	public StartResult execute(Server server) {
		String jobId = server.start(script);
		return new StartResult(jobId);
	}
	
}
