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

package br.com.tecsinapse.glimpse.client.http;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.tecsinapse.glimpse.client.CancelPoll;
import br.com.tecsinapse.glimpse.client.ClientPoll;
import br.com.tecsinapse.glimpse.client.ClosePoll;
import br.com.tecsinapse.glimpse.client.Connector;

public class HttpConnector implements Connector {

	private HttpInvoker invoker;

	private Set<String> ids = new HashSet<String>();

	public HttpConnector(String url, String username, String password) {
		this.invoker = new HttpInvoker(url, username, password);
	}

	public String start(String script) {
		String id = invoker.invoke("/start", script);
		ids.add(id);
		return id;
	}

	public boolean isOpen(String id) {
		return ids.contains(id);
	}

	public List<ClientPoll> poll(String id) {
		String body = invoker.invoke("/poll", id);
		List<ClientPoll> result = ClientPollConverter.convert(body);
		for (ClientPoll clientPoll : result) {
			if (clientPoll instanceof CancelPoll || clientPoll instanceof ClosePoll) {
				ids.remove(id);
			}
		}
		return result;
 	}

	public void cancel(String id) {
		invoker.invoke("/cancel", id);
	}

	public String createRepl() {
		return invoker.invoke("/createRepl", "");
	}

	public String eval(String replId, String script) {
		return invoker.invoke("/eval", replId + "\n" + script);
	}

	public void closeRepl(String replId) {
		invoker.invoke("/closeRepl", replId);
	}

}
