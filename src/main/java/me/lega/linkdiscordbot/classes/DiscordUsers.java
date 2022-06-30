package me.lega.linkdiscordbot.classes;

public class DiscordUsers {
    
    private int id;
    private int privilegeLevel;
    private long discordUserID;
    private String discordUserTag;

    public DiscordUsers(int id, int privilegeLevel, long discordUserID, String discordUserTag) {
        this.id = id;
        this.privilegeLevel = privilegeLevel;
        this.discordUserID = discordUserID;
        this.discordUserTag = discordUserTag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public long getDiscordUserID() {
        return discordUserID;
    }

    public void setDiscordUserID(long discordUserID) {
        this.discordUserID = discordUserID;
    }

    public String getDiscordUserTag() {
        return discordUserTag;
    }

    public void setDiscordUserTag(String discordUserTag) {
        this.discordUserTag = discordUserTag;
    }
}
