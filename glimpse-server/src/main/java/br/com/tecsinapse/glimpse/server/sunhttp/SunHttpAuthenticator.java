package br.com.tecsinapse.glimpse.server.sunhttp;

import br.com.tecsinapse.glimpse.server.Authenticator;

import com.sun.net.httpserver.BasicAuthenticator;

public class SunHttpAuthenticator extends BasicAuthenticator {

	private Authenticator authenticator;
	
	public SunHttpAuthenticator(Authenticator authenticator) {
		super("Glimpse");
		
		this.authenticator = authenticator;
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		return authenticator.authenticate(username, password);
	}

}
