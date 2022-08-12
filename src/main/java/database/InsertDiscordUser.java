package database;

import io.github.cdimascio.dotenv.Dotenv;
import classes.DiscordUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertDiscordUser {

    public InsertDiscordUser() {

    }

    public void insertDiscordUser(MessageReceivedEvent event) {

        GetDiscordUser getDiscordUser = new GetDiscordUser();
        DiscordUser discordUser = getDiscordUser.getDiscordUser(event);

        if (discordUser != null) {
            return;
        }

        Dotenv dotenv = Dotenv.configure().load();
        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertDiscordUserQuery = "INSERT INTO discord_users (privilege_level, discord_user_id, discord_user_tag) VALUES (?, ?, ?);";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(insertDiscordUserQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, 3);
            ps.setLong(2, event.getAuthor().getIdLong());
            ps.setString(3, event.getAuthor().getAsTag());
            ps.executeUpdate();

            discordUser = getDiscordUser.getDiscordUser(event);

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
