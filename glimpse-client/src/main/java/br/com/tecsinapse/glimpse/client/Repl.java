package br.com.tecsinapse.glimpse.client;

public interface Repl {

	String eval(String script);
	
	void close();

}
