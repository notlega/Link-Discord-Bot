package embeds;

import records.EmbedClass;
import classes.EmbedField;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SuccessEmbed {

    public SuccessEmbed() {

    }

    /**
     * @param embedData Embed data
     * @param event Message received event
     */
    public void successEmbed(EmbedClass embedData, MessageReceivedEvent event) {

        EmbedBuilder successEmbed = new EmbedBuilder();

        successEmbed.setColor(embedData.Color());
        for (EmbedField field : embedData.Field()) {
            successEmbed.addField(field.getName(), field.getValue(), field.isInline());
        }
        successEmbed.setFooter(embedData.Footer());
        if (embedData.Image() != null) {
            successEmbed.setImage(embedData.Image());
        }
        successEmbed.setTimestamp(embedData.Timestamp());
        successEmbed.setTitle(embedData.Title());

        event.getMessage().replyEmbeds(successEmbed.build()).queue();
    }
}
