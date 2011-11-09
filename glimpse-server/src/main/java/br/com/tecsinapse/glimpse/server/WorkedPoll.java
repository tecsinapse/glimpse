package br.com.tecsinapse.glimpse.server;

public class WorkedPoll implements ServerPoll {

	private int workedSteps;

	public WorkedPoll(int workedSteps) {
		this.workedSteps = workedSteps;
	}

	public int getWorkedSteps() {
		return workedSteps;
	}
	
	public boolean isInterrupt() {
		return false;
	}

}
