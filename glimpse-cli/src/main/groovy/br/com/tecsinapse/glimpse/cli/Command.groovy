package br.com.tecsinapse.glimpse.cli

interface Command {

    Command run(PrintWriter writer)

}
