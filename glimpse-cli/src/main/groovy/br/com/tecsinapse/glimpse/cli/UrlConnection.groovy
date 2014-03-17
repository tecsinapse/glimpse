package br.com.tecsinapse.glimpse.cli

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class UrlConnection implements Connection {

    private String url
    private String username
    private String password

    UrlConnection(String url, String username, String password) {
        this.url = url
        this.username = username
        this.password = password
    }
}
