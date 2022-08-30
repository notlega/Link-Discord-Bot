package util;

import records.CommandContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

	private static final Map<String, Class<?>> commands = new HashMap<>();

	public static Map<String, Class<?>> getCommands() {
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
								Class.forName("commands" + "." + line.replace(".class", "")).getSimpleName(),
								Class.forName("commands" + "." + line.replace(".class", ""))
						);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				});
	}

	public static void handleCommand(CommandContainer commandContainer) {
		MethodInvocator methodInvocator = new MethodInvocator(commands.get(commandContainer.command()));
		methodInvocator.invokeCommands(Capitalisation.decapitalise(commandContainer.command()), new Object[]{commandContainer});
	}
}
