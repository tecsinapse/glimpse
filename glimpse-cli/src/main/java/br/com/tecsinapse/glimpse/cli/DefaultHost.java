package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.Repl;
import br.com.tecsinapse.glimpse.client.ReplManager;
import br.com.tecsinapse.glimpse.client.ReplManagerFactory;
import br.com.tecsinapse.glimpse.client.ScriptRunner;
import br.com.tecsinapse.glimpse.client.ScriptRunnerFactory;

import java.util.Map;

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
	public void startRepl(Console console) {
		ReplManager replManager = ReplManagerFactory.create(hostSpec.getUrl(), hostSpec.getUsername(), hostSpec.getPassword());
		Repl repl = replManager.createRepl();
		console.startRepl(this, repl);
		repl.close();
	}

	@Override
	public void runScript(String scriptFileName, Map<String, String> params, Console console) {
		ScriptRunner scriptRunner = ScriptRunnerFactory.create(hostSpec.getUrl(), hostSpec.getUsername(), hostSpec.getPassword());
		scriptRunner.run(fileSystem.readFile(scriptFileName), params, new ConsoleMonitor(console));
	}
}
