package br.com.tecsinapse.glimpse.client;

public class CancelPoll implements ClientPoll {

	public void apply(Monitor monitor) {
		monitor.close();
	}

}
