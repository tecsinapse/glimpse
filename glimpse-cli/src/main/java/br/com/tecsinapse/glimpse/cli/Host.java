package br.com.tecsinapse.glimpse.cli;

public interface Host {

	void runScript(String scriptFileName, Console console);

	String getName();

	String getUrl();

	boolean isDefaultHost();
}
