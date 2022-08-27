package util;

import records.CommandContainer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
		try {
			Class<?> commandClass = commands.get(commandContainer.command());
			Object newObject = commandClass.getDeclaredConstructors()[0].newInstance();
			Method newMethod = commandClass.getMethod(Capitalisation.decapitalise(commandContainer.command()), commandContainer.getClass());

			System.out.println("Invoking Method: " + newMethod.getName());

			newMethod.setAccessible(true);
			newMethod.invoke(newObject, commandContainer);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				 NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
