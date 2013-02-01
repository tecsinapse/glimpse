/*
 * Copyright 2012 Tecsinapse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	
	public void setResponseInternalServerError() throws IOException {
		exchange.sendResponseHeaders(500, 0);
	}
	
	public void writeReponse(String response) throws IOException {
		OutputStream out = exchange.getResponseBody();
		out.write(response.getBytes());
		out.flush();
		out.close();
	}

}
