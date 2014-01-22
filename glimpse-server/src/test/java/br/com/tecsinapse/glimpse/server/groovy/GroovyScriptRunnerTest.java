package br.com.tecsinapse.glimpse.server.groovy;

import br.com.tecsinapse.glimpse.server.Monitor;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GroovyScriptRunnerTest {

	@Test
	public void testMonitorMethodsPresence() {
		String script =
				"println(\"test\")\n" +
				"begin(15)\n" +
				"worked(1)\n" +
				"isCanceled()";

		Monitor monitor = mock(Monitor.class);

		GroovyScriptRunner runner = new GroovyScriptRunner();

		runner.run(script, monitor);

		verify(monitor).println("test");
		verify(monitor).begin(15);
		verify(monitor).worked(1);
		verify(monitor).isCanceled();
	}

	@Test
	public void testParameters() {
		String script = "println params.param1";

		Monitor monitor = mock(Monitor.class);

		GroovyScriptRunner runner = new GroovyScriptRunner();

		Map<String, String> parameters = Maps.newHashMap();
		parameters.put("param1", "test");

		runner.run(script, parameters, monitor);

		verify(monitor).println("test");
	}

}
