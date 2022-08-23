package database;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import records.CommandContainer;
import records.DiscordServer;
import records.Prefix;
import util.LoadSQLDriver;
import util.SQLQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PrefixDAO {

	public PrefixDAO() {

	}

	/**
	 * Inserts a new prefix into the database.
	 *
	 * @param event  The event that triggered the command.
	 * @param prefix Prefix object.
	 */
	public void insertPrefix(GuildJoinEvent event, String prefix) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO link_bot_db.prefixes " +
					"(prefix, discord_server_id) " +
					"VALUES " +
					"(?, " +
					"(SELECT id " +
					"FROM link_bot_db.discord_servers " +
					"WHERE discord_server_id = ? " +
					"));") {

				@Override
				public Integer parseResult(ResultSet resultSet, int numRowsModified) {
					return numRowsModified;
				}
			};

			sqlQuery.querySingle(connection, new String[]{prefix, String.valueOf(event.getGuild().getIdLong())});

			// Close connection
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the prefix for the given discord server.
	 *
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
			String getDiscordServerQuery = "SELECT * FROM link_bot_db.prefixes WHERE discord_server_id = ?;";

			// Execute SQL query
			PreparedStatement ps = conn.prepareStatement(getDiscordServerQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, discordServer.id());

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
	 *
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
			ps.setString(1, commandContainer.contentOfCommand());
			ps.setLong(2, commandContainer.currentDiscordServer().discordServerID());
			numRowsAffected = ps.executeUpdate();

			// Close connection
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numRowsAffected;
	}
}
