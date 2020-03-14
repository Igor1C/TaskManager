package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypesEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;

public class TaskActionFactory {

    public static TaskActionController createTaskActionController(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {

        ActionTypesEnum actionTypesEnum = taskActionEntity.getActionTypeEnum();

        switch (actionTypesEnum) {
            case ONE_C_FILE_UNLOAD_DT:
                return new OneCFileUnloadDtAction(userTaskEntity, taskActionEntity);
            case CREATE_FOLDER:
                return new CreateFolderAction(userTaskEntity, taskActionEntity);
        }

        return null;

    }

}
