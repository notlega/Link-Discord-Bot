package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.Links;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import io.github.cdimascio.dotenv.Dotenv;

public class GetDBLink {

    public GetDBLink() {

    }

    public Links GetLink(String linkName, String link) {

        Links linksClass = new Links();
        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        Links links = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getLinkQuery;
            PreparedStatement ps = null;

            if (link.equals("")) {
                linkName = "%" + linkName + "%";
                getLinkQuery = "SELECT * FROM links WHERE link_name LIKE ?;";

                // Execute SQL query
                ps = conn.prepareStatement(getLinkQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, linkName);
            } else if (linksClass.getLINK_PATTERN().matcher(link).find()) {
                getLinkQuery = "SELECT * FROM links WHERE link = ?;";

                // Execute SQL query
                ps = conn.prepareStatement(getLinkQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, link);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                links = new Links(rs.getInt("id"), rs.getString("link"), rs.getString("link_name"), rs.getInt("discord_user_id"), rs.getTimestamp("created_at"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return links;
    }
}
