package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.*;
import com.igor1c.taskmanager.helpers.DateHelper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class OneCFileUnloadDtAction extends TaskActionController {

    /* CONSTRUCTORS */

    public OneCFileUnloadDtAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        HashMap<ActionTypeParamsEnum, TaskActionParamEntity> paramEntities = getParamEntities();

        Runtime rt = Runtime.getRuntime();
        StringBuilder executableString = new StringBuilder();

        String executablePath = getParamValue(ActionTypeParamsEnum.EXECUTABLE_PATH);
        String dbPath = getParamValue(ActionTypeParamsEnum.ONE_C_DB_FILE_PATH);
        String user = getParamValue(ActionTypeParamsEnum.USER);
        String password = getParamValue(ActionTypeParamsEnum.PASSWORD);

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);

        String dtFileName = DateHelper.dateToString(DateHelper.sdf_yyyycMMcdd_hhhmmhsscS, new Date()) + ".dt";



        executableString.append("\"")
                        .append(executablePath)
                        .append("\" ");

        executableString.append("CONFIG /F \"")
                        .append(dbPath)
                        .append("\" ");

        executableString.append("/N \"")
                        .append(user)
                        .append("\" ");

        executableString.append("/P \"")
                        .append(password)
                        .append("\" ");

        executableString.append("/DumpIB \"")
                        .append(destinationFolder)
                        .append(dtFileName)
                        .append("\"");

        try {
            rt.exec(executableString.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
