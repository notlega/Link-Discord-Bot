package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.DiscordUsers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import io.github.cdimascio.dotenv.Dotenv;

public class InsertLink {

    public InsertLink() {

    }

    public int InsertLink(DiscordUsers discordUsers, String link, String linkName) {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        int numRowsAffected = -1;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertImageLink = "INSERT INTO links (link, link_name, discord_user_id) VALUES (?, ?, ?);";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(insertImageLink);
            ps.setString(1, link);
            ps.setString(2, linkName);
            ps.setLong(3, discordUsers.getId());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return numRowsAffected;
    }
}
