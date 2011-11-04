package br.com.tecsinapse.glimpse.client;


public interface ScriptRunner {

	/**
	 * Runs a given script in the server.
	 * 
	 * @param script
	 *            the script to be run
	 * @param out
	 *            provides a Monitor the server can use to feed its current status
	 */
	void run(String script, Monitor monitor);

}
