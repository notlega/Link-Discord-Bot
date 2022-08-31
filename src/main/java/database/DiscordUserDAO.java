package database;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import records.DiscordUser;
import util.LoadSQLDriver;
import util.SQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscordUserDAO {

	public DiscordUserDAO() {

	}

	public void insertDiscordUser(SlashCommandInteractionEvent event) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO discord_users " +
					"(privilege_level, discord_user_id, discord_user_tag) " +
					"VALUES " +
					"(?, ?, ?);") {

				@Override
				public Integer parseResult(ResultSet resultSet, int numRowsModified) {
					return numRowsModified;
				}
			};

			sqlQuery.querySingle(connection, new String[]{String.valueOf(3), String.valueOf(event.getUser().getIdLong()), event.getUser().getAsTag()});

			// Close connection
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DiscordUser getDiscordUser(SlashCommandInteractionEvent event) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<DiscordUser> sqlQuery = new SQLQuery<>("SELECT * " +
					"FROM discord_users " +
					"WHERE discord_user_id = ?;") {

				@Override
				public DiscordUser parseResult(ResultSet resultSet, int numRowsModified) throws SQLException {
					return new DiscordUser(resultSet.getInt("id"),
							resultSet.getInt("privilege_level"),
							resultSet.getLong("discord_user_id"),
							resultSet.getString("discord_user_name"));
				}
			};

			DiscordUser discordUser = sqlQuery.querySingle(connection, new String[]{String.valueOf(event.getUser().getIdLong())});

			// Close connection
			connection.close();
			return discordUser;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
