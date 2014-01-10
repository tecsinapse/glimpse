package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DefaultFileSystem implements FileSystem {

	public static final String HOSTS_FILES_NAME = "hosts.xml";
	public static final String ENCODING = "UTF-8";
	private File glimpseHome;

	public DefaultFileSystem(File glimpseHome) {
		this.glimpseHome = glimpseHome;
	}

	@Override
	public String readHostsFile() {
		try {
			return FileUtils.readFileToString(getHostsFile());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private File getHostsFile() {
		return new File(glimpseHome, HOSTS_FILES_NAME);
	}

	@Override
	public String readFile(String fileName) {
		try {
			return FileUtils.readFileToString(new File(fileName), ENCODING);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void writeHostsFile(String content) {
		try {
			FileUtils.writeStringToFile(getHostsFile(), content, ENCODING);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
