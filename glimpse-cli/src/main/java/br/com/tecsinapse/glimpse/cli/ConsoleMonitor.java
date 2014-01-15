package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.Monitor;

public class ConsoleMonitor implements Monitor {
	private final Console console;

	private int totalWorkedSteps;

	private int steps;

	public ConsoleMonitor(Console console) {
		this.console = console;
	}

	@Override
	public void println(Object o) {
		console.println(o.toString());
	}

	@Override
	public void begin(int steps) {
		this.steps = steps;
		console.enableProgressBar();
	}

	@Override
	public void worked(int workedSteps) {
		totalWorkedSteps += workedSteps;
		console.updateProgressBar((float) totalWorkedSteps / (float) steps);
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void close() {
	}
}
