package br.com.tecsinapse.glimpse.cli;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.sort;

public class Bootstrap {

	static Console console;

	static Command noArgsCommand;
	static Map<String, Command> commandsMap;
	static {
		List<Command> commands = Lists.<Command>newArrayList(
			new RunCommand(),
			new ListHostsCommand(),
			new AddHostCommand(),
			new DeleteHostCommand(),
			new SetDefaultHostCommand()
		);

		HelpCommand helpCommand = new HelpCommand(Lists.newArrayList(commands));
		commands.add(helpCommand);

		sort(commands, new Comparator<Command>() {
			@Override
			public int compare(Command o1, Command o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		noArgsCommand = new UsageCommand(commands);

		commandsMap = Maps.uniqueIndex(commands, new Function<Command, String>() {
			@Override
			public String apply(Command command) {
				return command.getName();
			}
		});
	}

	private static CommandLineParser parser = new PosixParser();

	public static void main(String[] args) {
		if (console == null) {
			File glimpseHome = new File(System.getProperty("user.home"), ".glimpse");
			console = new DefaultConsole(new DefaultHostManager(new DefaultFileSystem(glimpseHome)));
		}

		if (args.length == 0) {
			noArgsCommand.execute(null, console);
		} else {
			Command command = commandsMap.get(args[0]);
			if (command == null) {
				console.println(String.format("Error: command '%s' does not exist", args[0]));
				return;
			}
			Options options = command.getOptions();
			try {
				CommandLine commandLine = parser.parse(options, Arrays.copyOfRange(args, 1, args.length));
				command.execute(commandLine, console);
			} catch (ParseException e) {
				console.println("Error: " + e.getMessage());
			}
		}
	}

}
