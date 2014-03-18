package br.com.tecsinapse.glimpse.cli

interface Command {

    Command run(PrintWriter writer)

    String nextPrompt()

    Command answer(String answer, Writer writer)

}
