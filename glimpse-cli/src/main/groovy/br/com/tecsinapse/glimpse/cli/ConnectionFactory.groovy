package br.com.tecsinapse.glimpse.cli

class ConnectionFactory {

    static Closure<Connection> connectionClosure

    public static Connection defaultConnection() {
        connectionClosure.call()
    }

    public static Connection hostConnection(String hostName) {
        new HostConnection(hostName)
    }

}
