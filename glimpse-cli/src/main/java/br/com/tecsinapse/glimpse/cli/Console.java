package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.Repl;
import org.apache.commons.cli.CommandLine;

import java.util.List;

public interface Console {

	Host getHost(CommandLine commandLine);

	void println(String s);

	List<HostSpec> listHostSpecs();

	void addHost(HostSpec hostSpec);

	void deleteHost(String hostName);

	void setDefaultHost(String hostName);

	void enableProgressBar();

	void updateProgressBar(float percentWorked);

	void startExecution();

	void finishExecution();

	void startRepl(Host host, Repl repl);
}
