/*
 * Copyright 2012 Tecsinapse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.tecsinapse.glimpse.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.tecsinapse.glimpse.protocol.CancelPoll;
import br.com.tecsinapse.glimpse.protocol.ClosePoll;
import br.com.tecsinapse.glimpse.protocol.ServerPoll;

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
