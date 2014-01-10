package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class AddHostCommandTest {

	@Test
	public void testExecute() throws ParseException {
		HostManager hostManager = mock(HostManager.class);

		DumbConsole console = new DumbConsole(hostManager);

		AddHostCommand command = new AddHostCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {"-name", "server1", "-url", "http://server1:8081"});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Host 'server1' added\n");

		verify(hostManager).addHost(new HostSpec("server1", "http://server1:8081", false, null, null));
	}

	@Test
	public void testServerAlreadyExist() throws ParseException {
		HostManager hostManager = mock(HostManager.class);

		doThrow(new IllegalArgumentException("Host 'server1' already exists")).when(hostManager).addHost(new HostSpec("server1", "http://server1:8081", false, null, null));

		DumbConsole console = new DumbConsole(hostManager);

		AddHostCommand command = new AddHostCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(),new String[] {"-name", "server1", "-url", "http://server1:8081"});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: Host 'server1' already exists\n");
	}


}
