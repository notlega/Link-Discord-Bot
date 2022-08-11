package me.lega.linkdiscordbot.classes;

public class Prefix {

    public static final String DEFAULT_PREFIX = "!";
    private int id;
    private String prefix;
    private int discordServerID;

    public Prefix(int id, String prefix, int discordServerID) {
        this.id = id;
        this.prefix = prefix;
        this.discordServerID = discordServerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getDiscordServerID() {
        return discordServerID;
    }

    public void setDiscordServerID(int discordServerID) {
        this.discordServerID = discordServerID;
    }
}
