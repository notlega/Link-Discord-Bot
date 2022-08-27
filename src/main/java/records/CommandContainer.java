package records;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.List;

/**
 * @param command The command that was sent
 * @param options The options that were sent
 * @param event   The event that triggered the command
 */
public record CommandContainer(String command, List<OptionMapping> options, SlashCommandInteractionEvent event) {

}
