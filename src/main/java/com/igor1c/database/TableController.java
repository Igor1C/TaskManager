package com.igor1c.database;

import com.igor1c.taskmanager.DBHelper;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class TableController extends DBHelper implements TableOperations {

    private String tableName;



    public TableController(String tableName) {
        setTableName(tableName);
    }



    public abstract void createTable();

    public abstract void createForeignKeys();

    public abstract void createExtraConstraints();

    public void executeQuery(String query) {

        openConnection();

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

    }



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
