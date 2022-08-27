package listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import records.CommandContainer;
import util.CaseConverter;
import util.CommandHandler;

public class SlashCommandInteractionEventListener extends ListenerAdapter {

	public SlashCommandInteractionEventListener() {

	}

	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		CommandHandler.handleCommand(new CommandContainer(CaseConverter.pascalCase(event.getCommandPath()), event.getOptions(), event));
	}
}
