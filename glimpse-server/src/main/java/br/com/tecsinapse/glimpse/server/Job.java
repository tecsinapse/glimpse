package br.com.tecsinapse.glimpse.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Job {

	private String script;
	
	private ScriptRunner scriptRunner;
	
	private final int MAX_ELEMENTS_PER_POLL = 20;
	
	private BlockingQueue<ServerPoll> queue = new LinkedBlockingDeque<ServerPoll>();
	
	public Job(String script, ScriptRunner scriptRunner) {
		this.script = script;
		this.scriptRunner = scriptRunner;
	}
	
	public void start() {
		// TODO usar executor, id para thread, etc.
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				scriptRunner.run(script, new DefaultMonitor(queue));
				queue.add(new ClosePoll());
			}
		});
		thread.start();
	}

	public void cancel() {
		throw new UnsupportedOperationException();
	}

	public List<ServerPoll> poll() {
		List<ServerPoll> result = new LinkedList<ServerPoll>();
		queue.drainTo(result, MAX_ELEMENTS_PER_POLL);
		return result;
	}

}
