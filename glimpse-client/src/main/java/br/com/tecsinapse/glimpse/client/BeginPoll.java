package br.com.tecsinapse.glimpse.client;

public class BeginPoll implements ClientPoll {

	private int steps;
	
	public BeginPoll(int steps) {
		this.steps = steps;
	}
	
	public void apply(Monitor monitor) {
		monitor.begin(steps);
	}

}
