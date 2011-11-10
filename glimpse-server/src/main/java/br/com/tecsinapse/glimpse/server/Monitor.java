package br.com.tecsinapse.glimpse.server;

public interface Monitor {

	void print(Object output);

	void println(Object output);

	void begin(int steps);

	void worked(int workedSteps);

	boolean isCanceled();

}
