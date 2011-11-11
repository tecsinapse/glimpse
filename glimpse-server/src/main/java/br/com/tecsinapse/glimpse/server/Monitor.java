package br.com.tecsinapse.glimpse.server;

public interface Monitor {

	void println(Object output);

	void begin(int steps);

	void worked(int workedSteps);

	boolean isCanceled();

}
