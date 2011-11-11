package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class ExchangeTemplate {

	private HttpExchange exchange;

	public ExchangeTemplate(HttpExchange exchange) {
		this.exchange = exchange;
	}

	public String getRequestBody() throws IOException {
		InputStream in = exchange.getRequestBody();
		StringBuilder script = new StringBuilder();
		int c = 0;
		while ((c = in.read()) != -1) {
			script.append((char) c);
		}
		return script.toString();
	}

	public void setResponseOk() throws IOException {
		exchange.sendResponseHeaders(200, 0);
	}
	
	public void writeReponse(String response) throws IOException {
		OutputStream out = exchange.getResponseBody();
		out.write(response.getBytes());
		out.flush();
		out.close();
	}

}
