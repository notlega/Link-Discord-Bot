package interfaces;

import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface ICommandable {

    String getCommandDescription();

    OptionData[] getOptions();
}
