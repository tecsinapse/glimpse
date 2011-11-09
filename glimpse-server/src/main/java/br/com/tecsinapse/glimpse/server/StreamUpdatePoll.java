package br.com.tecsinapse.glimpse.server;

public class StreamUpdatePoll implements ServerPoll {

	private String update;

	public StreamUpdatePoll(String update) {
		this.update = update;
	}
	
	public String getUpdate() {
		return update;
	}

	public boolean isInterrupt() {
		return false;
	}

}
