package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.*;
import com.igor1c.taskmanager.helpers.EntityHelper;

import java.util.ArrayList;

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

        UserTaskSchedulesTable userTaskSchedulesTable = new UserTaskSchedulesTable();
        for (BaseEntity userTaskScheduleBaseEntity : entity.getUserTaskSchedules()) {
            UserTaskScheduleEntity userTaskScheduleEntity = (UserTaskScheduleEntity) userTaskScheduleBaseEntity;
            userTaskScheduleEntity.setUserTask(id);
            userTaskSchedulesTable.insertUpdate(userTaskScheduleEntity);
        }

        return id;

    }

    public void fullDelete(UserTaskEntity entity) {

        entity.getTaskActions().clear();
        deleteUnusedTaskActions(entity);
        deleteUserTaskSchedules(entity);
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

    public void deleteUserTaskSchedules(BaseEntity baseEntity) {

        UserTaskEntity userTaskEntity = (UserTaskEntity) baseEntity;

        UserTaskSchedulesTable userTaskSchedulesTable = new UserTaskSchedulesTable();
        for (BaseEntity userTaskSchedule : userTaskEntity.getUserTaskSchedules()) {
            long userTaskScheduleId = userTaskSchedule.getId();
            if (userTaskScheduleId != 0) {
                userTaskSchedulesTable.deleteById(userTaskScheduleId);
            }
        }

    }



    // PROCESSING OF ENTITY

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        UserTaskEntity entity = (UserTaskEntity) baseEntity;

        fillEntityWithTaskActions(entity);
        fillEntityWithUserTaskSchedules(entity);
        fillEntityWithUserTaskExecutions(entity);

        return entity;
    }

    private void fillEntityWithTaskActions(UserTaskEntity userTaskEntity) {

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.selectOrder("userTask=" + userTaskEntity.getId(), "taskOrder");

        for (BaseEntity taskActionEntity : taskActionEntityArrayList)
            taskActionsTable.fillEntity(taskActionEntity);

        userTaskEntity.setTaskActions(taskActionEntityArrayList);
        userTaskEntity.renewTaskActionIndexes();

    }

    private void fillEntityWithUserTaskSchedules(UserTaskEntity userTaskEntity) {

        UserTaskSchedulesTable userTaskSchedulesTable = new UserTaskSchedulesTable();
        ArrayList<BaseEntity> userTaskSchedulesEntityArrayList = userTaskSchedulesTable.selectOrder("userTask=" + userTaskEntity.getId(), "id");
        userTaskEntity.setUserTaskSchedules(userTaskSchedulesEntityArrayList);

    }

    public static void fillEntityWithUserTaskExecutions(UserTaskEntity userTaskEntity) {

        UserTaskExecutionsTable userTaskExecutionTable = new UserTaskExecutionsTable();
        ArrayList<BaseEntity> userTaskExecutionsEntityArrayList = userTaskExecutionTable.selectOrder("userTask=" + userTaskEntity.getId(), "id desc");
        userTaskEntity.setUserTaskExecutions(userTaskExecutionsEntityArrayList);

    }

}
