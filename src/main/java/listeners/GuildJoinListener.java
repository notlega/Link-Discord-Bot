package listeners;

import database.DiscordServerDAO;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildJoinListener extends ListenerAdapter {

    public GuildJoinListener() {

    }

    @Override
    public void onGuildJoined(@NotNull GuildJoinEvent event) {
        DiscordServerDAO discordServerDAO = new DiscordServerDAO();
        discordServerDAO.insertDiscordServer(event);
    }
}
