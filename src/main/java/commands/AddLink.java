package commands;

import database.DiscordUserDAO;
import database.LinkDAO;
import embeds.SuccessEmbed;
import interfaces.CommandInterface;
import models.TwitterModel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.*;
import util.CaseConverter;
import util.LinkFilter;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class AddLink implements CommandInterface {

	public String getCommandDescription() {
		return "Allows a user to insert a link and name the link in the database.";
	}

	public OptionData[] getOptions() {
		return new OptionData[]{
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("linkName"), "Link name of link being inserted", true),
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("link"), "Link being inserted", true)
		};
	}

	public void addLink(CommandContainer commandContainer) throws URISyntaxException, IOException {
		DiscordUserDAO discordUserDAO = new DiscordUserDAO();
		DiscordUser discordUser = discordUserDAO.getDiscordUser(commandContainer.event());
		EmbedClass newEmbed;
		EmbedField[] newEmbedField;
		SuccessEmbed successEmbed = new SuccessEmbed();

		if (!LinkFilter.checkLinkValid(commandContainer.options().get(1).toString())) {
			commandContainer.event().reply("Invalid link entered").queue();
			return;
		}

		StringBuilder linkName = new StringBuilder(commandContainer.options().get(0).toString());
		LinkDAO linkDAO = new LinkDAO();
		Link link = linkDAO.getLinkByLink(commandContainer.options().get(1).toString());

		if (link == null) {
			linkDAO.insertLink(discordUser, linkName.toString(), commandContainer.options().get(1).toString());
			link = linkDAO.getLinkByLink(commandContainer.options().get(1).toString());
			newEmbedField = new EmbedField[2];
			newEmbedField[0] = new EmbedField("Link Name", link.linkName(), false);
			newEmbedField[1] = new EmbedField("Link", link.link(), false);
			String imageLink;

			if (LinkFilter.checkLinkTwitter(link.link())) {
				imageLink = (String) TwitterModel.getTweets(new String[] {"asd"});
			}

			newEmbed = new EmbedClass(Color.GREEN, newEmbedField, Objects.requireNonNull(commandContainer.event().getUser()).getAsMention(), null, link.createdAt().toLocalDateTime(), "Added successfully!");
			successEmbed.successEmbed(newEmbed, commandContainer.event());
		} else {
			commandContainer.event().reply(link + " has already been added into the database!").queue();
		}
	}
}
