package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.jmx.JmxGlimpseFactory

class ConnectionFactory {

    static Closure<Connection> defaultConnectionClosure = { Console console ->
        def glimpses = JmxGlimpseFactory.listGlimpses()
        if (glimpses.isEmpty()) throw new IllegalStateException("no glimpses")
        if (glimpses.size() == 1) {
            return new UrlConnection(glimpses[0].serviceUrl, null, null)
        } else {
            def glimpsesDescriptions = glimpses.collect { glimpse ->
                return "${glimpse.vmId.padLeft(8, " ")} - ${glimpse.vmDisplayName}"
            }
            def prompt = """Enter the pid of one of the available glimpses:
${glimpsesDescriptions.join("\n")}
"""
            def result = console.ask(prompt)
            def glimpse = glimpses.find { g ->
                g.vmId == result
            }
            if (glimpse == null) {
                throw new IllegalStateException("no such glimpse: ${result}")
            }
            return new UrlConnection(glimpse.serviceUrl, null, null)
        }
    }

    static Connection defaultConnection(Console console) {
        defaultConnectionClosure.call(console)
    }

    static Connection hostConnection(String hostName) {
        new HostConnection(hostName)
    }

    static Connection urlConnection(String url, String username, String password) {
        new UrlConnection(url, username, password)
    }
}
