package commands;

import database.GetLinksByLinkName;
import database.LinkDAO;
import embeds.SuccessEmbed;
import records.CommandContainer;
import records.EmbedClass;
import records.EmbedField;
import records.Link;

import java.awt.*;
import java.util.Objects;

public class AddLink {

	private static final String COMMAND_DESCRIPTION = "Allows a user to insert a link and name the link in the database.";

	public void addLink(CommandContainer commandContainer) {

		EmbedClass newEmbed;
		EmbedField[] newEmbedField;
		SuccessEmbed successEmbed = new SuccessEmbed();
		String[] splitContent = commandContainer.contentOfCommand().split(" ");

		if (!Link.LINK_PATTERN.matcher(splitContent[splitContent.length - 1]).find()) {
			commandContainer.event().getMessage().reply("Invalid link entered").queue();
			return;
		}

		StringBuilder linkName = new StringBuilder();
		for (int i = 0; i < splitContent.length - 1; i++) {
			linkName.append(splitContent[i]);
			if (i != splitContent.length - 2) {
				linkName.append(" ");
			}
		}

		GetLinksByLinkName getLink = new GetLinksByLinkName();
		LinkDAO linkDAO = new LinkDAO();
		Link link = getLink.getLinksByLinkName(linkName.toString(), splitContent[splitContent.length - 1]);

		if (link == null) {

			linkDAO.insertLink(commandContainer.currentDiscordUser(), linkName.toString(), splitContent[splitContent.length - 1]);
			link = getLink.getLinksByLinkName(linkName.toString(), splitContent[splitContent.length - 1]);
			newEmbedField = new EmbedField[2];
			newEmbedField[0] = new EmbedField("Link Name", link.linkName(), false);
			newEmbedField[1] = new EmbedField("Link", link.link(), false);
			String imageLink = null;

			// switch to using rich link preview later
			try {
				imageLink = Objects.requireNonNull(commandContainer.event().getMessage().getEmbeds().get(0).getImage()).getUrl();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			newEmbed = new EmbedClass(Color.GREEN, newEmbedField, commandContainer.event().getAuthor().getAsMention(), imageLink, link.createdAt().toLocalDateTime(), "Added successfully!");
			successEmbed.successEmbed(newEmbed, commandContainer.event());
		} else {
			commandContainer.event().getMessage().reply(link + " has already been added into the database!").queue();
		}
	}
}
