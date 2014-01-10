package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class ListHostsCommandTest {

	@Test
	public void testExecute() throws ParseException {
		List<HostSpec> hosts = new ArrayList<HostSpec>();
		hosts.add(new HostSpec("host1", "http://host1:8081", false, null, null));
		hosts.add(new HostSpec("host2", "http://host2:8081", true, null, null));

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.listHostSpecs()).thenReturn(hosts);

		DumbConsole console = new DumbConsole(hostManager);

		ListHostsCommand command = new ListHostsCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(),
				"host1 - http://host1:8081\n" +
						"host2 - http://host2:8081 (default)\n");
	}

	@Test
	public void testExecuteNoHosts() throws ParseException {
		HostManager hostManager = mock(HostManager.class);
		when(hostManager.listHostSpecs()).thenReturn(new ArrayList<HostSpec>());

		DumbConsole console = new DumbConsole(hostManager);

		ListHostsCommand command = new ListHostsCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "There are no hosts saved\n");
	}

}
