package listeners;

import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import util.CaseConverter;
import util.CommandHandler;
import util.MethodInvocator;

import java.util.ArrayList;

public class GuildEventListener extends ListenerAdapter {

	public GuildEventListener() {

	}

	@Override
	public void onGuildReady(@NotNull GuildReadyEvent event) {
		ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

		CommandHandler.getCommands().forEach((key, clazz) -> {
			MethodInvocator methodInvocator = new MethodInvocator(clazz);
			methodInvocator.invokeCommands("getCommandDescription", new Object[]{});

			commandDataArrayList.add(
					Commands.slash(
									CaseConverter.kebabCase(key),
									(String) methodInvocator.invokeCommands("getCommandDescription", new Object[]{}))
							.addOptions((OptionData[]) methodInvocator.invokeCommands("getOptions", new Object[]{})));
		});

		event.getGuild().updateCommands().addCommands(commandDataArrayList).queue();
	}
}
