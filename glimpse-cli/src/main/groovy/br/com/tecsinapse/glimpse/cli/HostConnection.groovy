package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.GlimpseShell
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class HostConnection implements Connection {

    private String hostName;

    HostConnection(String hostName) {
        this.hostName = hostName
    }

    @Override
    String getDescription() {
        throw new UnsupportedOperationException()
    }

    @Override
    GlimpseShell getShell() {
        throw new UnsupportedOperationException()
    }
}
