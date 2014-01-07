package br.com.tecsinapse.glimpse.cli;

public class HostSpec {

	private String name;

	private String url;

	private boolean defaultHost;

	private String username;

	private String password;

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
		if (o == null || getClass() != o.getClass()) return false;

		HostSpec hostSpec = (HostSpec) o;

		if (defaultHost != hostSpec.defaultHost) return false;
		if (!name.equals(hostSpec.name)) return false;
		if (password != null ? !password.equals(hostSpec.password) : hostSpec.password != null) return false;
		if (!url.equals(hostSpec.url)) return false;
		if (username != null ? !username.equals(hostSpec.username) : hostSpec.username != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + url.hashCode();
		result = 31 * result + (defaultHost ? 1 : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		return result;
	}
}
