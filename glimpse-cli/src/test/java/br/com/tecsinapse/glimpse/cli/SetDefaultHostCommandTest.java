package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.AssertJUnit.assertEquals;

public class SetDefaultHostCommandTest {

	@Test
	public void testExecute() throws ParseException {
		HostManager hostManager = mock(HostManager.class);

		DumbConsole console = new DumbConsole(hostManager);

		SetDefaultHostCommand command = new SetDefaultHostCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"host1"});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Host 'host1' set as default\n");
		verify(hostManager).setDefaultHost("host1");
	}

	@Test
	public void testNoSuchHost() throws ParseException {
		HostManager hostManager = mock(HostManager.class);
		doThrow(new IllegalArgumentException("No such host: 'host1'")).when(hostManager).setDefaultHost("host1");

		DumbConsole console = new DumbConsole(hostManager);

		SetDefaultHostCommand command = new SetDefaultHostCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"host1"});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: No such host: 'host1'\n");
	}

	@Test
	public void testNoArgument() throws ParseException {
		HostManager hostManager = mock(HostManager.class);

		DumbConsole console = new DumbConsole(hostManager);

		SetDefaultHostCommand command = new SetDefaultHostCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: A host name must be provided\n");
	}

}
