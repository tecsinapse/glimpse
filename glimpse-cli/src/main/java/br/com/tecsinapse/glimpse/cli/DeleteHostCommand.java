package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

public class DeleteHostCommand extends AbstractCommand {
	public DeleteHostCommand() {
		super("delete-host");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse delete-host $HOST_NAME";
	}

	@Override
	protected String getHelpDescription() {
		return "Deletes a given host. The name of the host should be the first argument after the command.";
	}

	@Override
	public String getShortDescription() {
		return "Deletes a host";
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		if (commandLine.getArgs().length == 0) {
			console.println("Error: A host name must be provided");
			return;
		}
		String hostName = commandLine.getArgs()[0];
		try {
			console.deleteHost(hostName);
			console.println(String.format("Host '%s' deleted", hostName));
		} catch (IllegalArgumentException e) {
			console.println(String.format("Error: %s", e.getMessage()));
		}
	}
}
