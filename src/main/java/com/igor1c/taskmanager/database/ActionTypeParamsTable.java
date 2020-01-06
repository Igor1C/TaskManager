package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.ActionTypeParamEntity;
import com.igor1c.taskmanager.entities.BaseEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionTypeParamsTable extends TableController<ActionTypeParamEntity> {

    public ActionTypeParamsTable() {

        super(  "actionTypeParams",
                new String[]{"name", "description"});

    }

    public void createTable() {

        String query =  "CREATE TABLE actionTypeParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        HashMap<Integer, ActionTypeParamEntity> predefinedMap = ActionTypeParamEntity.getPredefinedMap();
        for (ActionTypeParamEntity actionTypeParamEntity : predefinedMap.values())
            insert(actionTypeParamEntity);

    }

    public ArrayList<BaseEntity> selectByActionTypeId(long id) {

        String query =  "SELECT\n" +
                        "   actionTypeParams.id,\n" +
                        "   actionTypeParams.name,\n" +
                        "   actionTypeParams.description\n" +
                        "FROM\n" +
                        "   paramRelations\n" +
                        "JOIN\n" +
                        "   actionTypeParams\n" +
                        "       ON\n" +
                        "           paramRelations.actionTypeParam = actionTypeParams.id\n" +
                        "WHERE\n" +
                        "   paramRelations.actionType=" + id;

        return executeDbPreparedStatementProcess(query);

    }

}
