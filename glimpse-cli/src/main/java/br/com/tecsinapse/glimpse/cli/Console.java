package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public interface Console {

	Host getHost(CommandLine commandLine);

	void println(String s);

	List<Host> listHosts();
}
