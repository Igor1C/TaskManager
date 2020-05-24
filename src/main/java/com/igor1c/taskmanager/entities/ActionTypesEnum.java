package com.igor1c.taskmanager.entities;

public enum ActionTypesEnum {

    ONE_C_FILE_UNLOAD_DT,
    ONE_C_SERVER_UNLOAD_DT,
    ZIP_PACK_ARCHIVE,
    FTP_UPLOAD_FILE,
    WAIT_FOR_FILE,
    WAIT_TIME,
    CREATE_FOLDER,
    DELETE_FOLDER;

    public static ActionTypesEnum getValueFromString(String valueString) {

        if (valueString.equals("ONE_C_FILE_UNLOAD_DT")) {
            return ONE_C_FILE_UNLOAD_DT;
        } else if (valueString.equals("ONE_C_SERVER_UNLOAD_DT")) {
            return ONE_C_SERVER_UNLOAD_DT;
        } else if (valueString.equals("ZIP_PACK_ARCHIVE")) {
            return ZIP_PACK_ARCHIVE;
        } else if (valueString.equals("FTP_UPLOAD_FILE")) {
            return FTP_UPLOAD_FILE;
        } else if (valueString.equals("WAIT_FOR_FILE")) {
            return WAIT_FOR_FILE;
        } else if (valueString.equals("WAIT_TIME")) {
            return WAIT_TIME;
        } else if (valueString.equals("CREATE_FOLDER")) {
            return CREATE_FOLDER;
        } else if (valueString.equals("DELETE_FOLDER")) {
            return DELETE_FOLDER;
        }

        return null;

    }

}
