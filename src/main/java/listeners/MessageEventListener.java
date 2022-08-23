package listeners;

import database.DiscordServerDAO;
import database.DiscordUserDAO;
import database.PrefixDAO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import records.DiscordServer;
import records.DiscordUser;
import records.Prefix;
import util.CommandHandler;

public class MessageEventListener extends ListenerAdapter {

	public MessageEventListener() {

	}

	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		CommandHandler commandHandler = new CommandHandler();
		DiscordServerDAO discordServerDAO = new DiscordServerDAO();
		DiscordUserDAO discordUserDAO = new DiscordUserDAO();
		PrefixDAO prefixDAO = new PrefixDAO();

		DiscordUser currentDiscordUser = discordUserDAO.getDiscordUser(event);
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
			discordUserDAO.insertDiscordUser(event);
			currentDiscordUser = discordUserDAO.getDiscordUser(event);
		}
		// checks if prefix is correct
		if (!event.getMessage().getContentRaw().startsWith(currentPrefix.prefix())) {
			return;
		}

		commandHandler.handleCommand(commandHandler.parseCommand(currentDiscordServer, currentDiscordUser, currentPrefix, event));
	}
}
