package listeners;

import records.CommandContainer;
import records.DiscordServer;
import records.DiscordUser;
import records.Prefix;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static final Map<String, Class<?>> commands = new HashMap<>();

    public CommandHandler() {

    }

    public CommandContainer parseCommand(DiscordServer currentDiscordServer, DiscordUser currentDiscordUser, Prefix currentPrefix, MessageReceivedEvent event) {

        String noPrefix = event.getMessage().getContentRaw().replaceFirst(currentPrefix.getPrefix(), "");
        String command = noPrefix.substring(0, noPrefix.indexOf(" "));
        String content = null;

        if (noPrefix.contains(" ")) {
            content = noPrefix.substring(noPrefix.indexOf(" ") + 1);
        }

        return new CommandContainer(currentDiscordServer, currentDiscordUser, currentPrefix, command, content, event);
    }

    public void handleCommand(CommandContainer commandContainer) {

        try {

            Class<?> commandClass = commands.get(commandContainer.command());

            if (commandClass == null) {
                commandContainer.event().getMessage().reply("The command " + commandContainer.command() + " does not exist.").queue();
                return;
            }

            Object newObject = commandClass.getDeclaredConstructors()[0].newInstance(Object[].class);
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
