package br.com.tecsinapse.glimpse.cli;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "host")
public class DefaultHost implements Host {

	@XmlElement
	private String name;

	@XmlElement
	private String url;

	@XmlElement(name = "default")
	private boolean defaultHost;

	// for jaxb use
	private DefaultHost() {
	}

	public DefaultHost(String url) {
		this.url = url;
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
		throw new UnsupportedOperationException();
	}
}
