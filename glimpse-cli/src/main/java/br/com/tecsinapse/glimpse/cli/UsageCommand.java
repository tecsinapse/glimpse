package br.com.tecsinapse.glimpse.cli;

import com.google.common.base.Strings;
import org.apache.commons.cli.CommandLine;

import java.util.List;

public class UsageCommand extends AbstractCommand {

	private List<Command> commands;

	public UsageCommand(List<Command> commands) {
		super("");
		this.commands = commands;
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		console.println("Welcome to Glimpse!");
		console.println("");
		console.println("Available commands:");
		int longestNameLength = 0;
		for (Command command: commands) {
			longestNameLength = command.getName().length() > longestNameLength ? command.getName().length() : longestNameLength;
		}
		for (Command command: commands) {
			StringBuilder commandText = new StringBuilder();
			commandText.append(command.getName());
			int numberOfSpaces = longestNameLength - command.getName().length() + 5;
			commandText.append(Strings.repeat(" ", numberOfSpaces));
			commandText.append(command.getShortDescription());
			console.println(commandText.toString());
		}
		console.println("");
		console.println("Run 'glimpse help <command>' for details");
	}
}
