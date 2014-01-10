package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.ScriptRunner;
import br.com.tecsinapse.glimpse.client.ScriptRunnerFactory;

public class DefaultHost implements Host {

	private HostSpec hostSpec;

	private FileSystem fileSystem;

	// for jaxb use
	@SuppressWarnings("UnusedDeclaration")
	private DefaultHost() {
	}

	public DefaultHost(HostSpec hostSpec, FileSystem fileSystem) {
		this.hostSpec = hostSpec;
		this.fileSystem = fileSystem;
	}

	public String getUrl() {
		return hostSpec.getUrl();
	}

	@Override
	public void runScript(String scriptFileName, Console console) {
		ScriptRunner scriptRunner = ScriptRunnerFactory.create(hostSpec.getUrl(), hostSpec.getUsername(), hostSpec.getPassword());
		scriptRunner.run(fileSystem.readFile(scriptFileName), new ConsoleMonitor(console));
	}
}
