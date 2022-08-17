package database;

import records.DiscordServer;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import util.LoadSQLDriver;
import util.SQLQuery;

import java.sql.*;

public class DiscordServerDAO {

    public DiscordServerDAO() {

    }

    public void insertDiscordServer(MessageReceivedEvent event) {

        try {

            Connection connection = LoadSQLDriver.loadSQLDriver();
            SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO discord_servers (discord_server_id, discord_server_name) VALUES (?, ?);\n") {
                @Override
                public Integer ParseResult(ResultSet resultSet) throws SQLException {
                    return resultSet.getInt("discord_server_id");
            };

            int discordServer = sqlQuery.QuerySingle(connection, new String[] { String.valueOf(event.getGuild().getIdLong()) });
            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DiscordServer getDiscordServerByDiscordServerId(MessageReceivedEvent event) {

        try {

            Connection connection = LoadSQLDriver.loadSQLDriver();
            SQLQuery<DiscordServer> sqlQuery = new SQLQuery<>("SELECT * FROM discord_servers WHERE discord_server_id = ?;") {
                @Override
                public DiscordServer ParseResult(ResultSet resultSet) throws SQLException {
                    return new DiscordServer(resultSet.getInt("id"), resultSet.getLong("discord_server_id"), resultSet.getString("discord_server_name"));
                }
            };

            DiscordServer discordServer = sqlQuery.QuerySingle(connection, new String[] { String.valueOf(event.getGuild().getIdLong()) });

            // Close connection
            connection.close();
            return discordServer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
