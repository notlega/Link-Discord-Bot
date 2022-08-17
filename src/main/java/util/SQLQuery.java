package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class SQLQuery<T> {

    private final String query;

    public SQLQuery(String query) {
        this.query = query;
    }

    private ResultSet getResultSet(Connection connection, String[] parameters) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }

            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<T> Query(Connection connection, String[] parameters) {
        ResultSet resultSet = getResultSet(connection, parameters);

        ArrayList<T> results = new ArrayList<>();
        if (resultSet == null) {
            return null;
        }
        try {
            while (resultSet.next()) {
                results.add(ParseResult(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public T QuerySingle(Connection connection, String[] parameters) {
        ResultSet resultSet = getResultSet(connection, parameters);

        if (resultSet == null) {
            return null;
        }
        try {
            if (resultSet.next()) {
                return ParseResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<T> Query(Connection connection) {
        return Query(connection, new String[]{});
    }

    public T QuerySingle(Connection connection) {
        return QuerySingle(connection, new String[]{});
    }

    protected abstract T ParseResult(ResultSet resultSet) throws SQLException;
}
