package database;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import records.DiscordServer;
import util.LoadSQLDriver;
import util.SQLQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscordServerDAO {

    public DiscordServerDAO() {

    }

    public void insertDiscordServer(GuildJoinEvent event) {
        try {
            Connection connection = LoadSQLDriver.loadSQLDriver();
            SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO discord_servers (discord_server_id, discord_server_name) VALUES (?, ?);") {

                @Override
                public Integer parseResult(ResultSet resultSet, int numRowsModified) {
                    return numRowsModified;
                }
            };

            sqlQuery.querySingle(connection, new String[]{String.valueOf(event.getGuild().getIdLong()), event.getGuild().getName()});

            // close connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DiscordServer getDiscordServerByDiscordServerId(MessageReceivedEvent event) {
        try {
            Connection connection = LoadSQLDriver.loadSQLDriver();
            SQLQuery<DiscordServer> sqlQuery = new SQLQuery<>("SELECT * FROM discord_servers WHERE discord_server_id = ?;") {
                @Override
                public DiscordServer parseResult(ResultSet resultSet, int numRowsModified) throws SQLException {
                    return new DiscordServer(resultSet.getInt("id"), resultSet.getLong("discord_server_id"), resultSet.getString("discord_server_name"));
                }
            };

            DiscordServer discordServer = sqlQuery.querySingle(connection, new String[]{String.valueOf(event.getGuild().getIdLong())});

            // Close connection
            connection.close();
            return discordServer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteDiscordServer(GuildLeaveEvent event) {
        try {
            Connection connection = LoadSQLDriver.loadSQLDriver();
            SQLQuery<Integer> sqlQuery = new SQLQuery<>("INSERT INTO discord_servers (discord_server_id, discord_server_name) VALUES (?, ?);") {

                @Override
                public Integer parseResult(ResultSet resultSet, int numRowsModified) {
                    return numRowsModified;
                }
            };

            sqlQuery.querySingle(connection, new String[]{String.valueOf(event.getGuild().getIdLong()), event.getGuild().getName()});

            // close connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
