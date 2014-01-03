package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.Monitor;

public class ConsoleMonitor implements Monitor {
	private final Console console;

	public ConsoleMonitor(Console console) {
		this.console = console;
	}

	@Override
	public void println(Object o) {
		console.println(o.toString());
	}

	@Override
	public void begin(int steps) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void worked(int workedSteps) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void close() {
	}
}
