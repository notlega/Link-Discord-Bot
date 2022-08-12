package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.database.PrefixDAO;
import me.lega.linkdiscordbot.listeners.CommandContainer;

public class SetPrefix {

    public SetPrefix() {

    }

    public void setPrefix(CommandContainer commandContainer) {

        PrefixDAO prefixDAO = new PrefixDAO();

        if (commandContainer.getContentOfCommand() == null) {
            commandContainer.getEvent().getMessage().reply("You need to specify a prefix!").queue();
            return;
        }

        if (commandContainer.getContentOfCommand().length() > 1) {
            commandContainer.getEvent().getMessage().reply("Prefix can only be one character long!").queue();
            return;
        }

        if (commandContainer.getCurrentPrefix().getPrefix().equals(commandContainer.getContentOfCommand())) {
            commandContainer.getEvent().getMessage().reply("That prefix is already set!").queue();
            return;
        }

        int numRowAffected = prefixDAO.updatePrefix(commandContainer);

        if (numRowAffected > 0) {
            commandContainer.getEvent().getMessage().reply("Prefix has been changed to <" + commandContainer.getContentOfCommand() + "> !").queue();
        } else if (numRowAffected == 0) {
            commandContainer.getEvent().getMessage().reply("Something went wrong!").queue();
        }
    }
}
