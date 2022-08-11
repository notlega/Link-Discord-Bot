package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.classes.Command;
import me.lega.linkdiscordbot.database.CommandDAO;
import me.lega.linkdiscordbot.listeners.CommandContainer;

public class Help {

    public Help() {

    }

    public void help(CommandContainer commandContainer) {

        CommandDAO commandDAO = new CommandDAO();
        Command[] commands = commandDAO.getAllCommands();
        StringBuilder output = new StringBuilder();

        if (output.toString().equals("")) {
            commandContainer.getEvent().getMessage().reply(commandContainer.getContentOfCommand() + " is not a valid command!").queue();
        } else if (commandContainer.getContentOfCommand() == null) {
            output.append("Use !help <command> to search for that specific command!\n\n");
            for (Command command : commands) {
                output.append(command.getCommandSyntax()).append(": ").append(command.getCommandDescription()).append("\n");
            }

            commandContainer.getEvent().getMessage().reply(output.toString()).queue();
        } else {
            for (Command command : commands) {
                if (!command.getCommand().equals(commandContainer.getContentOfCommand())) {
                    continue;
                }

                output.append(command.getCommandSyntax()).append(": ").append(command.getCommandDescription()).append("\t");
            }
            commandContainer.getEvent().getMessage().reply(output.toString()).queue();
        }
    }
}
