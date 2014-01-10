package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class DeleteHostCommand extends AbstractCommand {
	public DeleteHostCommand() {
		super("delete-host");
	}

	@Override
	public Options getOptions() {
		return new Options();
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
