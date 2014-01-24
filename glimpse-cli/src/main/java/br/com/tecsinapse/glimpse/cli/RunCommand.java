package br.com.tecsinapse.glimpse.cli;

import com.google.common.collect.Maps;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Map;

import static java.lang.String.format;

public class RunCommand extends AbstractCommand {
	public RunCommand() {
		super("run");
	}

	@Override
	protected String getCommandLineSyntax() {
		return "glimpse run $SCRIPT_FILE";
	}

	@Override
	protected String getHelpDescription() {
		return "Runs a given script. If no host name is provided the script is run on the default host. Options are provided to run the script in another host.";
	}

	@Override
	public String getShortDescription() {
		return "Runs a given script";
	}

	@Override
	public Options getOptions() {
		Options options = new Options();
		DefaultHostManager.addHostOptions(options);

		Option option = new Option("params", true, "Parameters to be sent to the script separated by '|', each param must use the format $param=$value");
		option.setValueSeparator('|');
		options.addOption(option);

		return options;
	}

	@Override
	public void execute(CommandLine commandLine, Console console) {
		Host host = console.getHost(commandLine);
		if (host != null) {
			if (commandLine.getArgs().length == 0) {
				console.println("Error: a script file must be specified\n\tUsage: glimpse run <script.groovy>");
				return;
			}
			Map<String, String> params = Maps.newLinkedHashMap();
			if (!parseParams(commandLine, params, console)) {
				return;
			}
			console.println(format("Executing script at: %s", host.getUrl()));
			console.println("--------------------------------------------------");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				console.println(format("%s=%s", entry.getKey(), entry.getValue()));
			}
			console.println("");
			console.startExecution();
			host.runScript(commandLine.getArgs()[0], params, console);
			console.finishExecution();
			console.println("");
			console.println("--------------------------------------------------");
			console.println("Finished executing script");
		}
	}

	private boolean parseParams(CommandLine commandLine, Map<String, String> params, Console console) {
		if (commandLine.hasOption("params")) {
			String paramsStr = commandLine.getOptionValue("params");
			String[] paramValuesStr = paramsStr.split("\\|");
			for (String paramValueStr: paramValuesStr) {
				String[] paramValue = paramValueStr.split("=");
				if (paramValue.length != 2) {
					console.println("Error: malformed parameters");
					return false;
				}
				String param = paramValue[0];
				String value = paramValue[1];
				params.put(param, value);
			}
		}
		return true;
	}
}
