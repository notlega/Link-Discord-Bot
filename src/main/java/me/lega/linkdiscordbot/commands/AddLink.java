package me.lega.linkdiscordbot.commands;

import java.awt.Color;
import java.time.LocalDateTime;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.classes.EmbedClass;
import me.lega.linkdiscordbot.classes.EmbedField;
import me.lega.linkdiscordbot.database.GetLink;
import me.lega.linkdiscordbot.database.InsertLink;
import me.lega.linkdiscordbot.classes.Links;
import me.lega.linkdiscordbot.embeds.SuccessEmbed;

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

        GetLink getLink = new GetLink();
        InsertLink insertLink = new InsertLink();
        linksClass = getLink.GetLink(linkName, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]);

        if (linksClass == null) {
            insertLink.InsertLink(discordUsers, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1], linkName);
            linksClass = getLink.GetLink(linkName, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]);
            newEmbedField = new EmbedField[2];
            newEmbedField[0] = new EmbedField("Link Name", linksClass.getLinkName(), false);
            newEmbedField[1] = new EmbedField("Link", linksClass.getLink(), false);
            String imageLink = commandContainer.getEvent().getMessage().getEmbeds().get(0).getImage().getUrl();
            newEmbed = new EmbedClass(Color.GREEN, newEmbedField, "<@!" + discordUsers.getDiscordUserID() + ">", imageLink, linksClass.getCreatedAt().toLocalDateTime(), "Added successfully!");
            successEmbed.SuccessEmbed(newEmbed, commandContainer.getEvent());
        } else {
            commandContainer.getEvent().getMessage().reply(links + " has already been added into the database!").queue();
        }
    }
}
