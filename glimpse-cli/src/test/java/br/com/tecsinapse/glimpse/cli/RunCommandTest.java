package br.com.tecsinapse.glimpse.cli;

import com.beust.jcommander.internal.Maps;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class RunCommandTest {

	@Test
	public void testExecute() throws ParseException {
		RunCommand command = new RunCommand();

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"script.groovy"});

		Host host = mock(Host.class);
		when(host.getUrl()).thenReturn("http://localhost:8081");

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);
		console.setHostManager(hostManager);

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Executing script at: http://localhost:8081\n" +
				"--------------------------------------------------\n" +
				"\n" +
				"\n" +
				"--------------------------------------------------\n" +
				"Finished executing script\n");

		verify(host).runScript("script.groovy", Collections.<String, String>emptyMap(), console);
	}

	@Test
	public void testExecuteWithNoArg() throws ParseException {
		RunCommand command = new RunCommand();

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		Host host = mock(Host.class);

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);
		console.setHostManager(hostManager);

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: a script file must be specified\n" +
				"\tUsage: glimpse run <script.groovy>\n");
	}

	@Test
	public void testExecuteWithParams() throws ParseException {
		RunCommand command = new RunCommand();

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"-params", "param1=test1|param2=test2", "script.groovy"});

		Host host = mock(Host.class);
		when(host.getUrl()).thenReturn("http://localhost:8081");

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);
		console.setHostManager(hostManager);

		command.execute(commandLine, console);

		Map<String, String> params = Maps.newHashMap();
		params.put("param1", "test1");
		params.put("param2", "test2");

		verify(host).runScript("script.groovy", params, console);

		assertEquals(console.getOutput(), "Executing script at: http://localhost:8081\n" +
				"--------------------------------------------------\n" +
				"param1=test1\n" +
				"param2=test2\n" +
				"\n" +
				"\n" +
				"--------------------------------------------------\n" +
				"Finished executing script\n");
	}

	@Test
	public void testExecuteWithMalformedParams() throws ParseException {
		RunCommand command = new RunCommand();

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"-params", "param1=test1|param2", "script.groovy"});

		Host host = mock(Host.class);
		when(host.getUrl()).thenReturn("http://localhost:8081");

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);
		console.setHostManager(hostManager);

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: malformed parameters\n");
	}

}
