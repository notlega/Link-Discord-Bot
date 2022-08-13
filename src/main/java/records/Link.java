package records;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public record Link(int id, String link, String linkName, int discordUserID, Timestamp createdAt) {
    public static final Pattern LINK_PATTERN = Pattern.compile("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)");
}
