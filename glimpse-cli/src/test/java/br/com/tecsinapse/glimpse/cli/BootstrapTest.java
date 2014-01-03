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
		Output output = mock(Output.class);
		Bootstrap.output = output;

		Command mockCommand = mock(Command.class);
		Bootstrap.noArgsCommand = mockCommand;

		Bootstrap.main(new String[] {});

		verify(mockCommand).execute(null, output);
	}

	@Test
	public void testExecuteCommand() {
		final Output mockOutput = mock(Output.class);
		Bootstrap.output = mockOutput;

		Command command1 = new Command() {
			@Override
			public Options getOptions() {
				return new Options();
			}

			@Override
			public void execute(CommandLine commandLine, Output output) {
				assertTrue(commandLine.getArgList().isEmpty());
				assertEquals(output, mockOutput);
			}
		};

		final Options command2Options = new Options();
		command2Options.addOption("p", true, "mock option");
		Command command2 = new Command() {
			@Override
			public Options getOptions() {
				return command2Options;
			}

			@Override
			public void execute(CommandLine commandLine, Output output) {
				assertTrue(commandLine.hasOption('p'));
				assertEquals("test", commandLine.getOptionValue('p'));
				assertEquals(output, mockOutput);
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
		DumbOutput output = new DumbOutput();
		Bootstrap.output = output;

		Bootstrap.main(new String[] {"nonexistent"});

		assertEquals(output.getOutput(), "Error: command 'nonexistent' does not exist\n");
	}

	@Test
	public void testParsingError() {
		DumbOutput output = new DumbOutput();
		Bootstrap.output = output;

		Command command1 = mock(Command.class);
		when(command1.getOptions()).thenReturn(new Options());

		Bootstrap.commandsMap = Collections.singletonMap("command1", command1);

		Bootstrap.main(new String[] {"command1", "-p"});

		assertEquals(output.getOutput(), "Error: Unrecognized option: -p\n");
	}


}
