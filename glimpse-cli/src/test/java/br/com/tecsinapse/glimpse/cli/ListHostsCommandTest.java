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
		List<Host> hosts = new ArrayList<Host>();
		Host host1 = mock(Host.class);
		when(host1.getName()).thenReturn("host1");
		when(host1.getUrl()).thenReturn("http://host1:8081");
		when(host1.isDefaultHost()).thenReturn(false);
		hosts.add(host1);

		Host host2 = mock(Host.class);
		when(host2.getName()).thenReturn("host2");
		when(host2.getUrl()).thenReturn("http://host2:8081");
		when(host2.isDefaultHost()).thenReturn(true);
		hosts.add(host2);

		HostManager hostManager = mock(HostManager.class);
		when(hostManager.listHosts()).thenReturn(hosts);

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
		when(hostManager.listHosts()).thenReturn(new ArrayList<Host>());

		DumbConsole console = new DumbConsole(hostManager);

		ListHostsCommand command = new ListHostsCommand();

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(command.getOptions(), new String[] {});

		command.execute(commandLine, console);

		assertEquals(console.getOutput(), "There are no hosts saved\n");
	}

}
