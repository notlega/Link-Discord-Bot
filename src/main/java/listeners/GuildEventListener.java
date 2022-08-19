package listeners;

import database.DiscordServerDAO;
import database.PrefixDAO;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import records.Prefix;

public class GuildEventListener extends ListenerAdapter {

    public GuildEventListener() {

    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        DiscordServerDAO discordServerDAO = new DiscordServerDAO();
        PrefixDAO prefixDAO = new PrefixDAO();
        discordServerDAO.insertDiscordServer(event);
        prefixDAO.insertPrefix(event, Prefix.DEFAULT_PREFIX);
    }
}
