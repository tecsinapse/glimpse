package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.List;

public class DefaultHostManager implements HostManager {

	private FileSystem fileSystem;

	private Hosts hosts;

	public DefaultHostManager(FileSystem fileSystem) {
		this.fileSystem = fileSystem;

		hosts = Hosts.parse(fileSystem);
	}

	public static void addHostOptions(Options options) {
		options.addOption("host", true, "name of the host where the script should be run");
		options.addOption("url", true, "url of an alternative host (only use this option if in need to run on a host that is not saved)");
	}

	@Override
	public Host getHost(CommandLine commandLine, Console console) {
		if (commandLine.hasOption("host")) {
			String hostName = commandLine.getOptionValue("host");
			Host result = hosts.getByName(hostName);
			if (result == null) {
				console.println("Error: no such host '" + hostName + "', use 'glimpse host add <host_name> <host_url>' to add a host");
			}
			return result;
		}
		if (commandLine.hasOption("url")) {
			String url = commandLine.getOptionValue("url");
			return new DefaultHost(new HostSpec(url, url, false, null, null), fileSystem);
		}
		Host defaultHost = hosts.getDefaultHost();
		if (defaultHost == null) {
			console.println("Error: there is no default host set, use 'glimpse set-default-host <host_name>' to set a default host");
		}
		return defaultHost;
	}

	@Override
	public List<HostSpec> listHostSpecs() {
		return hosts.getHostSpecs();
	}

	@Override
	public void addHost(HostSpec hostSpec) {
		hosts.addHost(hostSpec);
	}

	@Override
	public void deleteHost(String hostName) {
		hosts.deleteHost(hostName);
	}

	@Override
	public void setDefaultHost(String hostName) {
		hosts.setDefaultHost(hostName);
	}
}
