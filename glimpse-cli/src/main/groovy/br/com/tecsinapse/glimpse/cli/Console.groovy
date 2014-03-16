package br.com.tecsinapse.glimpse.cli

public interface Console {

    void start(Connection connection)

    void runScript(Connection connection, String scriptFile)
}