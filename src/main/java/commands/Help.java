package commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import util.CaseConverter;
import util.CommandHandler;
import records.CommandContainer;

import java.util.Objects;
import java.util.stream.Collectors;

public class Help {

	public String getCommandDescription() {
		return "SOS for commands. Displays all commands and command descriptions.";
	}

	public OptionData[] getOptions() {
		return new OptionData[] {
				new OptionData(OptionType.STRING, CaseConverter.kebabCase("commandName"), "Name of command you want to search", false)
		};
	}

	public void help(CommandContainer commandContainer) {

		String output;

		if (commandContainer.options().isEmpty()) {
			output = CommandHandler.getCommands()
					.values()
					.stream()
					.map(clazz -> clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1) + ": " + clazz.getSimpleName() + "\n")
					.collect(Collectors.joining("", "Use !help <command> to search for that specific command!\n\n", ""));
		} else {
			output = CommandHandler.getCommands()
					.keySet()
					.stream()
					.filter(className -> Objects.equals(className, commandContainer.options().toString().substring(0, 1).toUpperCase() + commandContainer.command().substring(1)))
					.map(className -> className + ": " + className + "\n")
					.collect(Collectors.joining("", "", ""));
		}

		if (output.equals("")) {
			commandContainer.event().reply(commandContainer.options().get(0) + " is not a valid command!").queue();
			return;
		}

		commandContainer.event().reply(output).queue();
	}
}
