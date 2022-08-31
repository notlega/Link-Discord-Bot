package embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import records.EmbedClass;
import records.EmbedField;

public class SuccessEmbed {

	public SuccessEmbed() {

	}

	/**
	 * @param embedData Embed data
	 * @param event     Message received event
	 */
	public void successEmbed(EmbedClass embedData, SlashCommandInteractionEvent event) {

		EmbedBuilder successEmbed = new EmbedBuilder();

		successEmbed.setColor(embedData.color());
		for (EmbedField field : embedData.field()) {
			successEmbed.addField(field.name(), field.value(), field.inline());
		}
		successEmbed.setFooter(embedData.footer());
		if (embedData.image() != null) {
			successEmbed.setImage(embedData.image());
		}
		successEmbed.setTimestamp(embedData.timestamp());
		successEmbed.setTitle(embedData.title());

		event.replyEmbeds(successEmbed.build()).queue();
	}
}
