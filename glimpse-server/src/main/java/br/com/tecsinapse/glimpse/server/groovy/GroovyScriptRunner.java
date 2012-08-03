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

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.tecsinapse.glimpse.server.Monitor;
import br.com.tecsinapse.glimpse.server.ScriptRunner;

public class GroovyScriptRunner implements ScriptRunner {

	private VarProducer varProducer = new VarProducer() {

		public void fill(GroovyObject groovyScript) {
		}
	};

	private GroovyShell shell = new GroovyShell();

	private String monitorMethodsScript = null;
	
	public void setVarProducer(VarProducer varProducer) {
		this.varProducer = varProducer;
	}

	public GroovyScriptRunner() {
		try {
			InputStream in = getClass()
					.getResourceAsStream(
							"/br/com/tecsinapse/glimpse/server/groovy/monitorMethods.template");
			StringBuilder builder = new StringBuilder();
			int c = 0;
			while ((c = in.read()) != -1) {
				builder.append((char) c);
			}
			monitorMethodsScript = builder.toString();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public void run(String script, Monitor monitor) {
		try {
			String eventualScript = script + "\n" + monitorMethodsScript;

			Script groovyScript = shell.parse(eventualScript);

			varProducer.fill(groovyScript);

			groovyScript.setProperty("monitor", monitor);

			groovyScript.run();
		} catch (RuntimeException e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			monitor.println(stringWriter.toString());
		}
	}

}
