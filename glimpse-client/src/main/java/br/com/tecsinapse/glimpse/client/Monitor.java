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
