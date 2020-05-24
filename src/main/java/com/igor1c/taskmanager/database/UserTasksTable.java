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

            if (userTaskScheduleEntity.getScheduleType() == 0
                    && userTaskScheduleEntity.getId() != 0) {
                userTaskSchedulesTable.deleteById(userTaskScheduleBaseEntity.getId());
            } else {
                userTaskScheduleEntity.setUserTask(id);
                userTaskSchedulesTable.insertUpdate(userTaskScheduleEntity);
            }
        }

        return id;

    }

    public void fullDelete(UserTaskEntity entity) {

        // First of all method deletes all params of all task actions.
        deleteTaskActionParams(entity);

        // Then method deletes all task actions (and task actions are already empty).
        entity.getTaskActions().clear();
        deleteUnusedTaskActions(entity);

        deleteUserTaskSchedules(entity);
        deleteById(entity.getId());

    }

    public void deleteTaskActionParams(BaseEntity baseEntity) {

        UserTaskEntity userTaskEntity = (UserTaskEntity) baseEntity;

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionsArrayList = taskActionsTable.select("userTask=" + userTaskEntity.getId());

        for (BaseEntity currentEntity : taskActionsArrayList) {
            TaskActionEntity currentTaskAction = (TaskActionEntity) currentEntity;
            taskActionsTable.deleteTaskActionParams(currentTaskAction);
        }

    }

    // The method deletes all task actions, which exist in the DB and don't exist in array of task actions.
    public void deleteUnusedTaskActions(BaseEntity baseEntity) {

        UserTaskEntity userTaskEntity = (UserTaskEntity) baseEntity;
        ArrayList<BaseEntity> currentTaskActionsArrayList = userTaskEntity.getTaskActions();

        ArrayList<Long> currentTaskActionsIdArray = EntityHelper.generateIdArray(currentTaskActionsArrayList);

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionsArrayList = taskActionsTable.select("userTask=" + userTaskEntity.getId());

        for (BaseEntity currentEntity : taskActionsArrayList) {
            TaskActionEntity currentTaskAction = (TaskActionEntity) currentEntity;
            long currentTaskActionId = currentTaskAction.getId();

            if (!currentTaskActionsIdArray.contains(currentTaskActionId))
                taskActionsTable.fullDelete(currentTaskAction);
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
