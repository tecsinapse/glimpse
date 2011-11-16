package br.com.tecsinapse.glimpse.client;

import br.com.tecsinapse.glimpse.client.http.HttpConnector;


public class ScriptRunnerFactory {

	private ScriptRunnerFactory() {
	}
	
	/**
	 * Creates a ScriptRunner which can be used to run scripts against a given server.
	 * 
	 * @param url the url of the server
	 * @param username the username used to authenticate with the server
	 * @param password the password used to authenticate with the server
	 * @return a new ScriptRunner
	 */
	public static ScriptRunner create(String url, String username, String password) {
		return new DefaultScriptRunner(new HttpConnector(url, username, password));
	}
	
}
