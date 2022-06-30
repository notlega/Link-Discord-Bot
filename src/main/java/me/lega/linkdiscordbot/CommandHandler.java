package me.lega.linkdiscordbot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import me.lega.linkdiscordbot.classes.Commands;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.GetAllCommands;
import me.lega.linkdiscordbot.database.GetDiscordServer;
import me.lega.linkdiscordbot.database.GetPrefix;
import me.lega.linkdiscordbot.database.InsertPrefix;

public class CommandHandler {

    private static final Map<String, String> commands = new HashMap<>();
    private static final GetAllCommands getAllCommands = new GetAllCommands();
    
    public CommandHandler() {
        for (Commands allCommands : getAllCommands.GetAllCommands()) {
            commands.put(allCommands.getCommand(), allCommands.getCommand());
        }
    }

    public Map<String, String> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

    public CommandContainer parseCommand(MessageReceivedEvent event) {
        
        GetDiscordServer getDiscordServer = new GetDiscordServer();
        GetPrefix getPrefix = new GetPrefix();
        String prefix = getPrefix.GetPrefix(getDiscordServer.GetDiscordServer(event)).getPrefix();

        String noPrefix = event.getMessage().getContentRaw().replaceFirst("\\" + prefix, "");
        String[] commandSplitContent = noPrefix.split(" ");
        String command = "";
        String[] content = new String[commandSplitContent.length - 1];

        try {

            command = commandSplitContent[0];

            for (int i = 0; i < commandSplitContent.length - 1; i++) {
                content[i] = commandSplitContent[i + 1];
            }

        } catch (ArrayIndexOutOfBoundsException AIOOBE) {
            return new CommandContainer(command, content, event);
        }

        return new CommandContainer(command, content, event);

    }

    public void handleCommand(DiscordUsers discordUsers, CommandContainer commandContainer) {

        try {

            if (commandContainer.getCommand().equals("")) {
                return;
            }

            CommandHandler c = new CommandHandler();
            Object o = null;

            if (commands.containsKey(commandContainer.getCommand())) {

                String capitalisedCommand = commandContainer.getCommand().substring(0, 1).toUpperCase() + commandContainer.getCommand().substring(1);
                Class<?> newClass = Class.forName(c.getClass().getPackage().getName() + ".commands." + capitalisedCommand);
                Object newObject = newClass.newInstance();
                Method[] allNewMethods = newClass.getDeclaredMethods();

                for (Method newMethod : allNewMethods) {
                    String methodName = newMethod.getName();
                    if (!methodName.startsWith(capitalisedCommand)) {
                        continue;
                    }

                    System.out.println("Invoking Method: " + methodName);
                    try {
                        newMethod.setAccessible(true);
                        o = newMethod.invoke(newObject, discordUsers, commandContainer);
                    } catch (InvocationTargetException ITE) {
                        ITE.printStackTrace();
                    }
                }
            } else {
                commandContainer.getEvent().getMessage().reply("The command " + commandContainer.getCommand() + " does not exist.").queue();
            }

        } catch (ClassNotFoundException CNFE) {
            CNFE.printStackTrace();
        } catch (InstantiationException IE) {
            IE.printStackTrace();
        } catch (IllegalAccessException IAE) {
            IAE.printStackTrace();
        }
    }
}
