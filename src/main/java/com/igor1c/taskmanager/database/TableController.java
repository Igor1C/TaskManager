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

    protected ArrayList<BaseEntity> executeDbPreparedStatementProcess(String query) {

        openConnection();
        ResultSet resultSet = executePreparedStatement(query);
        ArrayList<BaseEntity> baseEntityArrayList = processResultSet(resultSet);
        closeConnection();

        return baseEntityArrayList;

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

        ResultSet resultSet = executePreparedStatement(query);
        ArrayList<BaseEntity> baseEntityArrayList = processResultSet(resultSet);
        closeConnection();

        return baseEntityArrayList;

    }

    public BaseEntity selectById(long id) {

        ArrayList<BaseEntity> baseEntityArrayList = select("id=" + id);
        if (baseEntityArrayList.isEmpty())
            return null;
        else
            return baseEntityArrayList.get(0);

    }

    protected ArrayList<BaseEntity> processResultSet(ResultSet resultSet) {

        ArrayList<BaseEntity> entitiesArrayList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                BaseEntity entity = EntityFactory.createEntity(tableName);
                entity.fillFromResultSet(resultSet);
                entitiesArrayList.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entitiesArrayList;

    }



    protected String getTableName() {
        return tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
