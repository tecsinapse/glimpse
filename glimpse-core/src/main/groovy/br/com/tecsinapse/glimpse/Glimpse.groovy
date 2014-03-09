package br.com.tecsinapse.glimpse

interface Glimpse {

    String createShell()

    GlimpseShell getShell(String id)

    void destroyShell(String id)

}
