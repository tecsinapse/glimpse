package br.com.tecsinapse.glimpse.server.groovy;

import groovy.lang.GroovyShell;
import br.com.tecsinapse.glimpse.server.Repl;

public class GroovyRepl implements Repl {

	private GroovyShell groovyShell = new GroovyShell();

	public GroovyRepl(VarProducer varProducer) {
		varProducer.fill(groovyShell);
	}

	public String eval(String expression) {
		try {
			Object result = groovyShell.evaluate(expression);
			if (result == null)
				return "null";
			else
				return result.toString();
		} catch (RuntimeException e) {
			return e.getMessage();
		}
	}

	public void close() {
	}

}
