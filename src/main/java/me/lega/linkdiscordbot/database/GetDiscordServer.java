package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.DiscordServers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import io.github.cdimascio.dotenv.Dotenv;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetDiscordServer {
    
    public GetDiscordServer() {
        
    }
    
    public DiscordServers GetDiscordServer(MessageReceivedEvent event) {
        
        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();
        
        DiscordServers discordServers = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordServerQuery = "SELECT * FROM discord_servers WHERE discord_server_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, event.getGuild().getIdLong());
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                discordServers = new DiscordServers(rs.getInt("id"), rs.getLong("discord_server_id"), rs.getString("discord_server_name"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discordServers;
    }
}
