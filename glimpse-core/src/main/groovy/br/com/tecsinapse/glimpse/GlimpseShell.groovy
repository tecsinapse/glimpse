package br.com.tecsinapse.glimpse

interface GlimpseShell {

    void setParameter(String param, String value)

    void setOutputStream(PrintStream out)

    Object evaluate(String script)

}