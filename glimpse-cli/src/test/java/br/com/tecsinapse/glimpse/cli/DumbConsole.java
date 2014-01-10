package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public class DumbConsole implements Console {

	private HostManager hostManager;

	private StringBuilder output = new StringBuilder();

	public DumbConsole() {
	}

	public DumbConsole(HostManager hostManager) {
		this.hostManager = hostManager;
	}

	public void setHostManager(HostManager hostManager) {
		this.hostManager = hostManager;
	}


	@Override
	public Host getHost(CommandLine commandLine) {
		return hostManager.getHost(commandLine, this);
	}

	@Override
	public void println(String s) {
		output.append(s);
		output.append("\n");
	}

	@Override
	public List<HostSpec> listHostSpecs() {
		return hostManager.listHostSpecs();
	}

	@Override
	public void addHost(HostSpec hostSpec) {
		hostManager.addHost(hostSpec);
	}

	public String getOutput() {
		return output.toString();
	}

}
