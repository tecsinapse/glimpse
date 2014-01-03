package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DefaultFileSystem implements FileSystem {

	private File glimpseHome;

	public DefaultFileSystem(File glimpseHome) {
		this.glimpseHome = glimpseHome;
	}

	@Override
	public String readHostsFile() {
		try {
			return FileUtils.readFileToString(new File(glimpseHome, "hosts.xml"));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String readFile(String fileName) {
		try {
			return FileUtils.readFileToString(new File(fileName));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
