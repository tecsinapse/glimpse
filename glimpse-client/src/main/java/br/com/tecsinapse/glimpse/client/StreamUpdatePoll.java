package br.com.tecsinapse.glimpse.client;

public class StreamUpdatePoll implements ClientPoll {

	private String update;
	
	public StreamUpdatePoll(String update) {
		this.update = update;
	}
	
	public void apply(Monitor monitor) {
		monitor.getStream().print(update);
	}

}
