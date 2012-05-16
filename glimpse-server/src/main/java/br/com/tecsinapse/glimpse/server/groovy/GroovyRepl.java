package br.com.tecsinapse.glimpse.server.groovy;

import groovy.lang.GroovyShell;
import br.com.tecsinapse.glimpse.server.Repl;

public class GroovyRepl implements Repl {

	private GroovyShell groovyShell = new GroovyShell();

	public GroovyRepl(VarProducer varProducer) {
		varProducer.fill(groovyShell);
	}

	public String eval(String expression) {
		Object result = groovyShell.evaluate(expression);
		return result.toString();
	}

	public void close() {
	}

}
