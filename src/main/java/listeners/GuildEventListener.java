package listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildEventListener extends ListenerAdapter {

    public GuildEventListener() {

    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        // Clears all GuildEvents within all Guilds
        event.getGuild().updateCommands().queue();
    }
}
