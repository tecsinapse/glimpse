package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import static java.lang.String.format;

public class RunCommand extends AbstractCommand {
	public RunCommand() {
		super("run");
	}

	@Override
	public Options getOptions() {
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);
		return options;
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		Host host = console.getHost(commandLine);
		if (host != null) {
			if (commandLine.getArgs().length == 0) {
				console.println("Error: a script file must be specified\n\tUsage: glimpse run <script.groovy>");
				return;
			}
			console.println(format("Executing script at: %s", host.getUrl()));
			console.println("--------------------------------------------------");
			console.println("");
			host.runScript(commandLine.getArgs()[0], console);
			console.println("");
			console.println("--------------------------------------------------");
			console.println("Finished executing script");
		}
	}
}
