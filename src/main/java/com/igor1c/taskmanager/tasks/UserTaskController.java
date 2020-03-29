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



    /* CONSTRUCTORS */

    public UserTaskController(long id) {

        UserTasksTable userTasksTable = new UserTasksTable();
        UserTaskEntity userTaskEntity = (UserTaskEntity) userTasksTable.selectById(id);
        userTasksTable.fillEntity(userTaskEntity);

        setUserTaskEntity(userTaskEntity);

    }

    public UserTaskController(UserTaskEntity userTaskEntity) {
        setUserTaskEntity(userTaskEntity);
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
                                                                                        true);
        userTaskExecutionsTable.insert(userTaskExecutionEntity);

    }



    /* GETTERS & SETTERS */

    public UserTaskEntity getUserTaskEntity() {
        return userTaskEntity;
    }

    public void setUserTaskEntity(UserTaskEntity userTaskEntity) {
        this.userTaskEntity = userTaskEntity;
    }

}
