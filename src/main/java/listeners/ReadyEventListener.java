package listeners;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import util.CaseConverter;
import util.CommandHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReadyEventListener extends ListenerAdapter {

	public ReadyEventListener() {

	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		ArrayList<CommandData> commandDataArrayList = new ArrayList<>();

		CommandHandler.getCommands().forEach((key, value) -> {
			try {
				Object classInstance = value.getDeclaredConstructors()[0].newInstance();
				Method getCommandDescription = value.getMethod("getCommandDescription");
				Method getOptions = value.getMethod("getOptions");

				commandDataArrayList.add(
						Commands.slash(
										CaseConverter.kebabCase(key),
										(String) getCommandDescription.invoke(classInstance))
								.addOptions((OptionData[]) getOptions.invoke(classInstance)));
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
					 InstantiationException e) {
				e.printStackTrace();
			}
		});
		event.getJDA().updateCommands().addCommands(commandDataArrayList).queue();
	}
}
