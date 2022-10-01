package commands;

import database.DiscordUserDAO;
import database.LinkDAO;
import embeds.FailEmbed;
import embeds.SuccessEmbed;
import models.TwitterModel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.*;
import util.CaseConverter;
import util.Command;
import util.LinkFilter;
import util.LinkHandler;

import java.awt.*;
import java.util.Objects;

public class AddLink extends Command {

	@Override
	public String commandDescription() {
		return "Allows a user to insert a link and name the link in the database.";
	}

	@Override
	public OptionData[] commandOptions() {
		return new OptionData[]{
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("linkName"), "Link name of link being inserted", true),
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("link"), "Link being inserted", true)
		};
	}

	@Override
	public void executeCommand(CommandContainer commandContainer) throws Exception {
		if (!LinkFilter.checkLinkValid(commandContainer.options().get(1).getAsString())) {
			commandContainer.event().reply("Invalid link entered").queue();
			return;
		}

		DiscordUserDAO discordUserDAO = new DiscordUserDAO();
		DiscordUser discordUser = discordUserDAO.getDiscordUser(commandContainer.event());

		if (discordUser == null) {
			discordUserDAO.insertDiscordUser(commandContainer.event());
			discordUser = discordUserDAO.getDiscordUser(commandContainer.event());
		}

		LinkDAO linkDAO = new LinkDAO();
		Link link = linkDAO.getLinkByLink(commandContainer.options().get(1).getAsString());

		if (link == null) {
			linkDAO.insertLink(discordUser, commandContainer.options().get(0).getAsString(), commandContainer.options().get(1).getAsString());
			link = linkDAO.getLinkByLink(commandContainer.options().get(1).getAsString());
			EmbedField[] newEmbedField = new EmbedField[2];
			String imageLink;
			newEmbedField[0] = new EmbedField("Link Name", link.linkName(), false);
			newEmbedField[1] = new EmbedField("Link", link.link(), false);

			if (LinkFilter.checkLinkTwitter(link.link())) {
				imageLink = TwitterModel.getTweetImageLink(link.link());
			} else {
				imageLink = LinkHandler.getRichInfoFromLink(link.link());
			}

			SuccessEmbed.successEmbed(new EmbedClass(Color.GREEN, newEmbedField, Objects.requireNonNull(commandContainer.event().getUser()).getAsMention(), imageLink, link.createdAt().toLocalDateTime(), "Added successfully!"), commandContainer.event());
		} else {
			FailEmbed.failEmbed(commandContainer.options().get(1).getAsString() + " has already been added into the database!", commandContainer.event());
		}
	}
}
