package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AddHostCommand implements Command {
	@Override
	public Options getOptions() {
		Options options = new Options();
		Option nameOption = new Option("name", true, "name of the host");
		nameOption.setRequired(true);
		options.addOption(nameOption);
		options.addOption(nameOption);
		Option urlOption = new Option("url", true, "url of the host");
		urlOption.setRequired(true);
		options.addOption(urlOption);
		options.addOption("default", false, "whether the host should be the default");
		options.addOption("username", true, "username used to authenticate the host");
		options.addOption("password", true, "password used to authenticate the host");
		return options;
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		HostSpec hostSpec = new HostSpec(commandLine.getOptionValue("name"), commandLine.getOptionValue("url"), commandLine.hasOption("default"), commandLine.getOptionValue("username"), commandLine.getOptionValue("password"));
		console.addHost(hostSpec);
		console.println(String.format("Host '%s' added", hostSpec.getName()));
	}
}
