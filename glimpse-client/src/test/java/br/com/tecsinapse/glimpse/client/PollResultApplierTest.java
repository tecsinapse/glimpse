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

import java.util.Arrays;

import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.com.tecsinapse.glimpse.protocol.BeginPollResultItem;
import br.com.tecsinapse.glimpse.protocol.CancelPollResultItem;
import br.com.tecsinapse.glimpse.protocol.ClosePollResultItem;
import br.com.tecsinapse.glimpse.protocol.PollResult;
import br.com.tecsinapse.glimpse.protocol.PollResultItem;
import br.com.tecsinapse.glimpse.protocol.StreamUpdatePollResultItem;
import br.com.tecsinapse.glimpse.protocol.WorkedPollResultItem;

public class PollResultApplierTest {

	private static interface MonitorVerifier {
		
		void verify(Monitor monitor);
		
	}
	
	private PollResultApplier applier = new PollResultApplier();
	
	@DataProvider(name="result-and-verifiers")
	public Object[][] getResultAndVerifiers() {
		Object[][] result = new Object[5][3];
		result[0] = beginResultAndVerifier();
		result[1] = cancelResultAndVerifier();
		result[2] = closeResultAndVerifier();
		result[3] = streamUpdateResultAndVerifier();
		result[4] = workedResultAndVerifier();
		return result;
	}
	
	private Object[] workedResultAndVerifier() {
		final int workedSteps = 1;
		Monitor monitor = Mockito.mock(Monitor.class);
		return new Object[] {
			new WorkedPollResultItem(workedSteps),
			monitor,
			new MonitorVerifier() {

				@Override
				public void verify(Monitor monitor) {
					Mockito.verify(monitor).worked(workedSteps);
				}
				
			}
		};
	}

	private Object[] streamUpdateResultAndVerifier() {
		final String update = "update";
		Monitor monitor = Mockito.mock(Monitor.class);
		return new Object[] {
			new StreamUpdatePollResultItem(update),
			monitor,
			new MonitorVerifier() {

				@Override
				public void verify(Monitor monitor) {
					Mockito.verify(monitor).println(update);
				}
				
			}
		};
	}

	private Object[] closeResultAndVerifier() {
		Monitor monitor = Mockito.mock(Monitor.class);
		return new Object[] {
			new ClosePollResultItem(),
			monitor,
			new MonitorVerifier() {

				@Override
				public void verify(Monitor monitor) {
					Mockito.verify(monitor).close();
				}
				
			}
		};
	}

	private Object[] cancelResultAndVerifier() {
		Monitor monitor = Mockito.mock(Monitor.class);
		return new Object[] {
			new CancelPollResultItem(),
			monitor,
			new MonitorVerifier() {

				@Override
				public void verify(Monitor monitor) {
					Mockito.verify(monitor).close();
				}
				
			}
		};
	}

	private Object[] beginResultAndVerifier() {
		final int steps = 10;
		Monitor monitor = Mockito.mock(Monitor.class);
		return new Object[] {
			new BeginPollResultItem(steps),
			monitor,
			new MonitorVerifier() {

				@Override
				public void verify(Monitor monitor) {
					Mockito.verify(monitor).begin(steps);
				}
				
			}
		};
	}

	@Test(dataProvider="result-and-verifiers")
	public void apply(PollResultItem item, Monitor monitor, MonitorVerifier verifier) {
		applier.apply(new PollResult(Arrays.asList(item)), monitor);
		
		verifier.verify(monitor);
	}
	
}
