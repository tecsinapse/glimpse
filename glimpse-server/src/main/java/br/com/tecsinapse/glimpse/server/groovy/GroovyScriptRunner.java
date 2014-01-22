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

package br.com.tecsinapse.glimpse.server.groovy;

import br.com.tecsinapse.glimpse.server.Monitor;
import br.com.tecsinapse.glimpse.server.ScriptRunner;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

public class GroovyScriptRunner implements ScriptRunner {

	private VarProducer varProducer = new VarProducer() {

		public void fill(GroovyObject groovyScript) {
		}
	};
	
	public void setVarProducer(VarProducer varProducer) {
		this.varProducer = varProducer;
	}

	public GroovyScriptRunner() {
	}

	public void run(String script, Map<String, String> parameters, Monitor monitor) {
		try {
			CompilerConfiguration configuration = new CompilerConfiguration();
			configuration.setScriptBaseClass("br.com.tecsinapse.glimpse.server.groovy.GlimpseScript");

			GroovyShell shell = new GroovyShell(configuration);
			
			GlimpseScript groovyScript = (GlimpseScript) shell.parse(script);
			groovyScript.setMonitor(monitor);

			groovyScript.setProperty("params", parameters);
			varProducer.fill(groovyScript);

			groovyScript.run();
		} catch (RuntimeException e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			monitor.println(stringWriter.toString());
		}
	}

}
