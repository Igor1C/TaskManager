package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;

import java.util.ArrayList;

public class UserTaskTable extends TableController<UserTaskEntity> {

    public UserTaskTable() {

        super(  "userTasks",
                new String[]{"name"});

    }



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



    public long fullInsertUpdate(UserTaskEntity entity) {

        long id = insertUpdate(entity);

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        for (BaseEntity taskActionEntity : entity.getTaskActions()) {
            ((TaskActionEntity) taskActionEntity).setUserTask(id);
            taskActionsTable.insertUpdate(taskActionEntity);
        }

        return id;

    }

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        UserTaskEntity entity = (UserTaskEntity) baseEntity;

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.selectOrder("userTask=" + entity.getId(), "taskOrder");

        entity.setTaskActions(taskActionEntityArrayList);

        return entity;
    }

}
