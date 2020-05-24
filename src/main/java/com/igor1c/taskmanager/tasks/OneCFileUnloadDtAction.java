package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.*;

import java.io.IOException;

public class OneCFileUnloadDtAction extends TaskActionController {

    /* CONSTRUCTORS */

    public OneCFileUnloadDtAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        Runtime rt = Runtime.getRuntime();
        StringBuilder executableString = new StringBuilder();

        String executablePath = getParamValue(ActionTypeParamsEnum.EXECUTABLE_PATH);
        String dbPath = getParamValue(ActionTypeParamsEnum.ONE_C_DB_FILE_PATH);
        String user = getParamValue(ActionTypeParamsEnum.USER);
        String password = getParamValue(ActionTypeParamsEnum.PASSWORD);

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);

        String fileName;
        if (getBooleanParamValue(ActionTypeParamsEnum.ONE_C_UNLOAD_ONLY_CF)) {
            fileName = generateFileNameFromCurrentDate() + ".cf";
        } else {
            fileName = generateFileNameFromCurrentDate() + ".dt";
        }



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

        if (getBooleanParamValue(ActionTypeParamsEnum.ONE_C_UNLOAD_ONLY_CF)) {
            executableString.append("/DumpCfg \"");
        } else {
            executableString.append("/DumpIB \"");
        }

        executableString.append(destinationFolder)
                        .append(fileName)
                        .append("\"");

        try {
            rt.exec(executableString.toString());
        } catch (IOException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        }

    }

}
