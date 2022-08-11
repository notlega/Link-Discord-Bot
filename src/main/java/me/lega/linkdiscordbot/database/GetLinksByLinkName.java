package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.Link;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetLinksByLinkName {

    public GetLinksByLinkName() {

    }

    public Link getLinksByLinkName(String linkName, String link) {

        Link linkClass = new Link();
        Dotenv dotenv = Dotenv.configure().load();
        Link links = null;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getLinkQuery;
            PreparedStatement ps = null;

            if (link.equals("")) {
                linkName = "%" + linkName + "%";
                getLinkQuery = "SELECT * FROM links WHERE link_name LIKE ?;";

                // Execute SQL query
                ps = conn.prepareStatement(getLinkQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, linkName);
            } else if (linkClass.getLINK_PATTERN().matcher(link).find()) {
                getLinkQuery = "SELECT * FROM links WHERE link = ?;";

                // Execute SQL query
                ps = conn.prepareStatement(getLinkQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, link);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                links = new Link(rs.getInt("id"), rs.getString("link"), rs.getString("link_name"), rs.getInt("discord_user_id"), rs.getTimestamp("created_at"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return links;
    }
}
