package records;


import java.sql.ResultSet;
import java.sql.SQLException;

public record Command(int id, String command, String commandSyntax, String commandDescription) {
    public Command(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.command = resultSet.getString("command");
        this.commandSyntax = resultSet.getString("command_syntax");
        this.commandDescription = resultSet.getString("command_description");
    }
}
