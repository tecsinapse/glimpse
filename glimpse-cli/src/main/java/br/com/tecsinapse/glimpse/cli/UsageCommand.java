package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class UsageCommand implements Command {
	@Override
	public Options getOptions() {
		return null;
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		console.println("Welcome to Glimpse!");
	}
}
