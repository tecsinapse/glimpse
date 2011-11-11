package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class StartHandler implements HttpHandler {

	private Server server;

	public StartHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String script = template.getRequestBody();
		template.setResponseOk();
		String id = server.start(script.toString());
		template.writeReponse(id);
	}

}
