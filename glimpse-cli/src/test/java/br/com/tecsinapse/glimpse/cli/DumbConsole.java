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

	@Override
	public void deleteHost(String hostName) {
		hostManager.deleteHost(hostName);
	}

	@Override
	public void setDefaultHost(String hostName) {
		hostManager.setDefaultHost(hostName);
	}

	@Override
	public void enableProgressBar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateProgressBar(float percentWorked) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void startExecution() {
	}

	@Override
	public void finishExecution() {
	}

	public String getOutput() {
		return output.toString();
	}

}
