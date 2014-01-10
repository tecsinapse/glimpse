package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class DefaultHostManagerTest {

	private String getHostsFileContent() {
		return "<hosts>\n" +
				"\t<host>\n" +
				"\t\t<name>localhost</name>\n" +
				"\t\t<url>http://localhost:8081</url>\n" +
				"\t\t<default>true</default>\n" +
				"\t</host>\n" +
				"\t<host>\n" +
				"\t\t<name>server1</name>\n" +
				"\t\t<url>http://server1:8081</url>\n" +
				"\t\t<default>false</default>\n" +
				"\t</host>\n" +
				"</hosts>";
	}

	@Test
	public void testGetDefaultHost() throws ParseException {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		CommandLine commandLine = parser.parse(options, new String[] {});

		DefaultHost host = (DefaultHost) defaultHostManager.getHost(commandLine, console);

		assertEquals(host.getUrl(), "http://localhost:8081");
	}

	@Test
	public void testNoneDefaultHost() throws ParseException {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn("<hosts></hosts>");

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		CommandLine commandLine = parser.parse(options, new String[] {});

		DefaultHost host = (DefaultHost) defaultHostManager.getHost(commandLine, console);

		assertEquals(console.getOutput(), "Error: there is no default host set, use 'glimpse host default <host_name>' to set a default host\n");
		assertNull(host);
	}

	@Test
	public void testGetHostByName() throws ParseException {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		CommandLine commandLine = parser.parse(options, new String[] {"-h", "server1"});

		DefaultHost host = (DefaultHost) defaultHostManager.getHost(commandLine, console);

		assertEquals(host.getUrl(), "http://server1:8081");
	}

	@Test
	public void testHostNotFound() throws ParseException {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn("<hosts></hosts>");

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		CommandLine commandLine = parser.parse(options, new String[] {"-h", "server1"});

		DefaultHost host = (DefaultHost) defaultHostManager.getHost(commandLine, console);

		assertEquals(console.getOutput(), "Error: no such host 'server1', use 'glimpse host add <host_name> <host_url>' to add a host\n");
		assertNull(host);
	}

	@Test
	public void testGetHostByUrl() throws ParseException {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		DumbConsole console = new DumbConsole();

		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		CommandLine commandLine = parser.parse(options, new String[] {"-u", "http://server2:8081"});

		DefaultHost host = (DefaultHost) defaultHostManager.getHost(commandLine, console);

		assertEquals(host.getUrl(), "http://server2:8081");
	}

	@Test
	public void testListHostSpecs() {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		List<HostSpec> hosts = defaultHostManager.listHostSpecs();

		assertEquals(hosts.size(), 2);

		HostSpec host1 = hosts.get(0);
		assertEquals(host1.getName(), "localhost");
		assertEquals(host1.getUrl(),  "http://localhost:8081");
		assertTrue(host1.isDefaultHost());

		HostSpec host2 = hosts.get(1);
		assertEquals(host2.getName(), "server1");
		assertEquals(host2.getUrl(), "http://server1:8081");
		assertFalse(host2.isDefaultHost());
	}

	@Test
	public void testAddHost() {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn("<hosts></hosts>");

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		defaultHostManager.addHost(new HostSpec("server1", "http://localhost:8081", false, null, null));

		verify(fileSystem).writeHostsFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><hosts><host><name>server1</name><url>http://localhost:8081</url><default>false</default></host></hosts>");
	}

	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void testAddHostThatAlreadyExist() {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		defaultHostManager.addHost(new HostSpec("localhost", null, false, null, null));
	}

	@Test
	public void testAddDefaultHost() {
		FileSystem fileSystem = mock(FileSystem.class);
		when(fileSystem.readHostsFile()).thenReturn(getHostsFileContent());

		DefaultHostManager defaultHostManager = new DefaultHostManager(fileSystem);

		defaultHostManager.addHost(new HostSpec("teste", "teste", true, null, null));

		verify(fileSystem).writeHostsFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><hosts><host><name>localhost</name><url>http://localhost:8081</url><default>false</default></host><host><name>server1</name><url>http://server1:8081</url><default>false</default></host><host><name>teste</name><url>teste</url><default>true</default></host></hosts>");
	}

}
