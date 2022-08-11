package me.lega.linkdiscordbot;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.listeners.CommandListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class LinkDiscordBot {

    public static void main(String[] args) {
        initialise();
    }

    public static void initialise() {

        Dotenv dotenv = Dotenv.configure().load();

        try {

            JDABuilder.createDefault(dotenv.get("TOKEN"))
                    .setAutoReconnect(true)
                    .enableCache(EnumSet.of(CacheFlag.VOICE_STATE))
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("with waifus"))
                    .setRequestTimeoutRetry(true)
                    .addEventListeners(new CommandListener())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
