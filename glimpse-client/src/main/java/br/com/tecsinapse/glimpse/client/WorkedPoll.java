package br.com.tecsinapse.glimpse.client;

public class WorkedPoll implements ClientPoll {

	private int workedSteps;
	
	public WorkedPoll(int workedSteps) {
		this.workedSteps = workedSteps;
	}
	
	public void apply(Monitor monitor) {
		monitor.worked(workedSteps);
	}

}
