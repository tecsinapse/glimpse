package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BootstrapTest {

	@Test
	public void testNoArgsCommand() {
		Console console = mock(Console.class);
		Bootstrap.console = console;

		Command mockCommand = mock(Command.class);
		Bootstrap.noArgsCommand = mockCommand;

		Bootstrap.main(new String[] {});

		verify(mockCommand).execute(null, console);
	}

	@Test
	public void testExecuteCommand() {
		final Console mockConsole = mock(Console.class);
		Bootstrap.console = mockConsole;

		Command command1 = new AbstractCommand("command1") {
			@Override
			public Options getOptions() {
				return new Options();
			}

			@Override
			public void execute(CommandLine commandLine, Console output) {
				assertTrue(commandLine.getArgList().isEmpty());
				assertEquals(output, mockConsole);
			}
		};

		final Options command2Options = new Options();
		command2Options.addOption("p", true, "mock option");
		Command command2 = new AbstractCommand("command2") {
			@Override
			public Options getOptions() {
				return command2Options;
			}

			@Override
			public void execute(CommandLine commandLine, Console output) {
				assertTrue(commandLine.hasOption('p'));
				assertEquals("test", commandLine.getOptionValue('p'));
				assertEquals(output, mockConsole);
			}
		};

		Map<String, Command> commandMap = new HashMap<String, Command>();
		commandMap.put("command1", command1);
		commandMap.put("command2", command2);
		Bootstrap.commandsMap = commandMap;

		Bootstrap.main(new String[] {"command1"});
		Bootstrap.main(new String[] {"command2", "-p", "test"});
	}

	@Test
	public void testNonExistentCommand() {
		DumbConsole output = new DumbConsole();
		Bootstrap.console = output;

		Bootstrap.main(new String[] {"nonexistent"});

		assertEquals(output.getOutput(), "Error: command 'nonexistent' does not exist\n");
	}

	@Test
	public void testParsingError() {
		DumbConsole output = new DumbConsole();
		Bootstrap.console = output;

		Command command1 = mock(Command.class);
		when(command1.getOptions()).thenReturn(new Options());

		Bootstrap.commandsMap = Collections.singletonMap("command1", command1);

		Bootstrap.main(new String[] {"command1", "-p"});

		assertEquals(output.getOutput(), "Error: Unrecognized option: -p\n");
	}


}
