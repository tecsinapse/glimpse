package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class ReplCommand extends AbstractCommand {
	public ReplCommand() {
		super("repl");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse repl";
	}

	@Override
	protected String getHelpDescription() {
		return "Starts a new Repl. A Repl allows one to enter arbitrary commands and run them on the glimpse host.";
	}

	@Override
	public String getShortDescription() {
		return "Starts a new Repl";
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
			host.startRepl(console);
		}
	}
}
