package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.Links;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import io.github.cdimascio.dotenv.Dotenv;

public class GetLinks {

    public GetLinks() {

    }

    public Links[] GetLinks(int discordUserID) {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        Links[] links = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            String getCommandQuery = "SELECT * FROM links WHERE discord_user_id = ?;";

            // Execute SQL query
            PreparedStatement ps = conn.prepareStatement(getCommandQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, discordUserID);
            
            ResultSet rs = ps.executeQuery();

            rs.last();
            links = new Links[rs.getRow()];
            rs.beforeFirst();

            while (rs.next()) {
                links[rs.getRow() - 1] = new Links(rs.getInt("id"), rs.getString("link"), rs.getString("link_name"), rs.getInt("discord_user_id"), rs.getTimestamp("created_at"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return links;
    }
}
