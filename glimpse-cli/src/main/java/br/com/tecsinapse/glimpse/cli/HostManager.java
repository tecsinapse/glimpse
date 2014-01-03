package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

public interface HostManager {

	Host getHost(CommandLine commandLine, Console console);

}
