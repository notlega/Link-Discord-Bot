package me.lega.linkdiscordbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContainer {

    private final String command;
    private final String[] contentOfCommand;
    private final MessageReceivedEvent event;

    public CommandContainer(String command, String[] contentOfCommand, MessageReceivedEvent event) {
        this.command = command;
        this.contentOfCommand = contentOfCommand;
        this.event = event;
    }

    public String getCommand() {
        return command;
    }

    public String[] getContentOfCommand() {
        return contentOfCommand;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
