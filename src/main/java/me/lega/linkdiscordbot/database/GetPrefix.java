package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.DiscordServer;
import me.lega.linkdiscordbot.classes.Prefix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetPrefix {

    public GetPrefix() {

    }

    public Prefix getPrefix(DiscordServer discordServer) {

        Dotenv dotenv = Dotenv.configure().load();
        Prefix prefix = null;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "SELECT * FROM prefix WHERE discord_server_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, discordServer.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prefix = new Prefix(rs.getInt("id"), rs.getString("prefix"), rs.getInt("discord_server_id"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefix;
    }
}
