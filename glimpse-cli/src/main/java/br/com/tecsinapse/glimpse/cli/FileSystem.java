package br.com.tecsinapse.glimpse.cli;

public interface FileSystem {

	String readHostsFile();

	String readFile(String fileName);
}
