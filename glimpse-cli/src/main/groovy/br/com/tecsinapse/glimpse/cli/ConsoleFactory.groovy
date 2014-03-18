package br.com.tecsinapse.glimpse.cli

class ConsoleFactory {

    static Closure<Console> consoleClosure = {
        new JlineConsole()
    }

    static Console createConsole() {
        consoleClosure.call()
    }

}
