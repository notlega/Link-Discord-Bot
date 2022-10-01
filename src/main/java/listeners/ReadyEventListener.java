package listeners;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CaseConverter;
import util.CommandHandler;

import java.util.ArrayList;

public class ReadyEventListener extends ListenerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ReadyEventListener.class);

	public ReadyEventListener() {

	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		logger.info("Adding commands to global slash commands...");
		ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

		CommandHandler.getCommands().forEach((key, command) -> commandDataArrayList.add(
				Commands.slash(
						CaseConverter.kebabCase(key),
						command.commandDescription()
				).addOptions(command.commandOptions())
		));

		event.getJDA().updateCommands().addCommands(commandDataArrayList).queue();
		logger.info("Global slash commands added.");
	}
}
