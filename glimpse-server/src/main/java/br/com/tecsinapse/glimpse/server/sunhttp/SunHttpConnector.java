package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;
import java.net.InetSocketAddress;

import br.com.tecsinapse.glimpse.server.Authenticator;
import br.com.tecsinapse.glimpse.server.ByPassAuthenticator;
import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SunHttpConnector {

	private Server server;
	
	private HttpServer httpServer;
	
	private int port;
	
	private Authenticator authenticator;
	
	public SunHttpConnector(Server server, int port) {
		this(server, port, new ByPassAuthenticator());
	}
	
	public SunHttpConnector(Server server, int port, Authenticator authenticator) {
		this.server = server;
		this.port = port;
		this.authenticator = authenticator;
	}
	
	public boolean isStarted() {
		return httpServer != null;
	}
	
	public void start() {
		try {
			InetSocketAddress address = new InetSocketAddress(port);
			SunHttpAuthenticator sha = new SunHttpAuthenticator(authenticator);
			httpServer = httpServer.create(address, -1);
			HttpHandler startHandler = new AuthenticationHandler(new StartHandler(server));
			HttpHandler cancelHandler = new AuthenticationHandler(new CancelHandler(server));
			HttpHandler pollHandler = new AuthenticationHandler(new PollHandler(server));
			httpServer.createContext("/start", startHandler).setAuthenticator(sha);
		    httpServer.createContext("/cancel", cancelHandler).setAuthenticator(sha);
		    httpServer.createContext("/poll", pollHandler).setAuthenticator(sha);
			httpServer.start();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public void stop() {
		httpServer.stop(0);
		httpServer = null;
	}
	
	public void restart() {
		stop();
		start();
	}
	
}
