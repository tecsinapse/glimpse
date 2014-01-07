package br.com.tecsinapse.glimpse.cli;

import com.google.common.collect.Lists;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.List;
import java.util.Map;

public class DefaultHostManager implements HostManager {

	private FileSystem fileSystem;

	private Host defaultHost;

	private Map<String, Host> hostsByName;

	public DefaultHostManager(FileSystem fileSystem) {
		this.fileSystem = fileSystem;

		Hosts hosts = Hosts.parse(fileSystem);
		defaultHost = hosts.getDefaultHost();
		hostsByName = hosts.getHostsByName();
	}

	public static void addHostOptions(Options options) {
		options.addOption("h", true, "name of the host");
		options.addOption("u", true, "url of the host");
	}

	@Override
	public Host getHost(CommandLine commandLine, Console console) {
		if (commandLine.hasOption('h')) {
			String hostName = commandLine.getOptionValue('h');
			Host result = hostsByName.get(hostName);
			if (result == null) {
				console.println("Error: no such host '" + hostName + "', use 'glimpse host add <host_name> <host_url>' to add a host");
			}
			return result;
		}
		if (commandLine.hasOption('u')) {
			String url = commandLine.getOptionValue('u');
			return new DefaultHost(url, fileSystem);
		}
		if (defaultHost == null) {
			console.println("Error: there is no default host set, use 'glimpse host default <host_name>' to set a default host");
		}
		return defaultHost;
	}

	@Override
	public List<Host> listHosts() {
		return Lists.newArrayList(hostsByName.values());
	}

	@Override
	public void addHost(HostSpec hostSpec) {
		throw new UnsupportedOperationException();
	}
}
