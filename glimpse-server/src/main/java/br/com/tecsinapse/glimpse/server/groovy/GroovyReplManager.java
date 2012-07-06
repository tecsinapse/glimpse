package br.com.tecsinapse.glimpse.server.groovy;

import groovy.lang.GroovyObject;
import br.com.tecsinapse.glimpse.server.Repl;
import br.com.tecsinapse.glimpse.server.ReplManager;

public class GroovyReplManager implements ReplManager {

	private VarProducer varProducer = new VarProducer() {
		
		public void fill(GroovyObject groovyScript) {
		}
	};
	
	public void setVarProducer(VarProducer varProducer) {
		this.varProducer = varProducer;
	}
	
	public Repl createRepl() {
		return new GroovyRepl(varProducer);
	}

}
