package br.com.tecsinapse.glimpse.cli

class Bootstrap {

    public static void main(String[] args) {
        def cli = new CliBuilder(usage:'glimpse [options] [script]')
        cli.h(args: 1, argName: 'hostName', 'host name')

        def options = cli.parse(args)
        def arguments = options.arguments()

        def connection
        if (options.h) {
            connection = ConnectionFactory.hostConnection(options.h)
        } else {
            connection = ConnectionFactory.defaultConnection()
        }

        def console = ConsoleFactory.createConsole()
        if (arguments.isEmpty()) {
            console.start(connection)
        } else {
            console.runScript(connection, arguments[0])
        }
    }

}
