package br.com.tecsinapse.glimpse.server;

import java.util.concurrent.BlockingQueue;


public class DefaultMonitor implements Monitor {

	private BlockingQueue<ServerPoll> queue;
	
	public DefaultMonitor(BlockingQueue<ServerPoll> queue) {
		this.queue = queue;
	}

	public void begin(int steps) {
		try {
			queue.put(new BeginPoll(steps));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	public void worked(int workedSteps) {
		try {
			queue.put(new WorkedPoll(workedSteps));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	public boolean isCanceled() {
		throw new UnsupportedOperationException();
	}

	public void print(String output) {
		try {
			queue.put(new StreamUpdatePoll(output));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
