package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.CommandLine;

import java.util.List;

public class DefaultConsole implements Console {

	private final HostManager hostManager;

	public DefaultConsole(HostManager hostManager) {
		this.hostManager = hostManager;
	}

	@Override
	public Host getHost(CommandLine commandLine) {
		return hostManager.getHost(commandLine, this);
	}

	@Override
	public void println(String s) {
		System.out.println(s);
	}

	@Override
	public List<Host> listHosts() {
		return hostManager.listHosts();
	}

	@Override
	public void addHost(HostSpec hostSpec) {
		hostManager.addHost(hostSpec);
	}
}
