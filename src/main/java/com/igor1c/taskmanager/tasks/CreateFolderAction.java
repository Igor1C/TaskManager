package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;

import java.io.File;

public class CreateFolderAction extends TaskActionController {

    /* CONSTRUCTORS */

    public CreateFolderAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);

        String newFolder = getParamValue(ActionTypeParamsEnum.NEW_FOLDER);

        String finalFolder = destinationFolder + newFolder;
        setParamValue(ActionTypeParamsEnum.FINAL_FOLDER, destinationFolder + newFolder);
        new File(finalFolder).mkdir();

    }

}
