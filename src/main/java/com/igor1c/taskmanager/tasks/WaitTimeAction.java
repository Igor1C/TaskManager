package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;

public class WaitTimeAction extends TaskActionController {

    /* CONSTRUCTORS */

    public WaitTimeAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String timeoutString = getParamValue(ActionTypeParamsEnum.TIMEOUT);
        Long timeout = Long.parseLong(timeoutString) * 1000;

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        }

    }

}
