package br.com.tecsinapse.glimpse

import java.util.concurrent.Future

interface GlimpseShell {

    void setParameter(String param, String value)

    void setOutputStream(PrintStream out)

    Future evaluate(String script)

}