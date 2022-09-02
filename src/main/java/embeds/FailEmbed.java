package embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class FailEmbed {

	/**
	 * @param failMessage Fail message
	 * @param event       Slash command event
	 */
	public static void failEmbed(String failMessage, SlashCommandInteractionEvent event) {

		EmbedBuilder failEmbed = new EmbedBuilder();

		failEmbed.setColor(Color.RED);
		failEmbed.setTitle(failMessage);

		event.replyEmbeds(failEmbed.build()).queue();
	}
}
