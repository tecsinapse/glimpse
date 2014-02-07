package br.com.tecsinapse.glimpse.cli;

import br.com.tecsinapse.glimpse.client.Repl;
import com.google.common.base.Strings;
import jline.console.ConsoleReader;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DefaultConsole implements Console {

	private final static int PRINTLN_MINIMUM_LENGTH = 100;

	private final static long PROGRESS_DELAY = 500;

	private final HostManager hostManager;

	private ProgressController progressController = new ProgressController();

	private Timer progressTimer;

	public DefaultConsole(HostManager hostManager) {
		this.hostManager = hostManager;
	}

	@Override
	public Host getHost(CommandLine commandLine) {
		return hostManager.getHost(commandLine, this);
	}

	@Override
	public void println(String s) {
		System.out.println(Strings.padEnd(s, PRINTLN_MINIMUM_LENGTH, ' '));
		printProgress();
	}

	@Override
	public List<HostSpec> listHostSpecs() {
		return hostManager.listHostSpecs();
	}

	@Override
	public void addHost(HostSpec hostSpec) {
		hostManager.addHost(hostSpec);
	}

	@Override
	public void deleteHost(String hostName) {
		hostManager.deleteHost(hostName);
	}

	@Override
	public void setDefaultHost(String hostName) {
		hostManager.setDefaultHost(hostName);
	}

	@Override
	public void enableProgressBar() {
		progressController.enableProgressBar();
	}

	@Override
	public void updateProgressBar(float percentWorked) {
		progressController.updateProgressBar(percentWorked);
	}

	@Override
	public void startExecution() {
		progressTimer = new Timer();
		progressTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				printProgress();
			}
		}, PROGRESS_DELAY, PROGRESS_DELAY);
	}

	private void printProgress() {
		if (progressTimer != null) {
			System.out.print(progressController.getText() + "\r");
		}
	}

	@Override
	public void finishExecution() {
		progressTimer.cancel();
		progressTimer = null;
		System.out.print(Strings.padEnd("", PRINTLN_MINIMUM_LENGTH, ' '));
	}

	@Override
	public void startRepl(Host host, Repl repl) {
		ConsoleReader reader = null;
		try {
			reader = new ConsoleReader();
			reader.setPrompt("glimpse-" + host.getUrl() + "> ");
			while (true) {
				String line = reader.readLine();
				if (line.equals("exit")) {
					break;
				}
				String result = repl.eval(line);
				System.out.println("==>" + result);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (reader != null) reader.shutdown();
		}
	}


}
