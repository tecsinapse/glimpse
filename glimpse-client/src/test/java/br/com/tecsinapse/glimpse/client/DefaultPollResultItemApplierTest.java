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

import java.util.Map;

import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import br.com.tecsinapse.glimpse.protocol.PollResultItem;

public class DefaultPollResultItemApplierTest {

	private DefaultPollResultItemApplier defaultApplier;
	
	private Monitor monitor;
	
	private static class Item1 implements PollResultItem {
	}
	
	private static class Item2 implements PollResultItem {
	}
	
	private PollResultItemApplier applier1;
	
	private PollResultItemApplier applier2;
	
	public DefaultPollResultItemApplierTest() {
		monitor = Mockito.mock(Monitor.class);
		
		applier1 = Mockito.mock(PollResultItemApplier.class);
		applier2 = Mockito.mock(PollResultItemApplier.class);
		
		Map<Class<? extends PollResultItem>, PollResultItemApplier<? extends PollResultItem>> appliersByClass = Maps.newHashMap();
		appliersByClass.put(Item1.class, applier1);
		appliersByClass.put(Item2.class, applier2);
		
		defaultApplier = new DefaultPollResultItemApplier(appliersByClass);
	}
	
	@DataProvider(name="appliers-and-items")
	public Object[][] getAppliersAndItems() {
		return new Object[][] {
				{ applier1, new Item1() },
				{ applier2, new Item2() }
		};
	}
	
	@Test(dataProvider="appliers-and-items")
	public void apply(PollResultItemApplier applier, PollResultItem item) {
		defaultApplier.apply(item, monitor);
		
		Mockito.verify(applier).apply(item, monitor);
	}
	
}
