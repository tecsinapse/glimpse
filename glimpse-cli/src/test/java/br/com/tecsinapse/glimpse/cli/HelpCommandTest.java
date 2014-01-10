package br.com.tecsinapse.glimpse.cli;

import com.beust.jcommander.internal.Lists;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class HelpCommandTest {

	@Test
	public void testExecute() throws ParseException {
		Command command1 = mock(Command.class);
		when(command1.getName()).thenReturn("command1");
		when(command1.getHelp()).thenReturn("help1");
		Command command2 = mock(Command.class);
		when(command2.getName()).thenReturn("command2");
		when(command2.getHelp()).thenReturn("help2");

		HelpCommand helpCommand = new HelpCommand(Arrays.asList(command1, command2));

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine1 = parser.parse(helpCommand.getOptions(), new String[] {"command1"});

		DumbConsole console1 = new DumbConsole();
		helpCommand.execute(commandLine1, console1);
		assertEquals(console1.getOutput(), "help1\n");

		CommandLine commandLine2 = parser.parse(helpCommand.getOptions(), new String[] {"command2"});
		DumbConsole console2 = new DumbConsole();
		helpCommand.execute(commandLine2, console2);
		assertEquals(console2.getOutput(), "help2\n");
	}

	@Test
	public void testNoSuchCommand() throws ParseException {
		HelpCommand helpCommand = new HelpCommand(Lists.<Command>newArrayList());

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(helpCommand.getOptions(), new String[] {"test"});

		DumbConsole console = new DumbConsole();

		helpCommand.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: No such command: test\n");
	}

	@Test
	public void testNoArgument() throws ParseException {
		HelpCommand helpCommand = new HelpCommand(Lists.<Command>newArrayList());

		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(helpCommand.getOptions(), new String[] {});

		DumbConsole console = new DumbConsole();

		helpCommand.execute(commandLine, console);

		assertEquals(console.getOutput(), "Error: A command name must be provided\n");
	}

}
