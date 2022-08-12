package listeners;

import classes.*;
import database.CommandDAO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static final Map<String, String> commands = new HashMap<>();
    private static final CommandDAO commandDAO = new CommandDAO();

    public CommandHandler() {
        for (Command allCommand : commandDAO.getAllCommands()) {
            commands.put(allCommand.getCommand(), allCommand.getCommand());
        }
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

            if (commandContainer.getCommand().equals("")) {
                return;
            }

            if (commands.containsKey(commandContainer.getCommand())) {

                try {

                    Class<?> newClass = Class.forName(CommandHandler.class.getPackage().getName() + ".commands." + commandContainer.getCommand().substring(0, 1).toUpperCase() + commandContainer.getCommand().substring(1));
                    Object newObject = newClass.getDeclaredConstructors()[0].newInstance(Object[].class);
                    Method[] newMethodsArray = newClass.getDeclaredMethods();

                    for (Method newMethod : newMethodsArray) {
                        String methodName = newMethod.getName();
                        if (!methodName.startsWith(commandContainer.getCommand())) {
                            continue;
                        }

                        System.out.println("Invoking Method: " + methodName);
                        newMethod.setAccessible(true);
                        newMethod.invoke(newObject, commandContainer);
                    }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                commandContainer.getEvent().getMessage().reply("The command " + commandContainer.getCommand() + " does not exist.").queue();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
