package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public interface HostManager {

	Host getHost(CommandLine commandLine, Console console);

	List<Host> listHosts();

	void addHost(HostSpec hostSpec);
}
