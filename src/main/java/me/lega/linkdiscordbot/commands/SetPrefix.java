package me.lega.linkdiscordbot.commands;

import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordServers;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.GetPrefix;
import me.lega.linkdiscordbot.database.InsertDiscordServer;
import me.lega.linkdiscordbot.database.InsertPrefix;
import me.lega.linkdiscordbot.classes.Prefixes;
import me.lega.linkdiscordbot.database.UpdatePrefix;

public class SetPrefix {

    public SetPrefix() {

    }

    public void SetPrefix(DiscordUsers discordUsers, CommandContainer commandContainer) {

        int numRowAffected;
        InsertDiscordServer insertDiscordServer = new InsertDiscordServer();
        Prefixes prefixes = null;
        DiscordServers discordServers = insertDiscordServer.InsertDiscordServer(commandContainer.getEvent());
        GetPrefix getPrefix = new GetPrefix();
        InsertPrefix insertPrefix = new InsertPrefix();
        UpdatePrefix updatePrefix = new UpdatePrefix();
        prefixes = getPrefix.GetPrefix(discordServers);

        if (prefixes == null) {
            numRowAffected = insertPrefix.InsertPrefix(discordServers, commandContainer.getContentOfCommand()[0]);
        } else {
            numRowAffected = updatePrefix.UpdatePrefix(discordServers, commandContainer);
        }

        if (numRowAffected > 0) {
            commandContainer.getEvent().getMessage().reply("Prefix has been changed to <" + commandContainer.getContentOfCommand()[0] + "> !").queue();
        }
    }
}
