package database;

import classes.DiscordServer;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiscordServerDAO {

    public DiscordServerDAO() {

    }

    public void insertDiscordServer(MessageReceivedEvent event) {

        DiscordServerDAO discordServerDAO = new DiscordServerDAO();
        DiscordServer discordServer = discordServerDAO.getDiscordServerByDiscordServerId(event);

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

    public DiscordServer getDiscordServerByDiscordServerId(MessageReceivedEvent event) {

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
