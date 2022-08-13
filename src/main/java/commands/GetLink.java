package commands;

import records.CommandContainer;
import database.GetLinksByLinkName;

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
