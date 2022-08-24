package commands;

import database.PrefixDAO;
import records.CommandContainer;

public class SetPrefix {

	private static final String COMMAND_DESCRIPTION = "Sets a new prefix for the sever the command was called in.";

	public void setPrefix(CommandContainer commandContainer) {

		PrefixDAO prefixDAO = new PrefixDAO();

		if (commandContainer.contentOfCommand() == null) {
			commandContainer.event().getMessage().reply("You need to specify a prefix!").queue();
			return;
		}

		if (commandContainer.contentOfCommand().length() > 1) {
			commandContainer.event().getMessage().reply("Prefix can only be one character long!").queue();
			return;
		}

		if (commandContainer.currentPrefix().prefix().equals(commandContainer.contentOfCommand())) {
			commandContainer.event().getMessage().reply("That prefix is already set!").queue();
			return;
		}

		int numRowAffected = prefixDAO.updatePrefix(commandContainer);

		if (numRowAffected > 0) {
			commandContainer.event().getMessage().reply("Prefix has been changed to <" + commandContainer.contentOfCommand() + "> !").queue();
		} else if (numRowAffected == 0) {
			commandContainer.event().getMessage().reply("Something went wrong!").queue();
		}
	}
}
