package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.Commands;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.GetAllCommands;

public class Help {

    public Help() {

    }

    public void Help(DiscordUsers discordUsers, CommandContainer commandContainer) {

        GetAllCommands getAllCommands = new GetAllCommands();
        Commands[] commands = getAllCommands.GetAllCommands();
        String helpOutput = "";

        if (commandContainer.getContentOfCommand() == null) {
            helpOutput = "Use !help <command> to search that specific command!\n\n";

            for (Commands command : commands) {
                helpOutput += command.getCommandSyntax() + ": ";
                helpOutput += command.getCommandDescription() + "\n";
            }
            commandContainer.getEvent().getMessage().reply(helpOutput).queue();

        } else {
            for (Commands command : commands) {
                if (!command.getCommand().equals(commandContainer.getContentOfCommand()[0])) {
                    continue;
                }
                helpOutput += command.getCommandSyntax() + ": ";
                helpOutput += command.getCommandDescription() + "\t";
            }
            commandContainer.getEvent().getMessage().reply(helpOutput).queue();
        }

        if (helpOutput.equals("")) {
            String invalidCommand = "";
            for (int i = 0; i < commandContainer.getContentOfCommand().length - 1; i++) {
                invalidCommand += commandContainer.getContentOfCommand()[i];
                if (i != commandContainer.getContentOfCommand().length - 2) {
                    invalidCommand += " ";
                }
            }
            commandContainer.getEvent().getMessage().reply(invalidCommand + " is not a valid command!").queue();
        }
    }
}
