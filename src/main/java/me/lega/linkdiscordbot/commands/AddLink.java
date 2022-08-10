package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.classes.EmbedClass;
import me.lega.linkdiscordbot.classes.EmbedField;
import me.lega.linkdiscordbot.classes.Links;
import me.lega.linkdiscordbot.database.GetDBLink;
import me.lega.linkdiscordbot.database.InsertLink;
import me.lega.linkdiscordbot.embeds.SuccessEmbed;

import java.awt.*;

public class AddLink {

    public AddLink() {

    }

    public void AddLink(DiscordUsers discordUsers, CommandContainer commandContainer) {

        EmbedClass newEmbed;
        EmbedField[] newEmbedField;
        SuccessEmbed successEmbed = new SuccessEmbed();
        Links links = new Links();
        Links linksClass;

        if (!links.getLINK_PATTERN().matcher(commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]).find()) {
            commandContainer.getEvent().getMessage().reply("No valid link found.").queue();
            return;
        }

        String linkName = "";
        for (int i = 0; i < commandContainer.getContentOfCommand().length - 1; i++) {
            linkName += commandContainer.getContentOfCommand()[i];
            if (i != commandContainer.getContentOfCommand().length - 2) {
                linkName += " ";
            }
        }

        GetDBLink getLink = new GetDBLink();
        InsertLink insertLink = new InsertLink();
        linksClass = getLink.GetLink(linkName, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]);

        if (linksClass == null) {
            insertLink.InsertLink(discordUsers, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1], linkName);
            linksClass = getLink.GetLink(linkName, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]);
            newEmbedField = new EmbedField[2];
            newEmbedField[0] = new EmbedField("Link Name", linksClass.getLinkName(), false);
            newEmbedField[1] = new EmbedField("Link", linksClass.getLink(), false);
            String imageLink = null;
            try {
                imageLink = commandContainer.getEvent().getMessage().getEmbeds().get(0).getImage().getUrl();
                System.out.println(imageLink);
            } catch (Exception E) {
                E.printStackTrace();
            }
            newEmbed = new EmbedClass(Color.GREEN, newEmbedField, commandContainer.getEvent().getAuthor().getAsMention(), imageLink, linksClass.getCreatedAt().toLocalDateTime(), "Added successfully!");
            successEmbed.SuccessEmbed(newEmbed, commandContainer.getEvent());
        } else {
            commandContainer.getEvent().getMessage().reply(links + " has already been added into the database!").queue();
        }
    }
}
