package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.classes.CommandContainer;
import me.lega.linkdiscordbot.classes.EmbedClass;
import me.lega.linkdiscordbot.classes.EmbedField;
import me.lega.linkdiscordbot.classes.Link;
import me.lega.linkdiscordbot.database.GetLinksByLinkName;
import me.lega.linkdiscordbot.database.InsertLink;
import me.lega.linkdiscordbot.embeds.SuccessEmbed;

import java.awt.Color;
import java.util.Objects;

public class AddLink {

    public AddLink() {

    }

    public void addLink(CommandContainer commandContainer) {

        EmbedClass newEmbed;
        EmbedField[] newEmbedField;
        SuccessEmbed successEmbed = new SuccessEmbed();
        Link link = new Link();
        Link linkClass;
        String[] splitContent = commandContainer.getContentOfCommand().split(" ");

        if (!link.getLINK_PATTERN().matcher(splitContent[splitContent.length - 1]).find()) {
            commandContainer.getEvent().getMessage().reply("No valid link found.").queue();
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
        InsertLink insertLink = new InsertLink();
        linkClass = getLink.getLink(linkName.toString(), splitContent[splitContent.length - 1]);

        if (linkClass == null) {

            insertLink.insertLink(commandContainer.getCurrentDiscordUser(), linkName.toString(), splitContent[splitContent.length - 1]);
            linkClass = getLink.getLink(linkName.toString(), splitContent[splitContent.length - 1]);
            newEmbedField = new EmbedField[2];
            newEmbedField[0] = new EmbedField("Link Name", linkClass.getLinkName(), false);
            newEmbedField[1] = new EmbedField("Link", linkClass.getLink(), false);
            String imageLink = null;

            // switch to using rich link preview later
            try {
                imageLink = Objects.requireNonNull(commandContainer.getEvent().getMessage().getEmbeds().get(0).getImage()).getUrl();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            newEmbed = new EmbedClass(Color.GREEN, newEmbedField, commandContainer.getEvent().getAuthor().getAsMention(), imageLink, linkClass.getCreatedAt().toLocalDateTime(), "Added successfully!");
            successEmbed.successEmbed(newEmbed, commandContainer.getEvent());
        } else {
            commandContainer.getEvent().getMessage().reply(link + " has already been added into the database!").queue();
        }
    }
}
