package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.IOException;

public class DeleteFolderAction extends TaskActionController {

    /* CONSTRUCTORS */

    public DeleteFolderAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        try {
            FileUtils.deleteDirectory(new File(destinationFolder));
        } catch (IOException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        }

    }

}
