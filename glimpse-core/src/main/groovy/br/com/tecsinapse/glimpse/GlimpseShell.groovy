package br.com.tecsinapse.glimpse

import java.util.concurrent.Future

interface GlimpseShell {

    void setParameter(String param, String value)

    Future<String> evaluate(String script, Output output)

}