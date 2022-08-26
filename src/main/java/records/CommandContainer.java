package records;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * @param currentDiscordUser   DiscordUser object
 * @param command              The command that was sent
 * @param contentOfCommand     The content of the command (if there is any)
 * @param event                The event that triggered the command
 */
public record CommandContainer(DiscordUser currentDiscordUser, String command, String contentOfCommand,
							   MessageReceivedEvent event) {

}
