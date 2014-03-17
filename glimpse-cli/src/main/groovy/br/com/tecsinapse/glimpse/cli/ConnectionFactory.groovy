package br.com.tecsinapse.glimpse.cli

class ConnectionFactory {

    static Closure<Connection> defaultConnectionClosure

    static Connection defaultConnection() {
        defaultConnectionClosure.call()
    }

    static Connection hostConnection(String hostName) {
        new HostConnection(hostName)
    }

    static Connection urlConnection(String url, String username, String password) {
        new UrlConnection(url, username, password)
    }
}
