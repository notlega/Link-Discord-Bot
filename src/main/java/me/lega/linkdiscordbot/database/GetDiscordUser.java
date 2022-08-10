package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.DiscordUsers;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetDiscordUser {

    public GetDiscordUser() {

    }

    public DiscordUsers GetDiscordUser(MessageReceivedEvent event) {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        DiscordUsers discordUsers = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getDiscordUserQuery = "SELECT * FROM discord_users WHERE discord_user_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getDiscordUserQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, event.getAuthor().getIdLong());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                discordUsers = new DiscordUsers(rs.getInt("id"), rs.getInt("privilege_level"), rs.getLong("discord_user_id"), rs.getString("discord_user_tag"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discordUsers;
    }
}
