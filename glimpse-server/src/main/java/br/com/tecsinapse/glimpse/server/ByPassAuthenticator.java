package br.com.tecsinapse.glimpse.server;

public class ByPassAuthenticator implements Authenticator {

	public boolean authenticate(String username, String password) {
		return true;
	}

}
