package br.com.tecsinapse.glimpse.server.sunhttp;

import java.io.IOException;
import java.net.InetSocketAddress;

import br.com.tecsinapse.glimpse.server.Server;

import com.sun.net.httpserver.HttpServer;

public class SunHttpConnector {

	private Server server;
	
	private HttpServer httpServer;
	
	private int port = 8080;
	
	public SunHttpConnector(Server server) {
		this.server = server;
	}
	
	public boolean isStarted() {
		return httpServer != null;
	}
	
	public void start() {
		try {
			InetSocketAddress address = new InetSocketAddress(port);
			httpServer = httpServer.create(address, -1);
			StartHandler startHandler = new StartHandler(server);
			CancelHandler cancelHandler = new CancelHandler(server);
			PollHandler pollHandler = new PollHandler(server);
			httpServer.createContext("/start", startHandler);
		    httpServer.createContext("/cancel", cancelHandler);
		    httpServer.createContext("/poll", pollHandler);
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
