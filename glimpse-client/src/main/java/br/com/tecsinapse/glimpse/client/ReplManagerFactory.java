package br.com.tecsinapse.glimpse.client;

import br.com.tecsinapse.glimpse.client.http.BackwardsCompatibilityHttpConnector;

public class ReplManagerFactory {

	private ReplManagerFactory() {
	}

	public static ReplManager create(String url, String username, String password) {
		return new DefaultReplManager(new BackwardsCompatibilityHttpConnector(url, username, password));
	}

}
