package br.com.tecsinapse.glimpse.selfinstall;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class SelfInstall {

	public static void main(String [] args) throws Exception {
		File glimpseHome = new File(System.getProperty("user.home"), ".glimpse");

		if (!isInstalled(glimpseHome)) {
			install();
		}

		File[] files = getLibDir(glimpseHome).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		URL[] urls = new URL[files.length + 1];
		for (int i=0; i<files.length; i++) {
			urls[i] = files[i].toURI().toURL();
		}
        urls[files.length] = new File(System.getProperty("java.home") + File.separator + ".." + File.separator + "lib" + File.separator + "tools.jar").toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(urls);
		Class clazz = classLoader.loadClass("br.com.tecsinapse.glimpse.cli.Bootstrap");
		Method method = clazz.getMethod("main", String[].class);
		method.invoke(null, new Object[] {args});
	}

	private static void install() {
		throw new UnsupportedOperationException();
	}

	private static boolean isInstalled(File glimpseHome) {
		return getLibDir(glimpseHome).exists();
	}

	private static File getLibDir(File glimpseHome) {
		return new File(glimpseHome, "lib");
	}

}