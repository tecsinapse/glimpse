package br.com.tecsinapse.glimpse.cli

import br.com.tecsinapse.glimpse.GlimpseShell
import br.com.tecsinapse.glimpse.http.HttpClientHttpHandler
import br.com.tecsinapse.glimpse.http.HttpGlimpse
import br.com.tecsinapse.glimpse.jmx.JmxGlimpseFactory
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class UrlConnection implements Connection {

    private String url
    private String username
    private String password
    private GlimpseShell shell

    UrlConnection(String url, String username, String password) {
        this.url = url
        this.username = username
        this.password = password
    }

    @Override
    String getDescription() {
        return url
    }

    @Override
    GlimpseShell getShell() {
        if (shell == null) {
            if (isHttp(url)) {
                def glimpse = new HttpGlimpse(new HttpClientHttpHandler(url, username, password), 500)
                shell = glimpse.getShell(glimpse.createShell())
            }
            if (isJmx(url)) {
                def glimpse = JmxGlimpseFactory.create(url)
                shell = glimpse.getShell(glimpse.createShell())
            }
        }
        return shell
    }

    static boolean isJmx(String url) {
        return url.startsWith("service:jmx")
    }

    static boolean isHttp(String url) {
        return url.startsWith("http")
    }
}
