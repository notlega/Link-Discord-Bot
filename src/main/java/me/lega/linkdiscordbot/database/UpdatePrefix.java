package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.CommandContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdatePrefix {

    public UpdatePrefix() {

    }

    public int updatePrefix(CommandContainer commandContainer) {

        Dotenv dotenv = Dotenv.configure().load();

        int numRowsAffected = -1;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String updateImageLink = "UPDATE prefixes SET prefix = ? WHERE discord_server_id = ?;";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(updateImageLink);
            ps.setString(1, commandContainer.getContentOfCommand());
            ps.setLong(2, commandContainer.getCurrentDiscordServer().getDiscordServerID());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRowsAffected;
    }
}
