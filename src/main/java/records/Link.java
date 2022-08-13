package records;

import java.sql.Timestamp;

public record Link(int id, String link, String linkName, int discordUserID, Timestamp createdAt) {

};