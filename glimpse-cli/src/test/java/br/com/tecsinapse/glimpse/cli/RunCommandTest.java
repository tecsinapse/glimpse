package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

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

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);
		console.setHostManager(hostManager);

		command.execute(commandLine, console);

		verify(host).runScript("script.groovy", console);
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

}
