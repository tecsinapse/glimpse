package br.com.tecsinapse.glimpse.server;

public interface Authenticator {

	boolean authenticate(String username, String password);
	
}
