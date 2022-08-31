package commands;

import database.LinkDAO;
import interfaces.CommandInterface;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.CommandContainer;
import records.Link;
import util.CaseConverter;

public class GetLink implements CommandInterface {

	public String getCommandDescription() {
		return "Allows a user to retrieve an inserted link under their discord tag.";
	}

	public OptionData[] getOptions() {
		return new OptionData[] {
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("linkName"), "Link name of link", false),
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("link"), "The link itself", false)
		};
	}

	public void getLink(CommandContainer commandContainer) {

		LinkDAO linkDAO = new LinkDAO();
		Link link;

		if (commandContainer.options() == null) {
			commandContainer.event().reply("No link name to search for!").queue();
		} else {
			link = linkDAO.getLinkByLinkName( "");
			if (link.link().equals("")) {
				commandContainer.event().reply("No link with name similar to " + commandContainer.options() + " !").queue();
			} else {
				commandContainer.event().reply(link.link()).queue();
			}
		}
	}
}
