package com.igor1c.taskmanager.database;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.EntityFactory;
import com.igor1c.taskmanager.helpers.DBHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.StringHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;

public abstract class TableController<E extends BaseEntity> extends DBHelper implements TableOperations<E> {

    private String tableName;
    private ArrayList<String> tableFields;



    public TableController(String tableName, String[] fields) {

        setTableName(tableName);
        fillTableFields(fields);

    }

    private void fillTableFields(String[] fields) {

        ArrayList<String> fieldsArray = new ArrayList<>();
        for (String currentField : fields)
            fieldsArray.add(currentField);
        setTableFields(fieldsArray);

    }



    public abstract void createTable();

    public abstract void createForeignKeys();

    public abstract void createExtraConstraints();

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

    public long insert(BaseEntity baseEntity) {

        String query = "INSERT INTO " + getTableName() + " " + getFieldsString(baseEntity.getId()) + " VALUES " + getValuesString(baseEntity);
        return executeDbQuery(query);

    }

    public void update(BaseEntity baseEntity) {

        String query = "UPDATE " + getTableName() + " SET " + getFieldsString(false) + " = " + getValuesString(baseEntity, false) + " WHERE id=" + baseEntity.getId();
        executeDbQuery(query);

    }

    public long insertUpdate(BaseEntity baseEntity) {

        if (baseEntity.getId() == 0)
            return insert(baseEntity);
        else {
            update(baseEntity);
            return baseEntity.getId();
        }

    }

    public void deleteById(long id) {

        String query = "DELETE FROM " + getTableName() + " WHERE id=" + id;
        executeDbQuery(query);

    }



    protected long executeDbQuery(String query) {

        openConnection();
        long id = executeQuery(query);
        closeConnection();

        return id;

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

    private String getFieldsString(long id) {

        return getFieldsString(id != 0);

    }

    private String getFieldsString(boolean useId) {

        String resultString = "(";
        boolean firstIteration = true;

        if (useId) {
            firstIteration = false;
            resultString = resultString.concat("id");
        }

        for (String field : getTableFields()) {
            if (firstIteration)
                firstIteration = false;
            else
                resultString = resultString.concat(", ");
            resultString = resultString.concat(field);
        }

        resultString = resultString.concat(")");
        return resultString;

    }

    private String getValuesString(BaseEntity baseEntity) {

        return getValuesString(baseEntity, baseEntity.getId() != 0);

    }

    private String getValuesString(BaseEntity baseEntity, boolean useId) {

        String resultString = "(";
        BooleanHolder firstIteration = new BooleanHolder(true);

        if (useId)
            resultString = processValueStringId(baseEntity, resultString, firstIteration);

        Class clazz = baseEntity.getClass();

        for (String currentFieldName : getTableFields())
            resultString = processValueStringField(baseEntity, resultString, firstIteration, clazz, currentFieldName);

        resultString = resultString.concat(")");

        return resultString;

    }

    private String processValueStringId(BaseEntity baseEntity, String resultString, BooleanHolder firstIteration) {

        firstIteration.value = false;
        return resultString.concat("'" + baseEntity.getId() + "'");

    }

    private String processValueStringField(BaseEntity baseEntity, String resultString, BooleanHolder firstIteration, Class clazz, String currentFieldName) {

        try {

            if (firstIteration.value)
                firstIteration.value = false;
            else
                resultString = resultString.concat(", ");

            String methodName = "get" + currentFieldName.substring(0, 1).toUpperCase() + currentFieldName.substring(1);
            Method method = clazz.getMethod(methodName);
            Object currentValue = method.invoke(baseEntity);
            if (currentValue != null)
                if (currentValue.getClass() == String.class)
                    resultString = resultString.concat("'" + currentValue + "'");
                else
                    resultString = resultString.concat(currentValue.toString());

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return resultString;

    }



    protected String getTableName() {
        return tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected ArrayList<String> getTableFields() {
        return tableFields;
    }

    protected void setTableFields(ArrayList<String> tableFields) {
        this.tableFields = tableFields;
    }

}
