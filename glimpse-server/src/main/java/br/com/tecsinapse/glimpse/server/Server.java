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
		return job.poll();
	}

}
