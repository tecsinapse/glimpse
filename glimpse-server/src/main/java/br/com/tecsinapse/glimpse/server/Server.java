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
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import br.com.tecsinapse.glimpse.protocol.PollResultItem;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class Server {

	private ScriptRunner scriptRunner;

	private ReplManager replManager;

	private ConcurrentMap<String, Job> jobs;

	private ConcurrentMap<String, Repl> repls;

	public Server(ScriptRunner scriptRunner, ReplManager replManager) {
		this.scriptRunner = scriptRunner;
		this.replManager = replManager;
		Cache<String, Job> jobCache = CacheBuilder.newBuilder()
				.expireAfterAccess(30, TimeUnit.MINUTES).build();
		jobs = jobCache.asMap();
		Cache<String, Repl> replCache = CacheBuilder.newBuilder()
				.expireAfterAccess(30, TimeUnit.MINUTES)
				.removalListener(new RemovalListener<String, Repl>() {

					public void onRemoval(RemovalNotification<String, Repl> not) {
						not.getValue().close();
					}
				}).build();
		repls = replCache.asMap();
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
	public List<PollResultItem> poll(String id) {
		Job job = jobs.get(id);
		if (!job.isRunning()) {
			jobs.remove(id);
			return job.pollEverything();
		} else {
			return job.poll();
		}
	}

	public String createRepl() {
		String id = UUID.randomUUID().toString();
		Repl repl = replManager.createRepl();
		repls.put(id, repl);
		return id;
	}

	public String eval(String replId, String expression) {
		Repl repl = repls.get(replId);
		if (repl == null)
			return "This repl has expired. Start a new repl to get back to work!";
		else
			return repl.eval(expression);
	}

	public void closeRepl(String replId) {
		Repl repl = repls.get(replId);
		repl.close();
		repls.remove(replId);
	}

}
