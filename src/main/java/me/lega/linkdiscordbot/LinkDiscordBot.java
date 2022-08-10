package me.lega.linkdiscordbot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
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
        JDA bot = null;

        try {

            bot = JDABuilder.createDefault(dotenv.get("TOKEN"))
                    .setAutoReconnect(true)
                    .enableCache(EnumSet.of(CacheFlag.VOICE_STATE))
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("with waifus"))
                    .setRequestTimeoutRetry(true)
                    .addEventListeners(new CommandListener())
                    .build();

        } catch (LoginException LE) {
            LE.printStackTrace();
        }
    }
}
