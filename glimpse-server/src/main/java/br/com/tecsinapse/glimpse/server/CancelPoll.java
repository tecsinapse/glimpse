package br.com.tecsinapse.glimpse.server;

public class CancelPoll implements ServerPoll {

	public boolean isInterrupt() {
		return true;
	}

}
