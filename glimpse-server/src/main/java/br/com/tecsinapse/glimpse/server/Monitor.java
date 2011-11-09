package br.com.tecsinapse.glimpse.server;


public interface Monitor {

	void print(String output);
	
	void begin(int steps);
	
	void worked(int workedSteps);
	
	boolean isCanceled();
	
}
