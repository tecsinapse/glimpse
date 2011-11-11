package br.com.tecsinapse.glimpse.client;


public interface Monitor {

	/**
	 * Prints an object to the output.
	 * 
	 * o the object to be printed
	 */
	void println(Object o);

	/**
	 * Indicates that a long process is going to take place. It must tell the
	 * client how many steps the long process takes. It's not required that the
	 * server invokes this method, but in that case, the client won't have any
	 * clue about the progress of the task.
	 * 
	 * @param steps
	 *            number of steps
	 */
	void begin(int steps);

	/**
	 * Indicates how many steps have been performed by the server for the given
	 * task. It's not required that the server invokes this method, but in that
	 * case, the client won't have any clue about the progress of the task.
	 * 
	 * @param workedSteps
	 *            number of steps performed so far
	 */
	void worked(int workedSteps);

	/**
	 * Indicates to the server that it should cancel the execution of the task.
	 * The server is not required to really cancel the task, but in that case,
	 * the client will have to wait until the server finishes the task.
	 * 
	 * @return
	 */
	boolean isCanceled();

	/**
	 * Indicates to the client that the server has finished doing its job.
	 */
	void close();

}
