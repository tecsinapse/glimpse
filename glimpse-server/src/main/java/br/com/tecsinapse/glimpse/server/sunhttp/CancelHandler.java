package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CancelHandler implements HttpHandler {

	private Server server;

	public CancelHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String id = template.getRequestBody();
		server.cancel(id);
		template.setResponseOk();
		template.writeReponse("");
	}

}
