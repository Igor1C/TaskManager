package com.igor1c.taskmanager.entities;

public enum ActionTypeParamsEnum {

    EXECUTABLE_PATH,
    USER,
    PASSWORD,
    SOURCE_FOLDER,
    SOURCE_FILE,
    DESTINATION_FOLDER,
    DESTINATION_FILE,
    NEW_FOLDER,
    FINAL_FOLDER,
    ONE_C_DB_FILE_PATH,
    ONE_C_DB_SERVER_SRV,
    ONE_C_DB_SERVER_REF,
    ONE_C_UNLOAD_ONLY_CF,
    FTP_ADDRESS,
    PORT,
    TIMEOUT,
    FILE_NAME_PREFIX;

    public static ActionTypeParamsEnum getValueFromString(String valueString) {

        if (valueString.equals("EXECUTABLE_PATH")) {
            return EXECUTABLE_PATH;
        } else if (valueString.equals("USER")) {
            return USER;
        } else if (valueString.equals("PASSWORD")) {
            return PASSWORD;
        } else if (valueString.equals("SOURCE_FOLDER")) {
            return SOURCE_FOLDER;
        } else if (valueString.equals("SOURCE_FILE")) {
            return SOURCE_FILE;
        } else if (valueString.equals("DESTINATION_FOLDER")) {
            return DESTINATION_FOLDER;
        } else if (valueString.equals("DESTINATION_FILE")) {
            return DESTINATION_FILE;
        } else if (valueString.equals("NEW_FOLDER")) {
            return NEW_FOLDER;
        } else if (valueString.equals("FINAL_FOLDER")) {
            return FINAL_FOLDER;
        } else if (valueString.equals("ONE_C_DB_FILE_PATH")) {
            return ONE_C_DB_FILE_PATH;
        } else if (valueString.equals("ONE_C_DB_SERVER_SRV")) {
            return ONE_C_DB_SERVER_SRV;
        } else if (valueString.equals("ONE_C_DB_SERVER_REF")) {
            return ONE_C_DB_SERVER_REF;
        } else if (valueString.equals("ONE_C_UNLOAD_ONLY_CF")) {
            return ONE_C_UNLOAD_ONLY_CF;
        } else if (valueString.equals("FTP_ADDRESS")) {
            return FTP_ADDRESS;
        } else if (valueString.equals("PORT")) {
            return PORT;
        } else if (valueString.equals("TIMEOUT")) {
            return TIMEOUT;
        } else if (valueString.equals("FILE_NAME_PREFIX")) {
            return FILE_NAME_PREFIX;
        }

        return null;

    }

}
