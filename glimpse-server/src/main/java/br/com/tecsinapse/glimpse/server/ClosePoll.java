package br.com.tecsinapse.glimpse.server;

public class ClosePoll implements ServerPoll {

	public boolean isInterrupt() {
		return true;
	}

}
