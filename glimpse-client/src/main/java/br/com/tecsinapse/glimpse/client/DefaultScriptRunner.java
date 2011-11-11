package br.com.tecsinapse.glimpse.client;

import java.util.List;

public class DefaultScriptRunner implements ScriptRunner {

	private Connector connector;

	public DefaultScriptRunner(Connector connector) {
		this.connector = connector;
	}

	public void run(final String script, final Monitor monitor) {
		String id = connector.start(script);

		while (connector.isOpen(id)) {
			if (monitor.isCanceled()) {
				connector.cancel(id);
			}
			List<ClientPoll> polls = connector.poll(id);
			if (polls.isEmpty()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			} else {
				for (ClientPoll clientPoll : polls) {
					clientPoll.apply(monitor);
				}
			}
		}
	}

}
