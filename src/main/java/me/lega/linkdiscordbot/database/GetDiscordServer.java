package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.DiscordServer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetDiscordServer {

    public GetDiscordServer() {

    }

    public DiscordServer getDiscordServer(MessageReceivedEvent event) {

        Dotenv dotenv = Dotenv.configure().load();
        DiscordServer discordServer = null;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "SELECT * FROM discord_servers WHERE discord_server_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, event.getGuild().getIdLong());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                discordServer = new DiscordServer(rs.getInt("id"), rs.getLong("discord_server_id"), rs.getString("discord_server_name"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discordServer;
    }
}
