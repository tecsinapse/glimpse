package br.com.tecsinapse.glimpse.cli

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class HostConnection {

    private String hostName;

    HostConnection(String hostName) {
        this.hostName = hostName
    }

}
