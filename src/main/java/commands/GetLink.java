package commands;

import database.GetLinksByLinkName;
import records.CommandContainer;
import records.Link;

public class GetLink {

	private static final String COMMAND_DESCRIPTION = "Allows a user to retrieve an inserted link under their discord tag.";

	public void getLink(CommandContainer commandContainer) {

		GetLinksByLinkName getLinksByLinkName = new GetLinksByLinkName();
		Link link;

		if (commandContainer.contentOfCommand() == null) {
			commandContainer.event().getMessage().reply("No link name to search for!").queue();
		} else {
			link = getLinksByLinkName.getLinksByLinkName(commandContainer.contentOfCommand(), "");
			if (link.link().equals("")) {
				commandContainer.event().getMessage().reply("No link with name similar to " + commandContainer.contentOfCommand() + " !").queue();
			} else {
				commandContainer.event().getMessage().reply(link.link()).queue();
			}
		}
	}
}
