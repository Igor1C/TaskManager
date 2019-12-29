package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.EntityFactory;
import com.igor1c.taskmanager.helpers.DBHelper;

import java.sql.*;
import java.util.ArrayList;

public abstract class TableController<E extends BaseEntity> extends DBHelper implements TableOperations<E> {

    private String tableName;



    public TableController(String tableName) {
        setTableName(tableName);
    }



    public abstract void createTable();

    public abstract void createForeignKeys();

    public abstract void createExtraConstraints();



    protected void executeDbQuery(String query) {

        openConnection();
        executeQuery(query);
        closeConnection();

    }

    protected ResultSet executeDbPreparedStatement(String query) {

        openConnection();
        ResultSet resultSet = executePreparedStatement(query);
        closeConnection();

        return resultSet;

    }



    public ArrayList<BaseEntity> select() {
        return select("");
    }

    public ArrayList<BaseEntity> select(String whereConditions) {

        openConnection();

        String query =  "SELECT\n" +
                        "   *\n" +
                        "FROM\n" +
                        "   " + getTableName();

        if (whereConditions.length() > 0)
            query = query + "\n" +
                            "WHERE\n" +
                            "   " + whereConditions;

        ArrayList<BaseEntity> entitiesArrayList = new ArrayList<>();

        ResultSet resultSet = executePreparedStatement(query);
        try {
            while (resultSet.next()) {
                BaseEntity entity = EntityFactory.createEntity(tableName);
                entity.fillFromResultSet(resultSet);
                entitiesArrayList.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

        return entitiesArrayList;

    }



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
