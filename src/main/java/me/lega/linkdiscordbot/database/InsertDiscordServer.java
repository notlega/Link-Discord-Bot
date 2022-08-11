package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.DiscordServer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertDiscordServer {

    public InsertDiscordServer() {

    }

    public void insertDiscordServer(MessageReceivedEvent event) {

        GetDiscordServer getDiscordServer = new GetDiscordServer();
        DiscordServer discordServer = getDiscordServer.getDiscordServer(event);

        if (discordServer != null) {
            return;
        }

        Dotenv dotenv = Dotenv.configure().load();

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "INSERT INTO discord_servers (discord_server_id, discord_server_name) VALUES (?, ?);";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, event.getGuild().getIdLong());
            ps.setString(2, event.getGuild().getName());
            ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
