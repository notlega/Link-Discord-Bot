package database;

import records.Command;
import util.SQLQuery;

import java.sql.*;

public class CommandDAO {

    public CommandDAO() {

    }

    public SQLQuery<Command> getAllCommands = new SQLQuery<>("SELECT * FROM commands ORDER BY command ASC;") {
        @Override
        protected Command ParseResult(ResultSet resultSet) throws SQLException {
            return new Command(resultSet);
        }
    };
}
