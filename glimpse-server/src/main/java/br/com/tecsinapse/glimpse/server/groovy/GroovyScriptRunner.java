package br.com.tecsinapse.glimpse.server.groovy;

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

		public void fill(Script groovyScript) {
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
