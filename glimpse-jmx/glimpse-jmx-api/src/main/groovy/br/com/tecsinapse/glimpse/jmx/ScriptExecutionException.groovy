package br.com.tecsinapse.glimpse.jmx


class ScriptExecutionException extends Exception {

    final String causeTrace;

    public ScriptExecutionException(Throwable e) {
        def stringWriter = new StringWriter()
        e.printStackTrace(new PrintWriter(stringWriter))
        causeTrace = stringWriter.getBuffer().toString()
    }

}
