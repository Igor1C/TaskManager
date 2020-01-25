package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.*;

import java.util.ArrayList;

public class TaskActionsTable extends TableController<TaskActionEntity> {

    public TaskActionsTable() {

        super(  "taskActions",
                new String[]{"name", "actionType", "userTask", "taskOrder"});

    }

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



    public BaseEntity fillEntity(BaseEntity baseEntity) {

        TaskActionEntity entity = (TaskActionEntity) baseEntity;

        TaskActionParamsTable taskActionParamsTable = new TaskActionParamsTable();
        ArrayList<BaseEntity> taskActionParamEntityArrayList = taskActionParamsTable.select("taskAction=" + entity.getId());

        entity.setTaskActionParams(taskActionParamEntityArrayList);

        return entity;

    }

    public BaseEntity initWithBlankActionTypeParams(BaseEntity baseEntity) {

        TaskActionEntity entity = (TaskActionEntity) baseEntity;

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ArrayList<BaseEntity> actionTypeParamEntityArrayList = actionTypeParamsTable.selectByActionTypeId(entity.getActionType());

        ArrayList<BaseEntity> taskActionParamEntityArrayList = entity.getTaskActionParams();
        taskActionParamEntityArrayList.clear();

        for (BaseEntity actionTypeParamEntity : actionTypeParamEntityArrayList) {
            ActionTypeParamEntity currentActionTypeParamEntity = (ActionTypeParamEntity) actionTypeParamEntity;

            TaskActionParamEntity taskActionParamEntity = new TaskActionParamEntity(currentActionTypeParamEntity.getName(),
                                                                                    currentActionTypeParamEntity.getId(),
                                                                                    entity.getId());
            taskActionParamEntityArrayList.add(taskActionParamEntity);
        }

        return entity;

    }

}
