package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class RunCommand implements Command {
	@Override
	public Options getOptions() {
		return new Options();
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		Host host = console.getHost(commandLine);
		if (host != null) {
			if (commandLine.getArgs().length == 0) {
				console.println("Error: a script file must be specified\n\tUsage: glimpse run <script.groovy>");
				return;
			}
			host.runScript(commandLine.getArgs()[0], console);
		}
	}
}
