package util;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import records.CommandContainer;
import records.DiscordUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

	private static final Map<String, Class<?>> commands = new HashMap<>();

	public CommandHandler() {

	}

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
								Class.forName(CommandHandler.class.getPackageName().replace(CommandHandler.class.getPackageName(), "commands") + "." + line.replace(".class", "")).getSimpleName(),
								Class.forName(CommandHandler.class.getPackageName().replace(CommandHandler.class.getPackageName(), "commands") + "." + line.replace(".class", ""))
						);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				});
	}

	public CommandContainer parseCommand(DiscordUser currentDiscordUser, MessageReceivedEvent event) {

		String noPrefix = event.getMessage().getContentRaw();
		String command = noPrefix;
		String content = null;

		if (noPrefix.contains(" ")) {
			command = noPrefix.substring(0, noPrefix.indexOf(" "));
			content = noPrefix.substring(noPrefix.indexOf(" ") + 1);
		}

		return new CommandContainer(currentDiscordUser, command, content, event);
	}

	public void handleCommand(CommandContainer commandContainer) {

		try {

			Class<?> commandClass = commands.get(Capitalisation.capitalise(commandContainer.command()));

			if (commandClass == null) {
				commandContainer.event().getMessage().reply("The command " + commandContainer.command() + " does not exist.").queue();
				return;
			}

			Object newObject = commandClass.getDeclaredConstructors()[0].newInstance();
			Method[] newMethodsArray = commandClass.getDeclaredMethods();

			Method newMethod = Arrays.stream(newMethodsArray)
					.filter(method -> method.getName().startsWith(commandContainer.command()))
					.findFirst()
					.orElse(null);

			if (newMethod == null) {
				return;
			}

			System.out.println("Invoking Method: " + newMethod.getName());
			newMethod.setAccessible(true);
			newMethod.invoke(newObject, commandContainer);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
