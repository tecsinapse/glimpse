package br.com.tecsinapse.glimpse.server;

public class BeginPoll implements ServerPoll {

	private int steps;

	public BeginPoll(int steps) {
		this.steps = steps;
	}
	
	public int getSteps() {
		return steps;
	}

	public boolean isInterrupt() {
		return false;
	}

}
