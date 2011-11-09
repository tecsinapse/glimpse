package br.com.tecsinapse.glimpse.client;

public class ClosePoll implements ClientPoll {

	public void apply(Monitor monitor) {
		monitor.close();
	}

}
