package br.com.tecsinapse.glimpse.client;

public class DefaultReplManager implements ReplManager {

	private Connector connector;

	public DefaultReplManager(Connector connector) {
		this.connector = connector;
	}

	public Repl createRepl() {
		return new DefaultRepl(connector);
	}

}
