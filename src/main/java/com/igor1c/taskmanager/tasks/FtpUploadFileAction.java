package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import java.io.*;

public class FtpUploadFileAction extends TaskActionController {

    /* CONSTRUCTORS */

    public FtpUploadFileAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String sourceFolder = getParamValue(ActionTypeParamsEnum.SOURCE_FOLDER);
        sourceFolder = appendBackslash(sourceFolder);
        String sourceFile = getParamValue(ActionTypeParamsEnum.SOURCE_FILE);
        String filePath = sourceFolder + sourceFile;

        String ftpAddress = getParamValue(ActionTypeParamsEnum.FTP_ADDRESS);
        int ftpPort = Integer.parseInt(getParamValue(ActionTypeParamsEnum.PORT));
        String ftpUser = getParamValue(ActionTypeParamsEnum.USER);
        String ftpPassword = getParamValue(ActionTypeParamsEnum.PASSWORD);
        String ftpFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        String ftpPath = ftpFolder + "/" + sourceFile;

        FTPClientConfig ftpClientConfig = new FTPClientConfig();
        FTPClient ftpClient = new FTPClient();
        ftpClient.setUseEPSVwithIPv4(true);
        ftpClient.configure(ftpClientConfig);

        try (FileInputStream fis = new FileInputStream(filePath)) {

            ftpClient.connect(ftpAddress, ftpPort);
            ftpClient.login(ftpUser, ftpPassword);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(ftpPath, fis);

        } catch (FileNotFoundException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        } catch (IOException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();

        } finally {

            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
