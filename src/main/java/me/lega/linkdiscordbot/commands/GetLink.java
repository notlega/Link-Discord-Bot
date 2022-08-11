package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.listeners.CommandContainer;
import me.lega.linkdiscordbot.classes.Link;
import me.lega.linkdiscordbot.database.GetLinksByLinkName;

public class GetLink {

    public GetLink() {

    }

    public void getLink(CommandContainer commandContainer) {

        GetLinksByLinkName getLinksByLinkName = new GetLinksByLinkName();
        Link link;

        if (commandContainer.getContentOfCommand() == null) {
            commandContainer.getEvent().getMessage().reply("No link name to search for!").queue();
        } else {
            link = getLinksByLinkName.getLinksByLinkName(commandContainer.getContentOfCommand(), "");
            if (link.getLink().equals("")) {
                commandContainer.getEvent().getMessage().reply("No link with name similar to " + commandContainer.getContentOfCommand() + " !").queue();
            } else {
                commandContainer.getEvent().getMessage().reply(link.getLink()).queue();
            }
        }
    }
}
