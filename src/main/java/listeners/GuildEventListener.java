package listeners;

import database.DiscordServerDAO;
import database.PrefixDAO;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import records.Prefix;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class GuildEventListener extends ListenerAdapter {

	public GuildEventListener() {

	}

	@Override
	public void onGuildReady(@NotNull GuildReadyEvent event) {
		ArrayList<CommandData> commandDataArrayList = new ArrayList<>();
		commandDataArrayList.add(Commands.slash("slash", "description"));
		event.getGuild().updateCommands().addCommands(commandDataArrayList).queue();
	}

	@Override
	public void onGuildJoin(@NotNull GuildJoinEvent event) {
		DiscordServerDAO discordServerDAO = new DiscordServerDAO();
		PrefixDAO prefixDAO = new PrefixDAO();
		discordServerDAO.insertDiscordServer(event);
		prefixDAO.insertPrefix(event, Prefix.DEFAULT_PREFIX);
	}

	@Override
	public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
		DiscordServerDAO discordServerDAO = new DiscordServerDAO();
		discordServerDAO.deleteDiscordServer(event);
	}
}
