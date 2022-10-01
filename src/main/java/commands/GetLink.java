package commands;

import database.LinkDAO;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.CommandContainer;
import records.Link;
import util.CaseConverter;
import util.Command;

public class GetLink extends Command {

	@Override
	public String commandDescription() {
		return "Allows a user to retrieve an inserted link under their discord tag.";
	}

	@Override
	public OptionData[] commandOptions() {
		return new OptionData[] {
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("linkName"), "Link name of link", false),
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("link"), "The link itself", false)
		};
	}

	@Override
	public void executeCommand(CommandContainer commandContainer) throws Exception{

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
