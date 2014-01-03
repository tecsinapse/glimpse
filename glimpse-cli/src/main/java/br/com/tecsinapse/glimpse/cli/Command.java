package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface Command {

	Options getOptions();

	void execute(CommandLine commandLine, Console console);

}
