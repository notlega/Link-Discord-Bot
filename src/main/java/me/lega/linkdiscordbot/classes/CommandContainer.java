package me.lega.linkdiscordbot.classes;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandContainer {

    private final DiscordServer currentDiscordServer;
    private final DiscordUser currentDiscordUser;
    private final Prefixes currentPrefix;
    private final String command;
    private final String contentOfCommand;
    private final MessageReceivedEvent event;

    /**
     * Constructor for CommandContainer
     *
     * @param currentDiscordServer DiscordServer object
     * @param currentDiscordUser   DiscordUser object
     * @param currentPrefix        Prefix object
     * @param command              The command that was sent
     * @param contentOfCommand     The content of the command (if there is any)
     * @param event                The event that triggered the command
     */
    public CommandContainer(DiscordServer currentDiscordServer, DiscordUser currentDiscordUser, Prefixes currentPrefix, String command, String contentOfCommand, MessageReceivedEvent event) {
        this.currentDiscordServer = currentDiscordServer;
        this.currentDiscordUser = currentDiscordUser;
        this.currentPrefix = currentPrefix;
        this.command = command;
        this.contentOfCommand = contentOfCommand;
        this.event = event;
    }

    public DiscordServer getCurrentDiscordServer() {
        return currentDiscordServer;
    }

    public DiscordUser getCurrentDiscordUser() {
        return currentDiscordUser;
    }

    public Prefixes getCurrentPrefix() {
        return currentPrefix;
    }

    public String getCommand() {
        return command;
    }

    public String getContentOfCommand() {
        return contentOfCommand;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
