package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;
import java.util.List;

import br.com.tecsinapse.glimpse.server.Server;
import br.com.tecsinapse.glimpse.server.ServerPoll;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PollHandler implements HttpHandler {

	private Server server;

	public PollHandler(Server server) {
		this.server = server;
	}

	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String id = template.getRequestBody();
		List<ServerPoll> polls = server.poll(id);
		StringBuilder builder = new StringBuilder();
		for (ServerPoll serverPoll : polls) {
			builder.append(PollWriter.write(serverPoll));
			builder.append("\n");
		}
		template.setResponseOk();
		template.writeReponse(builder.toString());
	}

}
