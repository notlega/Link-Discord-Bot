package database;

import records.DiscordUser;
import records.Link;
import util.LoadSQLDriver;
import util.SQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;

public class LinkDAO {

	public LinkDAO() {

	}

	public void insertLink(DiscordUser currentDiscordUser, String linkName, String link) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO links " +
					"(link, link_name, discord_user_id) " +
					"VALUES " +
					"(?, ?, ?);") {

				@Override
				public Integer parseResult(ResultSet resultSet, int numRowsModified) {
					return numRowsModified;
				}
			};

			sqlQuery.querySingle(connection, new String[]{link, linkName, String.valueOf(currentDiscordUser.id())});

			// Close connection
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Link getLinks() {
		return null;
	}
}
