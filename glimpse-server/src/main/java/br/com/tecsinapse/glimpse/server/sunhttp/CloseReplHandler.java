package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CloseReplHandler implements HttpHandler {

	private Server server;

	public CloseReplHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String id = template.getRequestBody();
		template.setResponseOk();
		server.closeRepl(id);
		template.writeReponse("");
	}
}
