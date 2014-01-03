package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.ScriptRunner;
import br.com.tecsinapse.glimpse.client.ScriptRunnerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "host")
public class DefaultHost implements Host {

	@XmlElement
	@SuppressWarnings("UnusedDeclaration")
	private String name;

	@XmlElement
	private String url;

	@XmlElement(name = "default")
	@SuppressWarnings("UnusedDeclaration")
	private boolean defaultHost;

	@XmlElement
	@SuppressWarnings("UnusedDeclaration")
	private String username;

	@XmlElement
	@SuppressWarnings("UnusedDeclaration")
	private String password;

	private FileSystem fileSystem;

	// for jaxb use
	@SuppressWarnings("UnusedDeclaration")
	private DefaultHost() {
	}

	public DefaultHost(String url, FileSystem fileSystem) {
		this.url = url;
		this.fileSystem = fileSystem;
	}

	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public boolean isDefaultHost() {
		return defaultHost;
	}

	@Override
	public void runScript(String scriptFileName, Console console) {
		ScriptRunner scriptRunner = ScriptRunnerFactory.create(url, username, password);
		scriptRunner.run(fileSystem.readFile(scriptFileName), new ConsoleMonitor(console));
	}
}
