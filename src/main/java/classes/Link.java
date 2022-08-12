package classes;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public class Link {

    private static final Pattern LINK_PATTERN = Pattern.compile("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");
    private int id;
    private String link;
    private String linkName;
    private int discordUserID;
    private Timestamp createdAt;

    public Link() {

    }

    public Link(int id, String link, String linkName, int discordUserID, Timestamp createdAt) {
        this.id = id;
        this.link = link;
        this.linkName = linkName;
        this.discordUserID = discordUserID;
        this.createdAt = createdAt;
    }

    public Pattern getLINK_PATTERN() {
        return LINK_PATTERN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public int getDiscordUserID() {
        return discordUserID;
    }

    public void setDiscordUserID(int discordUserID) {
        this.discordUserID = discordUserID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
