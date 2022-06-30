package me.lega.linkdiscordbot;

import java.util.EnumSet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.OnlineStatus;

import io.github.cdimascio.dotenv.Dotenv;

import javax.security.auth.login.LoginException;

public class LinkDiscordBot {
    
    public static void main(String[] args) {
        initialise();
    }

    public static JDA initialise() {
        
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

        return bot;
    }
}
