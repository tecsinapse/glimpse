package br.com.tecsinapse.glimpse.server;

public interface Repl {

	String eval(String expression);
	
	void close();
	
}
