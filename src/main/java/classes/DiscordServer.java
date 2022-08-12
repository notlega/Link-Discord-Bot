package classes;

public class DiscordServer {

    private int id;
    private long discordServerID;
    private String discordServerName;

    public DiscordServer(int id, long discordServerID, String discordServerName) {
        this.id = id;
        this.discordServerID = discordServerID;
        this.discordServerName = discordServerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDiscordServerID() {
        return discordServerID;
    }

    public void setDiscordServerID(long discordServerID) {
        this.discordServerID = discordServerID;
    }

    public String getDiscordServerName() {
        return discordServerName;
    }

    public void setDiscordServerName(String discordServerName) {
        this.discordServerName = discordServerName;
    }
}
