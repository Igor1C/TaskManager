package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.TaskActionParamEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.helpers.DateHelper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class CreateFolderAction extends TaskActionController {

    /* CONSTRUCTORS */

    public CreateFolderAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    @Override
    public void process() {

        HashMap<ActionTypeParamsEnum, TaskActionParamEntity> paramEntities = getParamEntities();

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);

        String newFolder = getParamValue(ActionTypeParamsEnum.NEW_FOLDER);

        String finalFolder = destinationFolder + newFolder;
        setParamValue(ActionTypeParamsEnum.FINAL_FOLDER, destinationFolder + newFolder);
        new File(finalFolder).mkdir();

    }

}
