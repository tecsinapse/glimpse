package br.com.tecsinapse.glimpse.cli

class DefaultFileSystem implements FileSystem {
    @Override
    String read(String file) {
        new String(new File(file).readBytes())
    }
}
