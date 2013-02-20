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
import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.tecsinapse.glimpse.server.Server;
import br.com.tecsinapse.glimpse.server.ServerInvoker;
import br.com.tecsinapse.glimpse.server.ServerInvokerTest;
import br.com.tecsinapse.glimpse.server.protocol.Operation;
import br.com.tecsinapse.glimpse.server.protocol.OperationParser;
import br.com.tecsinapse.glimpse.server.protocol.Result;
import br.com.tecsinapse.glimpse.server.protocol.ResultMarshaller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class OperationHandler implements HttpHandler {

	private OperationParser operationParser = new OperationParser();
	
	private ResultMarshaller resultMarshaller = new ResultMarshaller();
	
	private ServerInvoker serverInvoker;
	
	public OperationHandler(Server server) {
		this.serverInvoker = new ServerInvoker(server);
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		ExchangeTemplate template = new ExchangeTemplate(exchange);
		String xml = template.getRequestBody();
		try {
			Operation operation = operationParser.parse(xml);
			Result result = serverInvoker.invoke(operation);
			template.setResponseOk();
			template.writeReponse(resultMarshaller.marshall(result));
		} catch (RuntimeException e) {
			template.setResponseInternalServerError();
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			template.writeReponse(writer.toString());
		}
	}

}
