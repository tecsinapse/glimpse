package br.com.tecsinapse.glimpse.cli;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class HelpCommand extends AbstractCommand {

	private Map<String, Command> commandsMap;

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse help $COMMAND_NAME";
	}

	@Override
	protected String getHelpDescription() {
		return "Shows the help for a given command. The command should be the first argument after the command.";
	}

	@Override
	public String getShortDescription() {
		return "Shows a command help";
	}

	public HelpCommand(List<Command> commands) {
		super("help");
		this.commandsMap = Maps.newHashMap(Maps.uniqueIndex(commands, new Function<Command, String>() {
			@Nullable
			@Override
			public String apply(Command command) {
				return command.getName();
			}
		}));
		this.commandsMap.put("help", this);
	}

	@Override
	public Options getOptions() {
		return new Options();
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		if (commandLine.getArgs().length == 0) {
			console.println("Error: A command name must be provided");
			return;
		}
		String commandName = commandLine.getArgs()[0];
		Command command = commandsMap.get(commandName);
		if (command == null) {
			console.println(format("Error: No such command: %s", commandName));
			return;
		}
		console.println(commandsMap.get(commandName).getHelp());
	}
}
