package listeners;

import database.DiscordServerDAO;
import database.GetDiscordUser;
import database.InsertDiscordUser;
import database.PrefixDAO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import records.DiscordServer;
import records.DiscordUser;
import records.Prefix;

public class MessageEventListener extends ListenerAdapter {

    public MessageEventListener() {

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        CommandHandler commandHandler = new CommandHandler();

        DiscordServerDAO discordServerDAO = new DiscordServerDAO();

        InsertDiscordUser insertDiscordUser = new InsertDiscordUser();
        GetDiscordUser getDiscordUser = new GetDiscordUser();

        PrefixDAO prefixDAO = new PrefixDAO();

        DiscordUser currentDiscordUser = getDiscordUser.getDiscordUser(event);
        DiscordServer currentDiscordServer = discordServerDAO.getDiscordServerByDiscordServerId(event);
        Prefix currentPrefix = prefixDAO.getPrefixByDiscordServerId(currentDiscordServer);

        // checks if message comes from server
        if (!event.isFromGuild()) {
            return;
        }
        // checks if it's a member of the server who sent the message
        if (event.getMember() == null) {
            return;
        }
        // checks if message author is bot or Mee6
        if (event.getAuthor().isBot() || event.getAuthor().getIdLong() == 159985870458322944L) {
            return;
        }
        // checks if discord user exists in database
        // if not, insert discord user into database
        if (currentDiscordUser == null) {
            insertDiscordUser.insertDiscordUser(event);
            currentDiscordUser = getDiscordUser.getDiscordUser(event);
        }
        // checks if prefix is correct
        if (!event.getMessage().getContentRaw().startsWith(currentPrefix.prefix())) {
            return;
        }

        commandHandler.handleCommand(commandHandler.parseCommand(currentDiscordServer, currentDiscordUser, currentPrefix, event));
    }
}
