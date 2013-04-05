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

import br.com.tecsinapse.glimpse.client.Connector;
import br.com.tecsinapse.glimpse.client.ConnectorException;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;

public class HttpConnector implements Connector {

	private HttpInvoker invoker;

	private Set<String> ids = new HashSet<String>();

	public HttpConnector(String url, String username, String password) {
		this.invoker = new HttpInvoker(url, username, password);
	}

	public String start(String script) throws ConnectorException {
		String id = invoker.invoke("/start", script);
		ids.add(id);
		return id;
	}

	public boolean isOpen(String id) {
		return ids.contains(id);
	}

	public List<PollResultItem> poll(String id) throws ConnectorException {
		String body = invoker.invoke("/poll", id);
		List<PollResultItem> result = ClientPollConverter.convert(body);
		for (PollResultItem clientPoll : result) {
			if (clientPoll instanceof CancelPollResultItem || clientPoll instanceof ClosePollResultItem) {
				ids.remove(id);
			}
		}
		return result;
 	}

	public void cancel(String id) throws ConnectorException {
		invoker.invoke("/cancel", id);
	}

	public String createRepl() throws ConnectorException {
		return invoker.invoke("/createRepl", "");
	}

	public String eval(String replId, String script) throws ConnectorException {
		return invoker.invoke("/eval", replId + "\n" + script);
	}

	public void closeRepl(String replId) throws ConnectorException {
		invoker.invoke("/closeRepl", replId);
	}

}
