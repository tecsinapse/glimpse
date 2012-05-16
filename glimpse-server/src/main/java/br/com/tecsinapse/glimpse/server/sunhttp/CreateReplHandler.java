package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateReplHandler implements HttpHandler {

	private Server server;

	public CreateReplHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		template.setResponseOk();
		String id = server.createRepl();
		template.writeReponse(id);
	}
}
