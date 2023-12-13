package com.brogle.beroepsproduct4.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseExecuter {

    private final Connection connection;

    public DatabaseExecuter(Connection connection) {
        this.connection = connection;
    }

    // Select statement
    public ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }
    
    // Rest van de crud (Delete, Insert, Update)
    public int executeUpdate(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeUpdate();
    }

    // Add other methods for insert, update, delete as needed
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
