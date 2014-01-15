package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

public class SetDefaultHostCommand extends AbstractCommand {
	public SetDefaultHostCommand() {
		super("set-default-host");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse set-default-host $HOST_NAME";
	}

	@Override
	public String getShortDescription() {
		return "Sets the default host";
	}

	@Override
	protected String getHelpDescription() {
		return "Sets a given host as the default host. The name of the host to be set as default should be the first argument after the command.";
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		if (commandLine.getArgs().length == 0) {
			console.println("Error: A host name must be provided");
			return;
		}
		String hostName = commandLine.getArgs()[0];
		try {
			console.setDefaultHost(hostName);
			console.println(String.format("Host '%s' set as default", hostName));
		} catch (IllegalArgumentException e) {
			console.println(String.format("Error: %s", e.getMessage()));
		}
	}
}
