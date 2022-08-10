package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.classes.Links;
import me.lega.linkdiscordbot.database.GetDBLink;

public class GetLink {

    public GetLink() {

    }

    public void GetLink(DiscordUsers discordUsers, CommandContainer commandContainer) {

        GetDBLink getDBLink = new GetDBLink();
        Links links = null;
        String linkName = "";

        if (commandContainer.getContentOfCommand() == null) {
            commandContainer.getEvent().getMessage().reply("No link name to search for!").queue();
        } else {
            for (String str : commandContainer.getContentOfCommand()) {
                linkName += str + " ";
            }
            linkName = linkName.substring(0, linkName.length() - 1);
            links = getDBLink.GetLink(linkName, "");
            if (links.getLink().equals("")) {
                commandContainer.getEvent().getMessage().reply("No link with name similar to " + linkName + " !").queue();
            } else {
                commandContainer.getEvent().getMessage().reply(links.getLink()).queue();
            }
        }
    }
}
