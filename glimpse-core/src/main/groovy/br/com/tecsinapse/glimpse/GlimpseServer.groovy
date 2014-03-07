package br.com.tecsinapse.glimpse

interface GlimpseServer {

    String createShell()

    GlimpseShell getShell(String id)

    void destroyShell(String id)

}
