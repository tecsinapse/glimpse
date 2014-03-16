package br.com.tecsinapse.glimpse.cli

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class HostConnection implements Connection {

    private String hostName;

    HostConnection(String hostName) {
        this.hostName = hostName
    }

}
