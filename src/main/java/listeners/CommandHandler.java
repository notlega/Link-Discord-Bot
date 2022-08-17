package listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import records.CommandContainer;
import records.DiscordServer;
import records.DiscordUser;
import records.Prefix;

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

    public CommandHandler() {

    }

    public static Map<String, Class<?>> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public static void initialiseCommands() {

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(CommandHandler.class.getPackageName().replaceAll("[.]", "/").replace("listeners", "commands"));

        if (stream == null) {
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.lines()
                .filter(line -> line.endsWith(".class"))
                .forEach(line -> {
                    try {
                        commands.put(
                                Class.forName(CommandHandler.class.getPackageName().replace(CommandHandler.class.getPackage().getName(), "commands") + "." + line.replace(".class", "")).getSimpleName(),
                                Class.forName(CommandHandler.class.getPackageName().replace(CommandHandler.class.getPackage().getName(), "commands") + "." + line.replace(".class", ""))
                        );
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

    public CommandContainer parseCommand(DiscordServer currentDiscordServer, DiscordUser currentDiscordUser, Prefix currentPrefix, MessageReceivedEvent event) {

        String noPrefix = event.getMessage().getContentRaw().replaceFirst(currentPrefix.prefix(), "");
        String command = noPrefix;
        String content = null;

        if (noPrefix.contains(" ")) {
            command = noPrefix.substring(0, noPrefix.indexOf(" "));
            content = noPrefix.substring(noPrefix.indexOf(" ") + 1);
        }

        return new CommandContainer(currentDiscordServer, currentDiscordUser, currentPrefix, command, content, event);
    }

    public void handleCommand(CommandContainer commandContainer) {

        try {

            Class<?> commandClass = commands.get(commandContainer.command().substring(0, 1).toUpperCase() + commandContainer.command().substring(1));

            if (commandClass == null) {
                commandContainer.event().getMessage().reply("The command " + commandContainer.command() + " does not exist.").queue();
                return;
            }

            Object newObject = commandClass.getDeclaredConstructors()[0].newInstance();
            Method[] newMethodsArray = commandClass.getDeclaredMethods();

            for (Method newMethod : newMethodsArray) {
                String methodName = newMethod.getName();
                if (!methodName.startsWith(commandContainer.command())) {
                    continue;
                }

                System.out.println("Invoking Method: " + methodName);
                newMethod.setAccessible(true);
                newMethod.invoke(newObject, commandContainer);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
