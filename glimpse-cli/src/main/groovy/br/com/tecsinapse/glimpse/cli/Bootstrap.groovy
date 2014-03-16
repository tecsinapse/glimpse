package br.com.tecsinapse.glimpse.cli

class Bootstrap {

    public static void main(String[] args) {
        def console = ConsoleFactory.createConsole()

        def cli = new CliBuilder(usage:'glimpse [options] [script]')
        cli.h(args: 1, argName: 'hostName', 'host name')
        cli.writer = console.writer

        def options = cli.parse(args)
        if (!options) return

        def arguments = options.arguments()

        def connection
        if (options.h) {
            connection = ConnectionFactory.hostConnection(options.h)
        } else {
            connection = ConnectionFactory.defaultConnection()
        }

        if (arguments.isEmpty()) {
            console.start(connection)
        } else {
            console.runScript(connection, arguments[0])
        }
    }

}
