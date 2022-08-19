package database;

import io.github.cdimascio.dotenv.Dotenv;
import records.Link;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetLinks {

	public GetLinks() {

	}

	public Link[] GetLinks(int discordUserID) {

		Dotenv dotenv = Dotenv.configure().load();
		Link[] links = null;

		try {

			// Load JDBC Driver
			Class.forName(dotenv.get("JDBC_DRIVER"));

			// Open connection to database
			Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

			// SQL query string
			String getCommandQuery = "SELECT * FROM links WHERE discord_user_id = ?;";

			// Execute SQL query
			PreparedStatement ps = conn.prepareStatement(getCommandQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, discordUserID);

			ResultSet rs = ps.executeQuery();

			rs.last();
			links = new Link[rs.getRow()];
			rs.beforeFirst();

			while (rs.next()) {
				links[rs.getRow() - 1] = new Link(rs.getInt("id"), rs.getString("link"), rs.getString("link_name"), rs.getInt("discord_user_id"), rs.getTimestamp("created_at"));
			}

			// Close connection
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return links;
	}
}
