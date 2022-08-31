import io.github.cdimascio.dotenv.Dotenv;
import listeners.ReadyEventListener;
import listeners.SlashCommandInteractionEventListener;
import util.CommandHandler;
import listeners.GuildEventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class LinkDiscordBot {

	public static void main(String[] args) throws LoginException {
		initialise();
	}

	public static void initialise() throws LoginException {

		Dotenv dotenv = Dotenv.configure().load();
		CommandHandler.initialiseCommands();

		JDABuilder.createDefault(dotenv.get("DISCORD_TOKEN"))
				.setAutoReconnect(true)
				.enableCache(EnumSet.of(CacheFlag.VOICE_STATE))
				.setStatus(OnlineStatus.IDLE)
				.setActivity(Activity.playing("with waifus"))
				.setRequestTimeoutRetry(true)
				.addEventListeners(
						new ReadyEventListener(),
						new GuildEventListener(),
						new SlashCommandInteractionEventListener()
				)
				.build();
	}
}
