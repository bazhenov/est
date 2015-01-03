package me.bazhenov.est;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.err;
import static java.lang.System.exit;

public class App {

	private final Map<String, Runnable> commands = new HashMap<String, Runnable>();

	@Parameter(names = {"-h", "--help"}, description = "Show this help")
	public boolean help = false;

	public App() {
		commands.put(TopCommand.COMMAND_NAME, new TopCommand());
		commands.put(UniqCommand.COMMAND_NAME, new UniqCommand());
	}

	public static void main(String[] args) throws IOException {
		new App().run(args);
	}

	static void closeQuietly(BufferedReader r) {
		try {
			r.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void run(String[] args) {
		JCommander commander = new JCommander(new App());
		for (Map.Entry<String, Runnable> i : commands.entrySet())
			commander.addCommand(i.getKey(), i.getValue());

		try {
			commander.parse(args);
		} catch (ParameterException e) {
			commander.usage();
			exit(255);
		}
		String command = commander.getParsedCommand();

		if (help || command == null || command.isEmpty()) {
			commander.usage();
			exit(1);
		}

		try {
			commands.get(command).run();
		} catch (ParameterException e) {
			err.println(e.getMessage());
			commander.usage();
		}
	}
}
