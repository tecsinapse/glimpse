package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class UsageCommandTest {

	@Test
	public void testExecute() throws ParseException {
		Command command1 = mock(Command.class);
		when(command1.getName()).thenReturn("run");
		when(command1.getShortDescription()).thenReturn("short description 1");
		Command command2 = mock(Command.class);
		when(command2.getName()).thenReturn("delete-host");
		when(command2.getShortDescription()).thenReturn("short description 1");
		Command command3 = mock(Command.class);
		when(command3.getName()).thenReturn("list-hosts");
		when(command3.getShortDescription()).thenReturn("short description 1");
		List<Command> commands = Arrays.asList(command1, command2, command3);

		UsageCommand usageCommand = new UsageCommand(commands);

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(usageCommand.getOptions(), new String[] {});

		DumbConsole console = new DumbConsole();

		usageCommand.execute(commandLine, console);

		assertEquals(console.getOutput(), "Welcome to Glimpse!\n" +
				"\n" +
				"Available commands:\n" +
				"run             short description 1\n" +
				"delete-host     short description 1\n" +
				"list-hosts      short description 1\n" +
				"\n" +
				"Run 'glimpse help <command>' for details\n");
	}
}
