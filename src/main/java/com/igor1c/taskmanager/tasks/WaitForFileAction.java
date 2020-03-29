package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.helpers.DateHelper;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class WaitForFileAction extends TaskActionController {

    long SLEEP_DURATION = 1_000L;

    /* CONSTRUCTORS */

    public WaitForFileAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);

        String destinationFile = getParamValue(ActionTypeParamsEnum.DESTINATION_FILE);

        String timeoutString = getParamValue(ActionTypeParamsEnum.TIMEOUT);
        int timeoutSeconds = Integer.parseInt(timeoutString);

        Date dateTimeCurrent = new Date();
        Date dateTimeEnd = DateHelper.dateAdd(dateTimeCurrent, Calendar.SECOND, timeoutSeconds);

        File file = new File(destinationFolder + destinationFile);
        while (dateTimeEnd.compareTo(dateTimeCurrent) > 0
                && !file.exists()) {

            try {
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                setSuccessfulExecution(false);
                e.printStackTrace();
            }
        }


    }

}
