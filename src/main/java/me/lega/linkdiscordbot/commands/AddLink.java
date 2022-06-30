package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.GetLink;
import me.lega.linkdiscordbot.database.InsertLink;
import me.lega.linkdiscordbot.classes.Links;

public class AddLink {

    public AddLink() {

    }

    /**
     * Inserts an image link into the Database. If link has been added, an
     * integer of -1 will be returned.
     *
     * @param discordUsers
     * @param commandContainer
     */
    public void AddLink(DiscordUsers discordUsers, CommandContainer commandContainer) {

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

        int numRowsAffected = 0;
        GetLink getLink = new GetLink();
        InsertLink insertLink = new InsertLink();
        linksClass = getLink.GetLink(linkName, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1]);

        if (linksClass == null) {
            numRowsAffected = insertLink.InsertLink(discordUsers, commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1], linkName);
        }
        
        if (numRowsAffected > 0) {
            commandContainer.getEvent().getMessage().reply("Added into database!\n\nLink Name: " + linkName + "\nLink: " + commandContainer.getContentOfCommand()[commandContainer.getContentOfCommand().length - 1] + "\nAdder: " + commandContainer.getEvent().getAuthor().getAsMention()).queue();
        }
    }
}
