package commands;

import database.GetLinksByLinkName;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.CommandContainer;
import records.Link;

public class GetLink {

	public String getCommandDescription() {
		return "Allows a user to retrieve an inserted link under their discord tag.";
	}

	public OptionData[] getOptions() {
		return new OptionData[] {
				new OptionData(OptionType.STRING, "link_name", "Link name of link", false),
				new OptionData(OptionType.STRING, "link", "The link itself", false)
		};
	}

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
