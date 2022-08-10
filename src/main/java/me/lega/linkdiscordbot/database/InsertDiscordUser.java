package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.DiscordUsers;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InsertDiscordUser {

    public InsertDiscordUser() {

    }

    public DiscordUsers insertDiscordUser(MessageReceivedEvent event) {

        GetDiscordUser getDiscordUser = new GetDiscordUser();
        DiscordUsers discordUsers = getDiscordUser.GetDiscordUser(event);

        if (discordUsers != null) {
            return discordUsers;
        }

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertDiscordUserQuery = "INSERT INTO discord_users (privilege_level, discord_user_id, discord_user_tag) VALUES (?, ?, ?);";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(insertDiscordUserQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, 3);
            ps.setLong(2, event.getAuthor().getIdLong());
            ps.setString(3, event.getAuthor().getAsTag());
            ps.executeUpdate();

            discordUsers = getDiscordUser.GetDiscordUser(event);

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return discordUsers;
    }
}
