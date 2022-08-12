package database;

import io.github.cdimascio.dotenv.Dotenv;
import classes.DiscordUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertLink {

    public InsertLink() {

    }

    public int insertLink(DiscordUser currentDiscordUser, String linkName, String link) {

        Dotenv dotenv = Dotenv.configure().load();

        int numRowsAffected = -1;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String insertImageLink = "INSERT INTO links (link, link_name, discord_user_id) VALUES (?, ?, ?);";

            // Execute SQL Query
            PreparedStatement ps = conn.prepareStatement(insertImageLink);
            ps.setString(1, link);
            ps.setString(2, linkName);
            ps.setLong(3, currentDiscordUser.getId());
            numRowsAffected = ps.executeUpdate();

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return numRowsAffected;
    }
}
