package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.database.UserTaskExecutionsTable;
import com.igor1c.taskmanager.database.UserTasksTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.entities.UserTaskExecutionEntity;

import java.util.ArrayList;
import java.util.Date;

public class UserTaskController {

    private UserTaskEntity userTaskEntity;
    private boolean autoExecution;



    /* CONSTRUCTORS */

    public UserTaskController(long id, boolean autoExecution) {

        UserTasksTable userTasksTable = new UserTasksTable();
        UserTaskEntity userTaskEntity = (UserTaskEntity) userTasksTable.selectById(id);
        userTasksTable.fillEntity(userTaskEntity);

        setUserTaskEntity(userTaskEntity);
        setAutoExecution(autoExecution);

    }

    public UserTaskController(UserTaskEntity userTaskEntity, boolean autoExecution) {

        setUserTaskEntity(userTaskEntity);
        setAutoExecution(autoExecution);

    }



    /* FUNCTIONAL */

    public void processUserTask() {

        UserTaskExecutionsTable userTaskExecutionsTable = new UserTaskExecutionsTable();
        boolean successfulExecution = true;

        ArrayList<BaseEntity> taskActionEntities = userTaskEntity.getTaskActions();
        for (BaseEntity baseEntity : taskActionEntities) {
            TaskActionController curTaskAction = TaskActionFactory.createTaskActionController(userTaskEntity, (TaskActionEntity) baseEntity);
            curTaskAction.process();

            if (!curTaskAction.isSuccessfulExecution()) {
                successfulExecution = false;
                break;
            }
        }

        UserTaskExecutionEntity userTaskExecutionEntity = new UserTaskExecutionEntity(  userTaskEntity.getId(),
                                                                                        new Date(),
                                                                                        successfulExecution,
                                                                                        isAutoExecution());
        userTaskExecutionsTable.insert(userTaskExecutionEntity);

    }



    /* GETTERS & SETTERS */

    public UserTaskEntity getUserTaskEntity() {
        return userTaskEntity;
    }

    public void setUserTaskEntity(UserTaskEntity userTaskEntity) {
        this.userTaskEntity = userTaskEntity;
    }

    public boolean isAutoExecution() {
        return autoExecution;
    }

    public void setAutoExecution(boolean autoExecution) {
        this.autoExecution = autoExecution;
    }

}
