package com.igor1c.taskmanager.database;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.helpers.EntityHelper;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class UserTasksTable extends TableController<UserTaskEntity> {

    public UserTasksTable() {

        super(  "userTasks",
                new String[]{"name"});

    }



    // TABLE CREATION

    public void createTable() {

        String query =  "CREATE TABLE userTasks(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {}



    // CRUD

    public long fullInsertUpdate(UserTaskEntity entity) {

        long id = insertUpdate(entity);

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        for (BaseEntity taskActionEntity : entity.getTaskActions()) {
            TaskActionEntity currentTaskActionEntity = (TaskActionEntity) taskActionEntity;
            currentTaskActionEntity.setUserTask(id);
            taskActionsTable.fullInsertUpdate(currentTaskActionEntity);
        }

        return id;

    }

    public void fullDelete(UserTaskEntity entity) {

        entity.getTaskActions().clear();
        deleteUnusedTaskActions(entity);
        deleteById(entity.getId());

    }

    // The method deletes all task actions, which exist in the DB and don't exist in array of task actions.
    public void deleteUnusedTaskActions(BaseEntity baseEntity) {

        UserTaskEntity userTaskEntity = (UserTaskEntity) baseEntity;
        ArrayList<BaseEntity> currentTaskActionsArrayList = userTaskEntity.getTaskActions();

        TaskActionsTable taskActionsTable = new TaskActionsTable();

        ArrayList<Long> currentTaskActionsIdArray = EntityHelper.generateIdArray(currentTaskActionsArrayList);

        ArrayList<BaseEntity> taskActionsArrayList = taskActionsTable.select("userTask=" + userTaskEntity.getId());
        for (BaseEntity currentEntity : taskActionsArrayList) {
            TaskActionEntity currentTaskAction = (TaskActionEntity) currentEntity;
            long currentTaskActionId = currentTaskAction.getId();

            if (!currentTaskActionsIdArray.contains(currentTaskActionId))
                taskActionsTable.fullDelete(currentTaskAction);
        }

        for (BaseEntity currentEntity : currentTaskActionsArrayList) {
            TaskActionEntity currentTaskAction = (TaskActionEntity) currentEntity;
            taskActionsTable.deleteUnusedTaskActionParams(currentTaskAction);
        }

    }



    // PROCESSING OF ENTITY

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        UserTaskEntity entity = (UserTaskEntity) baseEntity;

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.selectOrder("userTask=" + entity.getId(), "taskOrder");

        for (BaseEntity taskActionEntity : taskActionEntityArrayList)
            taskActionsTable.fillEntity(taskActionEntity);

        entity.setTaskActions(taskActionEntityArrayList);
        entity.renewTaskActionIndexes();

        return entity;
    }

}
