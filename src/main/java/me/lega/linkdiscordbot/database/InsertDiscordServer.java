package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.DiscordServers;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InsertDiscordServer {

    public InsertDiscordServer() {

    }

    public DiscordServers InsertDiscordServer(MessageReceivedEvent event) {

        GetDiscordServer getDiscordServer = new GetDiscordServer();
        DiscordServers discordServers = getDiscordServer.GetDiscordServer(event);

        if (discordServers != null) {
            return discordServers;
        }

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "INSERT INTO discord_servers (discord_server_id, discord_server_name) VALUES (?, ?);";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, event.getGuild().getIdLong());
            ps.setString(2, event.getGuild().getName());
            ps.executeUpdate();

            discordServers = getDiscordServer.GetDiscordServer(event);

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discordServers;
    }
}
