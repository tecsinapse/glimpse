package br.com.tecsinapse.glimpse.cli

class ConsoleFactory {

    static Closure<Console> consoleClosure

    static Console createConsole() {
        consoleClosure.call()
    }

}
