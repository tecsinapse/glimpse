package br.com.tecsinapse.glimpse.cli;

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
	public String getHelp() {
		return "No help is provided for this command right now";
	}

	@Override
	public String getShortDescription() {
		return "No description is provided";
	}
}
