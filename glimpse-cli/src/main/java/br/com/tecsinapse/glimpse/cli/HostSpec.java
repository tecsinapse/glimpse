package br.com.tecsinapse.glimpse.cli;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "host")
public class HostSpec {

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

	// for jaxb use
	@SuppressWarnings("UnusedDeclaration")
	private HostSpec() {
	}

	public HostSpec(String name, String url, boolean defaultHost, String username, String password) {
		this.name = name;
		this.url = url;
		this.defaultHost = defaultHost;
		this.username = username;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public boolean isDefaultHost() {
		return defaultHost;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof HostSpec)) return false;

		HostSpec hostSpec = (HostSpec) o;

		return name.equals(hostSpec.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public HostSpec asNonDefault() {
		return new HostSpec(name, url, false, username, password);
	}

	public HostSpec asDefault() {
		return new HostSpec(name, url, true, username, password);
	}
}
