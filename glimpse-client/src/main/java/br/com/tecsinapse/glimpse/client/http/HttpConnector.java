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

}
