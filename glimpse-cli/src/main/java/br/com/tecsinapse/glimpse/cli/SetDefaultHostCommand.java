package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class SetDefaultHostCommand implements Command {
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
			console.setDefaultHost(hostName);
			console.println(String.format("Host '%s' set as default", hostName));
		} catch (IllegalArgumentException e) {
			console.println(String.format("Error: %s", e.getMessage()));
		}
	}
}
