package br.com.tecsinapse.glimpse.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class Job {

	private String script;

	private ScriptRunner scriptRunner;

	private final int MAX_ELEMENTS_PER_POLL = 20;

	private BlockingQueue<ServerPoll> queue = new LinkedBlockingDeque<ServerPoll>();

	private AtomicBoolean canceled = new AtomicBoolean();
	
	private AtomicBoolean running = new AtomicBoolean(true);
	
	public Job(String script, ScriptRunner scriptRunner) {
		this.script = script;
		this.scriptRunner = scriptRunner;
	}

	public void start() {
		// TODO usar executor, id para thread, etc.
		Thread thread = new Thread(new Runnable() {

			public void run() {
				scriptRunner.run(script, new DefaultMonitor(queue, canceled));
				queue.add(new ClosePoll());
				Job.this.running.set(false);
			}
		}, "Job");
		thread.start();
	}
	
	public boolean isRunning() {
		return running.get();
	}

	public void cancel() {
		canceled.set(true);
		queue.add(new CancelPoll());
	}

	public List<ServerPoll> pollEverything() {
		List<ServerPoll> result = new LinkedList<ServerPoll>();
		queue.drainTo(result);
		return result;
	}
	
	public List<ServerPoll> poll() {
		List<ServerPoll> result = new LinkedList<ServerPoll>();
		queue.drainTo(result, MAX_ELEMENTS_PER_POLL);
		return result;
	}

}
