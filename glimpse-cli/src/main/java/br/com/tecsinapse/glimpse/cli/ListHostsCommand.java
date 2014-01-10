package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.List;

public class ListHostsCommand implements Command {
	@Override
	public Options getOptions() {
		return new Options();
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
