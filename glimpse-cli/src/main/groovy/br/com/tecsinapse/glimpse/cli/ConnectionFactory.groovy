package br.com.tecsinapse.glimpse.cli

class ConnectionFactory {

    static Closure<Connection> defaultConnectionClosure

    public static Connection defaultConnection() {
        defaultConnectionClosure.call()
    }

    public static Connection hostConnection(String hostName) {
        new HostConnection(hostName)
    }

}
