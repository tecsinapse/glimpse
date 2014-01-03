package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Bootstrap {

	static Output output;

	static Command noArgsCommand;
	static Map<String, Command> commandsMap = new HashMap<String, Command>();

	private static CommandLineParser parser = new PosixParser();

	public static void main(String[] args) {
		if (args.length == 0) {
			noArgsCommand.execute(null, output);
		} else {
			Command command = commandsMap.get(args[0]);
			if (command == null) {
				output.println(String.format("Error: command '%s' does not exist", args[0]));
				return;
			}
			Options options = command.getOptions();
			try {
				CommandLine commandLine = parser.parse(options, Arrays.copyOfRange(args, 1, args.length));
				command.execute(commandLine, output);
			} catch (ParseException e) {
				output.println("Error: " + e.getMessage());
			}
		}
	}

}
