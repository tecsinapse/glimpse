package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.GlimpseServer
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

class SunHttpGlimpseConnector {

    private int port;

    private GlimpseServer glimpseServer

    private HttpServer httpServer;

    SunHttpGlimpseConnector(GlimpseServer glimpseServer, int port) {
        this.glimpseServer = glimpseServer
        this.port = port
    }

    boolean isStarted() {
        return httpServer != null
    }

    void start() {
        if (httpServer != null) throw new IllegalStateException("server already started")
        def address = new InetSocketAddress(port)
        def jsonHandler = new JsonHandler()
        def httpHandler = new HttpHandler() {
            @Override
            void handle(HttpExchange httpExchange) throws IOException {
                def template = new ExchangeTemplate(httpExchange)
                def body = template.getRequestBody()
                def response = jsonHandler.handle(body)
                template.setResponseOk()
                template.writeReponse(response)
            }
        }
        httpServer.createContext("/", httpHandler)
        httpServer.start()
    }

    void stop() {
        if (httpServer == null) throw new IllegalStateException("server not started")
        httpServer.stop(0)
        httpServer == null
    }

}
