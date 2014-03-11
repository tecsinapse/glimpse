package br.com.tecsinapse.glimpse.http

import br.com.tecsinapse.glimpse.Glimpse
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

class SunHttpGlimpseConnector {

    private int port;

    private Glimpse glimpse

    private HttpServer httpServer;

    SunHttpGlimpseConnector(Glimpse glimpse, int port) {
        this.glimpse = glimpse
        this.port = port
    }

    boolean isStarted() {
        return httpServer != null
    }

    void start() {
        if (httpServer != null) throw new IllegalStateException("server already started")
        def address = new InetSocketAddress(port)
        def jsonHandler = new JsonHandler(glimpse)
        def httpHandler = new HttpHandler() {
            @Override
            void handle(HttpExchange httpExchange) throws IOException {
                def template = new ExchangeTemplate(httpExchange)
                try {
                    def body = template.getRequestBody()
                    def response = jsonHandler.handle(body)
                    template.setResponseOk()
                    template.writeReponse(response)
                } catch (e) {
                    e.printStackTrace()
                    template.setResponseInternalServerError();
                }
            }
        }
        httpServer = HttpServer.create(address, -1)
        httpServer.createContext("/", httpHandler)
        httpServer.start()
    }

    void stop() {
        if (httpServer == null) throw new IllegalStateException("server not started")
        httpServer.stop(0)
        httpServer == null
    }

}
