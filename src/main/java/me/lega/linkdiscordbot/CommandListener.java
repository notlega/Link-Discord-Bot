package me.lega.linkdiscordbot;

import me.lega.linkdiscordbot.classes.DiscordServers;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    public CommandListener() {

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        CommandHandler commandHandler = new CommandHandler();
        InsertDiscordUser insertDiscordUser = new InsertDiscordUser();
        InsertDiscordServer insertDiscordServer = new InsertDiscordServer();

        // checks if message comes from server
        if (!event.isFromGuild()) {
            return;
        }
        // checks if its a member of the server who sent the message
        // can import events.message.guild.GuildMessageReceivedEvent to remove this if statement
        if (event.getMember() == null) {
            return;
        }
        // checks if message author is bot or Mee6
        if (event.getAuthor().isBot() || event.getAuthor().getIdLong() == 159985870458322944L) {
            return;
        }

        DiscordUsers discordUsers = insertDiscordUser.insertDiscordUser(event);
        GetDiscordServer getDiscordServer = new GetDiscordServer();
        InsertPrefix insertPrefix = new InsertPrefix();
        GetPrefix getPrefix = new GetPrefix();
        if (getDiscordServer.GetDiscordServer(event) == null) {
            insertDiscordServer.InsertDiscordServer(event);
        }
        DiscordServers currentDiscordServer = getDiscordServer.GetDiscordServer(event);
        if (getPrefix.GetPrefix(currentDiscordServer) == null) {
            insertPrefix.InsertPrefix(currentDiscordServer, "!");
        }
        String prefix = getPrefix.GetPrefix(currentDiscordServer).getPrefix();

        // checks if prefix is correct
        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            commandHandler.handleCommand(discordUsers, commandHandler.parseCommand(event));
        }
    }
}
