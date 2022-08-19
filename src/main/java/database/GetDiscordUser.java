package database;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import records.DiscordUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetDiscordUser {

	public GetDiscordUser() {

	}

	public DiscordUser getDiscordUser(MessageReceivedEvent event) {

		Dotenv dotenv = Dotenv.configure().load();
		DiscordUser discordUser = null;

		try {

			// Load JDBC Driver
			Class.forName(dotenv.get("JDBC_DRIVER"));

			// Open connection to database
			Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

			// SQL query string
			String getDiscordUserQuery = "SELECT * FROM discord_users WHERE discord_user_id = ?;";

			// Execute SQL query
			PreparedStatement ps = conn.prepareStatement(getDiscordUserQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setLong(1, event.getAuthor().getIdLong());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				discordUser = new DiscordUser(rs.getInt("id"), rs.getInt("privilege_level"), rs.getLong("discord_user_id"), rs.getString("discord_user_tag"));
			}

			// Close connection
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return discordUser;
	}
}
