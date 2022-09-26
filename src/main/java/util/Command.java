package util;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import records.CommandContainer;

public abstract class Command {
	public abstract String commandDescription();

	public abstract OptionData[] commandOptions();

	public abstract void executeCommand(CommandContainer commandContainer) throws Exception;
}
