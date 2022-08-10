package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.CommandContainer;
import me.lega.linkdiscordbot.classes.DiscordServers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdatePrefix {

    public UpdatePrefix() {

    }

    public int UpdatePrefix(DiscordServers discordServers, CommandContainer commandContainer) {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        int numRowsAffected = -1;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String updateImageLink = "UPDATE prefixes SET prefix = ? WHERE discord_server_id = ?;";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(updateImageLink);
            ps.setString(1, commandContainer.getContentOfCommand()[0]);
            ps.setLong(2, discordServers.getId());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRowsAffected;
    }
}
