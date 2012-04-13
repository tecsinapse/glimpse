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

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Server {

	private ScriptRunner scriptRunner;

	private ConcurrentMap<String, Job> jobs = new ConcurrentHashMap<String, Job>();

	public Server(ScriptRunner scriptRunner) {
		this.scriptRunner = scriptRunner;
	}

	/**
	 * Starts a conversation between a client and the server.
	 * 
	 * @param script
	 *            the script to be run by the server
	 * @return the id assigned for the new conversation
	 */
	public String start(String script) {
		String id = UUID.randomUUID().toString();
		Job job = new Job(script, scriptRunner);
		jobs.putIfAbsent(id, job);
		job.start();
		return id;
	}

	/**
	 * Cancels the given conversation. This method is not guaranteed to work as
	 * it depends that the script being run supports cancelation. The server
	 * will stop sending status, but the script might keep running.
	 * 
	 * @param id
	 *            the id of the conversation
	 */
	public void cancel(String id) {
		Job job = jobs.get(id);
		job.cancel();
	}

	/**
	 * Polls the current status of the conversation.
	 * 
	 * @param id
	 *            the id assigned for the conversation
	 * @return output produced by the server since latest poll
	 */
	public List<ServerPoll> poll(String id) {
		Job job = jobs.get(id);
		if (!job.isRunning()) {
			jobs.remove(id);
			return job.pollEverything();
		} else {
			return job.poll();
		}
	}

}
