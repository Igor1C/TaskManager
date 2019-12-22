package com.igor1c.taskmanager;

import com.igor1c.database.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHelper {

    public static final String DB_URL = "jdbc:h2:/C:/Java/TaskManager/db/taskManager";
    public static final String DB_DRIVER = "org.h2.Driver";

    public Connection connection;



    public void openConnection() {

        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executeQuery(String query) {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDatabase() {

        ArrayList<TableOperations> tableOperationsArray = new ArrayList<TableOperations>();
        tableOperationsArray.add(new ActionTypesTable());
        tableOperationsArray.add(new ActionTypeParamsTable());
        tableOperationsArray.add(new UserTasksTable());
        tableOperationsArray.add(new TaskActionsTable());
        tableOperationsArray.add(new TaskActionParamsTable());
        tableOperationsArray.add(new ParamRelationsTable());

        for (TableOperations tableOperations : tableOperationsArray) {
            tableOperations.createTable();
            tableOperations.createForeignKeys();
            tableOperations.createExtraConstraints();
            tableOperations.fillTable();
        }

    }

}
