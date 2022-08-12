package database;

import io.github.cdimascio.dotenv.Dotenv;
import classes.DiscordServer;
import classes.Prefix;
import listeners.CommandContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrefixDAO {

    public PrefixDAO() {

    }

    /**
     * Inserts a new prefix into the database.
     * @param discordServer DiscordServer object.
     * @param prefix Prefix object.
     */
    public void insertPrefix(DiscordServer discordServer, String prefix) {

        Dotenv dotenv = Dotenv.configure().load();

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertImageLink = "INSERT INTO prefixes (prefix, discord_server_id) VALUES (?, ?);";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(insertImageLink);
            ps.setString(1, prefix);
            ps.setLong(2, discordServer.getId());
            ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the prefix for the given discord server.
     * @param discordServer The discord server to get the prefix for.
     * @return The prefix for the given discord server.
     */
    public Prefix getPrefixByDiscordServerId(DiscordServer discordServer) {

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

    /**
     * Updates the prefix for the given discord server.
     * @param commandContainer The command container.
     * @return The number of rows affected.
     */
    public int updatePrefix(CommandContainer commandContainer) {

        Dotenv dotenv = Dotenv.configure().load();

        int numRowsAffected = 0;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String updateImageLink = "UPDATE prefixes SET prefix = ? WHERE discord_server_id = ?;";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(updateImageLink);
            ps.setString(1, commandContainer.getContentOfCommand());
            ps.setLong(2, commandContainer.getCurrentDiscordServer().getDiscordServerID());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRowsAffected;
    }
}
