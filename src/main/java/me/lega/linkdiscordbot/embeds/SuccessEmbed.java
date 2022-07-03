package me.lega.linkdiscordbot.embeds;

import me.lega.linkdiscordbot.classes.EmbedClass;
import me.lega.linkdiscordbot.classes.EmbedField;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SuccessEmbed {

    public SuccessEmbed() {

    }

    public void SuccessEmbed(EmbedClass embedData, MessageReceivedEvent event) {

        EmbedBuilder successEmbed = new EmbedBuilder();

        successEmbed.setColor(embedData.getColor());
        for (EmbedField field : embedData.getField()) {
            successEmbed.addField(field.getName(), field.getValue(), field.isInline());
        }
        successEmbed.setFooter(embedData.getFooter());
        if (embedData.getImage() != null) {
            successEmbed.setImage(embedData.getImage());
        }
        successEmbed.setTimestamp(embedData.getTimestamp());
        System.out.println(embedData.getTimestamp());
        successEmbed.setTitle(embedData.getTitle());

        event.getMessage().replyEmbeds(successEmbed.build()).queue();
    }
}
