package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;
import java.security.Principal;

import br.com.tecsinapse.glimpse.server.AuthManager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AuthenticationHandler implements HttpHandler {

	private HttpHandler delegate;

	public AuthenticationHandler(HttpHandler delegate) {
		this.delegate = delegate;
	}

	public void handle(HttpExchange exchange) throws IOException {
		Principal principal = exchange.getPrincipal();
		String username = principal.getName();
		try {
			AuthManager.setCurrentUser(username);
			delegate.handle(exchange);
		} finally {
			AuthManager.releaseCurrentUser();
		}

	}

}
