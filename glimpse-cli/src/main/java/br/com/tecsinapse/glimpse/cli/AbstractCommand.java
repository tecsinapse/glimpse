package br.com.tecsinapse.glimpse.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractCommand implements Command {

	private final String name;

	public AbstractCommand(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Options getOptions() {
		return new Options();
	}

	@Override
	public String getHelp() {
		StringWriter writer = new StringWriter();
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(new PrintWriter(writer), 100, getCommandLineSyntax(), getHeader(), getOptions(), 4, 4, null);
		return writer.toString();
	}

	private String getHeader() {
		StringBuilder builder = new StringBuilder()
			.append("\n")
			.append(getHelpDescription());
		if (!getOptions().getOptions().isEmpty()) {
			builder.append("\n");
			builder.append("Options:\n");
		}
		return builder.toString();
	}

	protected String getCommandLineSyntax() {
		return "No syntax is provided";
	}

	protected String getHelpDescription() {
		return "No description is provided";
	}

	@Override
	public String getShortDescription() {
		return "No description is provided";
	}
}
