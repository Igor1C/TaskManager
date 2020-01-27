package com.igor1c.taskmanager.database;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.entities.*;
import com.igor1c.taskmanager.helpers.EntityHelper;

import java.util.ArrayList;

public class TaskActionsTable extends TableController<TaskActionEntity> {

    public TaskActionsTable() {

        super(  "taskActions",
                new String[]{"name", "actionType", "userTask", "taskOrder"});

    }



    // TABLE CREATION

    public void createTable() {

        String query =  "CREATE TABLE taskActions(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   actionType BIGINT NOT NULL,\n" +
                        "   userTask BIGINT NOT NULL,\n" +
                        "   taskOrder BIGINT\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE taskActions\n" +
                        "   ADD FOREIGN KEY (actionType)\n" +
                        "   REFERENCES actionTypes(id);\n" +
                        "ALTER TABLE taskActions\n" +
                        "   ADD FOREIGN KEY (userTask)\n" +
                        "   REFERENCES userTasks(id);";

        executeDbQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}



    // CRUD

    public long fullInsertUpdate(TaskActionEntity entity) {

        long id = insertUpdate(entity);

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();
        for (BaseEntity taskActionParamEntity : entity.getTaskActionParams()) {
            TaskActionParamEntity currentTaskActionParamEntity = (TaskActionParamEntity) taskActionParamEntity;
            currentTaskActionParamEntity.setTaskAction(id);
            taskActionParamsTable.insertUpdate(currentTaskActionParamEntity);
        }

        return id;

    }

    public void fullDelete(TaskActionEntity entity) {

        entity.getTaskActionParams().clear();
        deleteUnusedTaskActionParams(entity);
        deleteById(entity.getId());

    }

    public void deleteUnusedTaskActionParams(BaseEntity baseEntity) {

        TaskActionEntity taskActionEntity = (TaskActionEntity) baseEntity;
        ArrayList<BaseEntity> currentTaskActionParamsArrayList = taskActionEntity.getTaskActionParams();

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();

        ArrayList<Long> currentTaskActionParamsIdArray = EntityHelper.generateIdArray(currentTaskActionParamsArrayList);

        ArrayList<BaseEntity> taskActionParamsArrayList = taskActionParamsTable.select("taskAction=" + taskActionEntity.getId());
        for (BaseEntity currentTaskActionParam : taskActionParamsArrayList) {
            long currentTaskActionParamId = currentTaskActionParam.getId();
            if (!currentTaskActionParamsIdArray.contains(currentTaskActionParamId))
                taskActionParamsTable.deleteById(currentTaskActionParamId);
        }

    }



    // PROCESSING OF ENTITY

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        TaskActionEntity entity = (TaskActionEntity) baseEntity;

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();
        ArrayList<BaseEntity> taskActionParamEntityArrayList = taskActionParamsTable.select("taskAction=" + entity.getId());
        int currentIndex = 0;
        for (BaseEntity taskActionParamEntity : taskActionParamEntityArrayList) {
            ((TaskActionParamEntity) taskActionParamEntity).setIndexInTaskAction(currentIndex);
            currentIndex++;
        }

        entity.setTaskActionParams(taskActionParamEntityArrayList);

        return entity;

    }

    public BaseEntity initWithBlankActionTypeParams(BaseEntity baseEntity) {

        TaskActionEntity entity = (TaskActionEntity) baseEntity;

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ArrayList<BaseEntity> actionTypeParamEntityArrayList = actionTypeParamsTable.selectByActionTypeId(entity.getActionType());

        ArrayList<BaseEntity> taskActionParamEntityArrayList = entity.getTaskActionParams();
        taskActionParamEntityArrayList.clear();

        int currentIndex = 0;
        for (BaseEntity actionTypeParamEntity : actionTypeParamEntityArrayList) {
            ActionTypeParamEntity currentActionTypeParamEntity = (ActionTypeParamEntity) actionTypeParamEntity;

            TaskActionParamEntity taskActionParamEntity = new TaskActionParamEntity(currentActionTypeParamEntity.getName(),
                    entity.getId(),
                    "");
            taskActionParamEntityArrayList.add(taskActionParamEntity);
            taskActionParamEntity.setIndexInTaskAction(currentIndex);
            currentIndex++;
        }

        return entity;

    }

}
