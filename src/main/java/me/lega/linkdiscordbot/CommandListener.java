package me.lega.linkdiscordbot;

import static com.sun.xml.internal.ws.util.xml.XmlUtil.getPrefix;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import me.lega.linkdiscordbot.database.InsertDiscordUser;
import me.lega.linkdiscordbot.classes.DiscordUsers;
import me.lega.linkdiscordbot.database.GetDiscordServer;
import me.lega.linkdiscordbot.database.GetPrefix;
import me.lega.linkdiscordbot.database.InsertDiscordServer;
import me.lega.linkdiscordbot.database.InsertPrefix;

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
        insertDiscordServer.InsertDiscordServer(event);
        GetDiscordServer getDiscordServer = new GetDiscordServer();
        InsertPrefix insertPrefix = new InsertPrefix();
        GetPrefix getPrefix = new GetPrefix();
        insertPrefix.InsertPrefix(getDiscordServer.GetDiscordServer(event), "!");
        String prefix = getPrefix.GetPrefix(getDiscordServer.GetDiscordServer(event)).getPrefix();
        
        // checks if prefix is correct
        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            commandHandler.handleCommand(discordUsers, commandHandler.parseCommand(event));
        }
    }
}
