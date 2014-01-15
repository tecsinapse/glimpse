package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class AddHostCommand extends AbstractCommand {
	public AddHostCommand() {
		super("add-host");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse add-host -name $HOST_NAME -url $HOST_URL";
	}

	@Override
	public String getShortDescription() {
		return "Adds a new host";
	}

	@Override
	protected String getHelpDescription() {
		return "Adds a new host. A host defines a server where glimpse scripts could run.";
	}

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
		try {
			console.addHost(hostSpec);
			console.println(String.format("Host '%s' added", hostSpec.getName()));
		} catch (IllegalArgumentException e) {
			console.println(String.format("Error: %s", e.getMessage()));
		}

	}
}
