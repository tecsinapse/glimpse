package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public class ListHostsCommand extends AbstractCommand {
	public ListHostsCommand() {
		super("list-hosts");
	}

	@Override
	protected String getHelpDescription() {
		return "Lists the name and url of the hosts.";
	}

	@Override
	public String getShortDescription() {
		return "Lists the hosts";
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse list-hosts";
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		List<HostSpec> hosts = console.listHostSpecs();
		if (hosts.isEmpty()) {
			console.println("There are no hosts saved");
			return;
		}
		for (HostSpec host : hosts) {
			console.println(host.getName() + " - " + host.getUrl() + (host.isDefaultHost() ? " (default)": ""));
		}
	}
}
