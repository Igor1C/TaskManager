package com.igor1c.taskmanager.helpers;

import java.io.File;
import java.sql.*;

import com.igor1c.taskmanager.database.*;

import java.util.ArrayList;

public class DBHelper {

    private static String DB_URL;

    private static Connection staticConnection;

    private Connection currentConnection;

    static {
        init();
    }



    /* INITIALIZATION */

    public static void init() {
        DB_URL = PropertiesHelper.getDbPath();
    }


    /* DATABASE CREATION */

    public static void createDatabase() {
        if (new File(DB_URL).exists())
            return;

        ArrayList<TableOperations> tableOperationsArray = new ArrayList<TableOperations>();
        tableOperationsArray.add(new ActionTypesTable());
        tableOperationsArray.add(new ActionTypeParamsTable());
        tableOperationsArray.add(new UserTasksTable());
        tableOperationsArray.add(new TaskActionsTable());
        tableOperationsArray.add(new TaskActionParamsTable());
        tableOperationsArray.add(new ParamRelationsTable());
        tableOperationsArray.add(new UserTaskSchedulesTable());
        tableOperationsArray.add(new UserTaskExecutionsTable());

        processTableOperations(tableOperationsArray);
    }

    private static void processTableOperations(ArrayList<TableOperations> tableOperationsArray) {
        for (TableOperations tableOperations : tableOperationsArray) {
            tableOperations.createTable();
            tableOperations.createForeignKeys();
            tableOperations.createExtraConstraints();
            tableOperations.fillTable();
        }
    }



    /* CONNECTION */

    public void openCurrentConnection() {
        if (!ifStaticConnectionAvailable()) {
            currentConnection = openConnection();
        }
    }

    public void closeCurrentConnection() {
        if (!ifStaticConnectionAvailable()) {
            try {
                currentConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openStaticConnection() {
        if (!ifStaticConnectionAvailable()) {
            staticConnection = openConnection();
        }
    }

    public static void closeStaticConnection() {
        if (ifStaticConnectionAvailable()) {
            closeConnection(staticConnection);
        }
    }

    private static Connection openConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        if (ifStaticConnectionAvailable()) {
            return staticConnection;
        } else {
            return currentConnection;
        }
    }

    private static boolean ifStaticConnectionAvailable() {
        try {
            if (staticConnection == null
                    || staticConnection.isClosed()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }



    /* QUERIES */

    public long executeQuery(String query) {
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                return generatedKeys.getLong(1);
            else
                return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ResultSet executePreparedStatement(String query) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

}
