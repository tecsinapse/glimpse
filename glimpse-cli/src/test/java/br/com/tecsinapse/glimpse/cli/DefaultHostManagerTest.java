package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

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
				"\t\t<default>true</default>\n" +
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

}
