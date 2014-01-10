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
		options.addOption("h", true, "name of the host");
		options.addOption("u", true, "url of the host");
	}

	@Override
	public Host getHost(CommandLine commandLine, Console console) {
		if (commandLine.hasOption('h')) {
			String hostName = commandLine.getOptionValue('h');
			Host result = hosts.getByName(hostName);
			if (result == null) {
				console.println("Error: no such host '" + hostName + "', use 'glimpse host add <host_name> <host_url>' to add a host");
			}
			return result;
		}
		if (commandLine.hasOption('u')) {
			String url = commandLine.getOptionValue('u');
			return new DefaultHost(new HostSpec(url, url, false, null, null), fileSystem);
		}
		Host defaultHost = hosts.getDefaultHost();
		if (defaultHost == null) {
			console.println("Error: there is no default host set, use 'glimpse host default <host_name>' to set a default host");
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
}
