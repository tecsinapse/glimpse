package br.com.tecsinapse.glimpse.server.groovy;

import br.com.tecsinapse.glimpse.server.Monitor;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

public class GroovyScriptRunnerTest {

	@Test
	public void testMonitorMethodsPresence() {
		String script =
				"println(\"test\")\n" +
				"begin(15)\n" +
				"worked(1)\n" +
				"isCanceled()";

		Monitor monitor = Mockito.mock(Monitor.class);

		GroovyScriptRunner runner = new GroovyScriptRunner();

		runner.run(script, monitor);

		verify(monitor).println("test");
		verify(monitor).begin(15);
		verify(monitor).worked(1);
		verify(monitor).isCanceled();
	}

}
