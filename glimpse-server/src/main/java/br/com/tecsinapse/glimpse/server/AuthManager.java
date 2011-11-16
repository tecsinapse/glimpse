package br.com.tecsinapse.glimpse.server;

public class AuthManager {

	private static ThreadLocal<String> usernameHolder = new ThreadLocal<String>();
	
	public static String getCurrentUser() {
		return usernameHolder.get();
	}
	
	public static void setCurrentUser(String username) {
		usernameHolder.set(username);
	}
	
	public static void releaseCurrentUser() {
		usernameHolder.remove();
	}
	
}
