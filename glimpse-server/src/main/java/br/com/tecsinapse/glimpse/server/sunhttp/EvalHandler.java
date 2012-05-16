package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class EvalHandler implements HttpHandler {

	private Server server;

	public EvalHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String body = template.getRequestBody();
		template.setResponseOk();
		int firstBreak = body.indexOf("\n");
		String id = body.substring(0, firstBreak);
		String expression = body.substring(firstBreak + 1, body.length());
		String result = server.eval(id, expression);
		template.writeReponse(result);
	}

}
