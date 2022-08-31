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

	public Link getLinkByLink(String strLink) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<Link> sqlQuery = new SQLQuery<>("SELECT * FROM links WHERE link = ?;") {

				@Override
				public Link parseResult(ResultSet resultSet, int numRowsModified) {
					Link link = null;
					try {
						if (resultSet.next()) {
							link = new Link(resultSet.getInt("id"), resultSet.getString("link"), resultSet.getString("link_name"), resultSet.getInt("discord_user_id"), resultSet.getTimestamp("created_at"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return link;
				}
			};

			Link returnLink = sqlQuery.querySingle(connection, new String[]{strLink});

			// Close connection
			connection.close();
			return returnLink;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Link getLinkByLinkName(String strLinkName) {
		try {
			Connection connection = LoadSQLDriver.loadSQLDriver();
			SQLQuery<Link> sqlQuery = new SQLQuery<>("SELECT * FROM links WHERE link_name LIKE ?;") {

				@Override
				public Link parseResult(ResultSet resultSet, int numRowsModified) {
					Link link = null;
					try {
						if (resultSet.next()) {
							link = new Link(resultSet.getInt("id"), resultSet.getString("link"), resultSet.getString("link_name"), resultSet.getInt("discord_user_id"), resultSet.getTimestamp("created_at"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					return link;
				}
			};

			Link link = sqlQuery.querySingle(connection, new String[]{"%" + strLinkName + "%"});

			// Close connection
			connection.close();
			return link;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
