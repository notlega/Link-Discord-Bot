package records;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * @param currentDiscordServer DiscordServer object
 * @param currentDiscordUser   DiscordUser object
 * @param currentPrefix        Prefix object
 * @param command              The command that was sent
 * @param contentOfCommand     The content of the command (if there is any)
 * @param event                The event that triggered the command
 */
public record CommandContainer(DiscordServer currentDiscordServer, DiscordUser currentDiscordUser, Prefix currentPrefix,
							   String command, String contentOfCommand, MessageReceivedEvent event) {

}
