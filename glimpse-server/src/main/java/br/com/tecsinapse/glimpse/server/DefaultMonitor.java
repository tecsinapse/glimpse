package br.com.tecsinapse.glimpse.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


public class DefaultMonitor implements Monitor {

	private BlockingQueue<ServerPoll> queue;
	private AtomicBoolean canceled;
	
	public DefaultMonitor(BlockingQueue<ServerPoll> queue, AtomicBoolean canceled) {
		this.queue = queue;
		this.canceled = canceled;
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
		return canceled.get();
	}

	public void print(Object output) {
		try {
			String text = nullSafeToString(output);
			queue.put(new StreamUpdatePoll(text));
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	private String nullSafeToString(Object output) {
		return output == null ? "null" : output.toString();
	}
	
	public void println(Object output) {
		String text = nullSafeToString(output);
		print(text + "\n");
	}

}
