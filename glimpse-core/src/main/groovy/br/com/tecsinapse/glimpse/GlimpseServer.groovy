package br.com.tecsinapse.glimpse

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder

import java.util.concurrent.TimeUnit

class GlimpseServer {

    private GlimpseShellFactory factory

    private Cache<String, GlimpseShell> cache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    GlimpseServer(GlimpseShellFactory factory) {
        this.factory = factory
    }

    String createShell() {
        def id = UUID.randomUUID().toString()
        def shell = factory.create()
        cache.put(id, shell)
        return id
    }

    GlimpseShell getShell(String id) {
        return cache.getIfPresent(id)
    }

    void destroyShell(String id) {
        cache.invalidate(id)
    }

}
