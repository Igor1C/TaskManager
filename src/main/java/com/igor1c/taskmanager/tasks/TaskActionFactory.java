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
            case ZIP_PACK_ARCHIVE:
                return new ZipPackAction(userTaskEntity, taskActionEntity);
            case FTP_UPLOAD_FILE:
                return new FtpUploadFileAction(userTaskEntity, taskActionEntity);
            case WAIT_FOR_FILE:
                return new WaitForFileAction(userTaskEntity, taskActionEntity);
            case WAIT_TIME:
                return new WaitTimeAction(userTaskEntity, taskActionEntity);
            case CREATE_FOLDER:
                return new CreateFolderAction(userTaskEntity, taskActionEntity);
            case DELETE_FOLDER:
                return new DeleteFolderAction(userTaskEntity, taskActionEntity);
        }

        return null;

    }

}
