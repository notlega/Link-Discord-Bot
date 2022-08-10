package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.Prefixes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.DiscordServers;

public class GetPrefix {

    public GetPrefix() {

    }

    public Prefixes GetPrefix(DiscordServers discordServers) {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        Prefixes prefixes = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "SELECT * FROM prefixes WHERE discord_server_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, discordServers.getId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prefixes = new Prefixes(rs.getInt("id"), rs.getString("prefix"), rs.getInt("discord_server_id"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefixes;
    }
}
