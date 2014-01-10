package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public interface HostManager {

	Host getHost(CommandLine commandLine, Console console);

	List<HostSpec> listHostSpecs();

	void addHost(HostSpec hostSpec);

	void deleteHost(String hostName);

	void setDefaultHost(String hostName);
}
