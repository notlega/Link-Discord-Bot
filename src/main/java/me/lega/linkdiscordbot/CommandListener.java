package me.lega.linkdiscordbot;

import me.lega.linkdiscordbot.classes.DiscordServer;
import me.lega.linkdiscordbot.classes.DiscordUser;
import me.lega.linkdiscordbot.classes.Prefixes;
import me.lega.linkdiscordbot.database.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    public CommandListener() {

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        CommandHandler commandHandler = new CommandHandler();

        InsertDiscordServer insertDiscordServer = new InsertDiscordServer();
        GetDiscordServer getDiscordServer = new GetDiscordServer();

        InsertDiscordUser insertDiscordUser = new InsertDiscordUser();
        GetDiscordUser getDiscordUser = new GetDiscordUser();

        InsertPrefix insertPrefix = new InsertPrefix();
        GetPrefix getPrefix = new GetPrefix();

        DiscordUser currentDiscordUser = getDiscordUser.getDiscordUser(event);
        DiscordServer currentDiscordServer = getDiscordServer.getDiscordServer(event);
        Prefixes currentPrefix = getPrefix.getPrefix(currentDiscordServer);

        // checks if message comes from server
        if (!event.isFromGuild()) {
            return;
        }
        // checks if it's a member of the server who sent the message
        // can import events.message.guild.GuildMessageReceivedEvent to remove this if statement
        if (event.getMember() == null) {
            return;
        }
        // checks if message author is bot or Mee6
        if (event.getAuthor().isBot() || event.getAuthor().getIdLong() == 159985870458322944L) {
            return;
        }
        // checks if discord server exists in database
        // if not, insert discord server into database
        if (currentDiscordServer == null) {
            insertDiscordServer.insertDiscordServer(event);
            currentDiscordServer = getDiscordServer.getDiscordServer(event);
        }
        // checks if discord user exists in database
        // if not, insert discord user into database
        if (currentDiscordUser == null) {
            insertDiscordUser.insertDiscordUser(event);
            currentDiscordUser = getDiscordUser.getDiscordUser(event);
        }
        // checks if prefix exists in database
        // if not, insert default prefix of ! into database
        if (getPrefix.getPrefix(currentDiscordServer) == null) {
            insertPrefix.insertPrefix(currentDiscordServer, Prefixes.DEFAULT_PREFIX);
            currentPrefix = getPrefix.getPrefix(currentDiscordServer);
        }
        // checks if prefix is correct
        if (!event.getMessage().getContentRaw().startsWith(currentPrefix.getPrefix())) {
            return;
        }

        commandHandler.handleCommand(commandHandler.parseCommand(currentDiscordServer, currentDiscordUser, currentPrefix, event));
    }
}
