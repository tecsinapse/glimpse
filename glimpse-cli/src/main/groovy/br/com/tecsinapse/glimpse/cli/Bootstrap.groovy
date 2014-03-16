package br.com.tecsinapse.glimpse.cli

class Bootstrap {

    public static void main(String[] args) {
        def console = ConsoleFactory.createConsole()
        if (args.length == 0) {
            console.start(ConnectionFactory.defaultConnection())
        } else {
            console.runScript(ConnectionFactory.defaultConnection(), args[0])
        }
    }

}
