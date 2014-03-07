package br.com.tecsinapse.glimpse.http

import com.sun.net.httpserver.HttpExchange

class ExchangeTemplate {

    private HttpExchange exchange;

    ExchangeTemplate(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public String getRequestBody() throws IOException {
        InputStream input = exchange.getRequestBody();
        StringBuilder script = new StringBuilder();
        int c = 0;
        while ((c = input.read()) != -1) {
            script.append((char) c);
        }
        return script.toString();
    }

    public void setResponseOk() throws IOException {
        exchange.sendResponseHeaders(200, 0);
    }

    public void setResponseInternalServerError() throws IOException {
        exchange.sendResponseHeaders(500, 0);
    }

    public void writeReponse(String response) throws IOException {
        OutputStream out = exchange.getResponseBody();
        out.write(response.getBytes());
        out.flush();
        out.close();
    }
}
