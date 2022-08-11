package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.DiscordServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertPrefix {

    public InsertPrefix() {

    }

    public int insertPrefix(DiscordServer discordServer, String prefix) {

        Dotenv dotenv = Dotenv.configure().load();

        int numRowsAffected = -1;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertImageLink = "INSERT INTO prefixes (prefix, discord_server_id) VALUES (?, ?);";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(insertImageLink);
            ps.setString(1, prefix);
            ps.setLong(2, discordServer.getId());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRowsAffected;
    }
}
