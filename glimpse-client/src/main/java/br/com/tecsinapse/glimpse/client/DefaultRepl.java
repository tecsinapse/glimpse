package br.com.tecsinapse.glimpse.client;

public class DefaultRepl implements Repl {

	private String replId;
	private Connector connector;

	public DefaultRepl(Connector connector) {
		this.connector = connector;
		this.replId = connector.createRepl();
	}

	public String eval(String script) {
		return connector.eval(replId, script);
	}

	public void close() {
		connector.closeRepl(replId);
	}

}
