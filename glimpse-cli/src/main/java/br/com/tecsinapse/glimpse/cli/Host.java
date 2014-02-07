package br.com.tecsinapse.glimpse.cli;

import java.util.Map;

public interface Host {

	void runScript(String scriptFileName, Map<String, String> params, Console console);

	String getUrl();

	void startRepl(Console console);

}
