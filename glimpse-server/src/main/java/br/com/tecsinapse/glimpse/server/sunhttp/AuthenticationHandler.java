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
