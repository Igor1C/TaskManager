package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.ActionTypeEntity;
import com.igor1c.taskmanager.entities.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionTypesTable extends TableController<ActionTypeEntity> {

    public ActionTypesTable() {

        super(  "actionTypes",
                new String[]{"name", "description"});

    }



    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        HashMap<Long, ActionTypeEntity> predefinedMap = ActionTypeEntity.getActionTypeEntitiesMap();
        for (ActionTypeEntity actionTypeEntity : predefinedMap.values())
            insert(actionTypeEntity);

    }



    public BaseEntity fillEntity(BaseEntity baseEntity) {

        ActionTypeEntity entity = (ActionTypeEntity) baseEntity;

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ArrayList<BaseEntity> actionTypeParamsEntityArrayList = actionTypeParamsTable.selectByActionTypeId(entity.getId());

        entity.setActionTypeParams(actionTypeParamsEntityArrayList);

        return entity;
    }

}
