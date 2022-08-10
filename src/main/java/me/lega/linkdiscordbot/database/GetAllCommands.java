package me.lega.linkdiscordbot.database;

import me.lega.linkdiscordbot.classes.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import io.github.cdimascio.dotenv.Dotenv;

public class GetAllCommands {

    public GetAllCommands() {

    }

    public Commands[] GetAllCommands() {

        Dotenv dotenv = Dotenv.configure().load();
        DBInfo dbInfo = new DBInfo();

        Commands[] commands = null;

        try {

            // Load JDBC Driver
            Class.forName(dbInfo.getJDBC_DRIVER());

            // Open connection to database
            Connection conn = DriverManager.getConnection(dbInfo.getDB_URL() + dbInfo.getDB_NAME(), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String getCommandQuery = "SELECT * FROM commands ORDER BY command;";

            // Execute SQL query
            ResultSet rs = stmt.executeQuery(getCommandQuery);

            rs.last();
            commands = new Commands[rs.getRow()];
            rs.beforeFirst();

            while (rs.next()) {
                commands[rs.getRow() - 1] = new Commands(rs.getInt("id"), rs.getString("command"), rs.getString("command_syntax"), rs.getString("command_description"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commands;
    }
}
