package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.ActionTypeParamsEnum;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipPackAction extends TaskActionController {

    /* CONSTRUCTORS */

    public ZipPackAction(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {
        super(userTaskEntity, taskActionEntity);
    }



    /* MAIN FUNCTIONAL */

    public void process() {

        String sourceFolder = getParamValue(ActionTypeParamsEnum.SOURCE_FOLDER);
        File fileToZip = new File(sourceFolder);

        String destinationFolder = getParamValue(ActionTypeParamsEnum.DESTINATION_FOLDER);
        destinationFolder = appendBackslash(destinationFolder);
        String destinationFile = generateFileNameFromCurrentDate() + ".zip";

        setParamValue(ActionTypeParamsEnum.DESTINATION_FILE, destinationFile);

        String zipPath = destinationFolder + destinationFile;
        try (FileOutputStream fos = new FileOutputStream(zipPath);
                ZipOutputStream zos = new ZipOutputStream(fos)) {

            zipFile(fileToZip, fileToZip.getName(), zos);
        } catch (FileNotFoundException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        } catch (IOException e) {
            setSuccessfulExecution(false);
            e.printStackTrace();
        }

    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {

        if (fileToZip.isDirectory()) {
            fileName = appendBackslash(fileName);

            File[] childFiles = fileToZip.listFiles();
            for (File childFile : childFiles) {
                zipFile(childFile, fileName + childFile.getName(), zos);
            }

        } else {

            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
        }

    }

}
