package listeners;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import util.CaseConverter;
import util.CommandHandler;

import java.util.ArrayList;

public class ReadyEventListener extends ListenerAdapter {

	public ReadyEventListener() {

	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

		CommandHandler.getCommands().forEach((key, command) -> commandDataArrayList.add(
				Commands.slash(
						CaseConverter.kebabCase(key),
						command.commandDescription()
				).addOptions(command.commandOptions())
		));

		event.getJDA().updateCommands().addCommands(commandDataArrayList).queue();
	}
}
