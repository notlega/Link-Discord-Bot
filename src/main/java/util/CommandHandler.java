package util;

import records.CommandContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

	private static final Map<String, Command> commands = new HashMap<>();

	public static Map<String, Command> getCommands() {
		return Collections.unmodifiableMap(commands);
	}

	public static void initialiseCommands() {

		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(CommandHandler.class.getPackageName().replaceAll("[.]", "/").replace(CommandHandler.class.getPackageName(), "commands"));

		if (stream == null) {
			return;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		reader.lines()
				.filter(line -> line.endsWith(".class"))
				.forEach(line -> {
					try {
						commands.put(
								line.replace(".class", ""),
								(Command) Class.forName("commands" + "." + line.replace(".class", "")).getDeclaredConstructor().newInstance()
						);
					} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
							 IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				});
	}

	public static void handleCommand(CommandContainer commandContainer) throws Exception {
		System.out.println(commandContainer.command());
		Command command = getCommands().get(commandContainer.command());
		command.executeCommand(commandContainer);
	}
}
