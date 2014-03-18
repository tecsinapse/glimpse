package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.GlimpseShell


public interface Connection {

    String getDescription()

    GlimpseShell getShell()
}