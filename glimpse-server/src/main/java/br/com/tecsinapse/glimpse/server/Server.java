package br.com.tecsinapse.glimpse.server;

public class Server {

	/**
	 * Starts a conversation between a client and the server.
	 * 
	 * @param script
	 *            the script to be run by the server
	 * @return the id assigned for the new conversation
	 */
	public String start(String script) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	/**
	 * Polls the current status of the conversation.
	 * 
	 * @param id
	 *            the id assigned for the conversation
	 * @return output produced by the server since latest poll
	 */
	public String poll(String id) {
		throw new UnsupportedOperationException();
	}

}
