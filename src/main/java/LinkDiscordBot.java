import io.github.cdimascio.dotenv.Dotenv;
import listeners.ReadyEventListener;
import listeners.SlashCommandInteractionEventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CommandHandler;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class LinkDiscordBot {

    private static final Logger logger = LoggerFactory.getLogger(LinkDiscordBot.class);

    public static void main(String[] args) {
        initialise();
    }

    public static void initialise() {
        try {
            Dotenv dotenv = Dotenv
                    .configure()
                    .ignoreIfMissing()
                    .ignoreIfMalformed()
                    .load();

            CommandHandler.initialiseCommands();

            JDABuilder LinkDiscordBot = JDABuilder.createDefault(dotenv.get("DISCORD_TOKEN"));
            LinkDiscordBot.setAutoReconnect(true);
            LinkDiscordBot.enableCache(EnumSet.of(CacheFlag.VOICE_STATE));
            LinkDiscordBot.setStatus(OnlineStatus.IDLE);
            LinkDiscordBot.setActivity(Activity.playing("with waifus"));
            LinkDiscordBot.setRequestTimeoutRetry(true);

            LinkDiscordBot.addEventListeners(
                    new ReadyEventListener(),
                    new SlashCommandInteractionEventListener()
            );

            LinkDiscordBot.build();
        } catch (LoginException e) {
            logger.error("Cannot login, invalid token.");
        }
    }
}
