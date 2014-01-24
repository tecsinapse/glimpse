package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Collections;

import static java.lang.String.format;

public class RunCommand extends AbstractCommand {
	public RunCommand() {
		super("run");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse run $SCRIPT_FILE";
	}

	@Override
	protected String getHelpDescription() {
		return "Runs a given script. If no host name is provided the script is run on the default host. Options are provided to run the script in another host.";
	}

	@Override
	public String getShortDescription() {
		return "Runs a given script";
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
			console.startExecution();
			host.runScript(commandLine.getArgs()[0], Collections.<String, String>emptyMap(), console);
			console.finishExecution();
			console.println("");
			console.println("--------------------------------------------------");
			console.println("Finished executing script");
		}
	}
}
