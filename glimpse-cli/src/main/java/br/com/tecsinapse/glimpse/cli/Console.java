package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

public interface Console {

	Host getHost(CommandLine commandLine);

	void println(String s);
}
