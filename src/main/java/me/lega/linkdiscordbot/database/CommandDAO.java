package me.lega.linkdiscordbot.database;

import io.github.cdimascio.dotenv.Dotenv;
import me.lega.linkdiscordbot.classes.Command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CommandDAO {

    public CommandDAO() {

    }

    /**
     * Get all commands from the database
     *
     * @return Array of commands
     */
    public Command[] getAllCommands() {

        Dotenv dotenv = Dotenv.configure().load();
        Command[] commands = null;

        try {

            // Load JDBC Driver
            Class.forName(dotenv.get("JDBC_DRIVER"));

            // Open connection to database
            Connection conn = DriverManager.getConnection(dotenv.get("DB_URI"), dotenv.get("SQLUser"), dotenv.get("SQLPassword"));

            // SQL query string
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String getCommandQuery = "SELECT * FROM commands ORDER BY command ASC;";

            // Execute SQL query
            ResultSet rs = stmt.executeQuery(getCommandQuery);

            rs.last();
            commands = new Command[rs.getRow()];
            rs.beforeFirst();

            while (rs.next()) {
                commands[rs.getRow() - 1] = new Command(rs.getInt("id"), rs.getString("command"), rs.getString("command_syntax"), rs.getString("command_description"));
            }

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commands;
    }
}
