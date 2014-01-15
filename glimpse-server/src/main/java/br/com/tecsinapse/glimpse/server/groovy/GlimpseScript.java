package br.com.tecsinapse.glimpse.server.groovy;

import br.com.tecsinapse.glimpse.server.Monitor;
import groovy.lang.Script;

public abstract class GlimpseScript extends Script {

	private Monitor monitor;

	public final void setMonitor(Monitor monitor) {
		if (this.monitor != null) {
			throw new IllegalStateException("Cannot call setMonitor from a script");
		}
		this.monitor = monitor;
	}

	@SuppressWarnings("UnusedDeclaration")
	public final void begin(int steps) {
		monitor.begin(steps);
	}

	@SuppressWarnings("UnusedDeclaration")
	public final void worked(int workedSteps) {
		monitor.worked(workedSteps);
	}

	@SuppressWarnings("UnusedDeclaration")
	public final boolean isCanceled() {
		return monitor.isCanceled();
	}

	public final void println(Object output) {
		monitor.println(output);
	}

}
