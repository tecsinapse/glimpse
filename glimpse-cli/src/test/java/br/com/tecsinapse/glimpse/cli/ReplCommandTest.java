package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReplCommandTest {

	@Test
	public void testExecute() throws ParseException {
		ReplCommand command = new ReplCommand();

		HostManager hostManager = mock(HostManager.class);

		DumbConsole console = new DumbConsole(hostManager);

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		Host host = mock(Host.class);
		when(hostManager.getHost(commandLine, console)).thenReturn(host);

		command.execute(commandLine, console);

		verify(host).startRepl(console);
	}

}
