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



    /* TABLE CREATION */

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



    /* CRUD */

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

    private void deleteUnusedTaskActionParams(BaseEntity baseEntity) {

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



    /* PROCESSING OF ENTITY */

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        TaskActionEntity taskActionEntity = (TaskActionEntity) baseEntity;

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();
        ArrayList<BaseEntity> taskActionParamEntityArrayList = taskActionParamsTable.select("taskAction=" + taskActionEntity.getId());

        int currentIndex = 0;
        for (BaseEntity taskActionParamBaseEntity : taskActionParamEntityArrayList) {
            TaskActionParamEntity taskActionParamEntity = (TaskActionParamEntity) taskActionParamBaseEntity;
            taskActionParamEntity.setIndexInTaskAction(currentIndex);
            taskActionParamsTable.fillEntity(taskActionParamEntity);

            fillAutoGeneration(taskActionEntity, taskActionParamEntity);

            currentIndex++;
        }

        taskActionEntity.setTaskActionParams(taskActionParamEntityArrayList);

        return taskActionEntity;

    }

    public void fillAutoGeneration(TaskActionEntity taskActionEntity, TaskActionParamEntity taskActionParamEntity) {

        ParamRelationsTable paramRelationsTable = new ParamRelationsTable();

        ArrayList<BaseEntity> paramRelationsArray = paramRelationsTable.selectByActionTypeAndActionTypeParam(
                taskActionEntity.getActionType(),
                taskActionParamEntity.getActionTypeParam());

        if (paramRelationsArray.size() > 0) {
            ParamRelationEntity paramRelationEntity = (ParamRelationEntity) paramRelationsArray.get(0);
            taskActionParamEntity.setAutoGeneration(paramRelationEntity.isAutoGeneration());
        } else {
            taskActionParamEntity.setAutoGeneration(false);
        }

    }

    public BaseEntity initWithBlankActionTypeParams(BaseEntity baseEntity) {

        TaskActionEntity taskActionEntity = (TaskActionEntity) baseEntity;

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ArrayList<BaseEntity> actionTypeParamEntityArrayList = actionTypeParamsTable.selectByActionTypeId(taskActionEntity.getActionType());

        ArrayList<BaseEntity> taskActionParamEntityArrayList = taskActionEntity.getTaskActionParams();
        taskActionParamEntityArrayList.clear();

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();

        int currentIndex = 0;
        for (BaseEntity actionTypeParamBaseEntity : actionTypeParamEntityArrayList) {
            ActionTypeParamEntity actionTypeParamEntity = (ActionTypeParamEntity) actionTypeParamBaseEntity;

            TaskActionParamEntity taskActionParamEntity = new TaskActionParamEntity(taskActionEntity.getId(),
                                                                                    actionTypeParamEntity.getId(),
                                                                                    "",
                                                                                    false,
                                                                                    null,
                                                                                    null);
            taskActionParamEntityArrayList.add(taskActionParamEntity);
            taskActionParamEntity.setIndexInTaskAction(currentIndex);
            taskActionParamsTable.fillEntity(taskActionParamEntity);

            fillAutoGeneration(taskActionEntity, taskActionParamEntity);

            currentIndex++;
        }

        return taskActionEntity;

    }

}
