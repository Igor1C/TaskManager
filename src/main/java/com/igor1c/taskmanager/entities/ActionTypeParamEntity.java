package com.igor1c.taskmanager.entities;

import org.omg.CORBA.TIMEOUT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ActionTypeParamEntity extends BaseEntity {

    public static final int EXECUTABLE_PATH = 1;
    public static final int USER = 2;
    public static final int PASSWORD = 3;
    public static final int SOURCE_FOLDER = 4;
    public static final int SOURCE_FILE = 5;
    public static final int DESTINATION_FOLDER = 6;
    public static final int DESTINATION_FILE = 7;
    public static final int NEW_FOLDER = 8;
    public static final int FINAL_FOLDER = 9;
    public static final int ONE_C_DB_FILE_PATH = 10;
    public static final int ONE_C_DB_SERVER_SRV = 11;
    public static final int ONE_C_DB_SERVER_REF = 12;
    public static final int ONE_C_UNLOAD_ONLY_CF = 13;
    public static final int FTP_ADDRESS = 14;
    public static final int PORT = 15;
    public static final int TIMEOUT = 16;
    public static final int FILE_NAME_PREFIX = 17;

    private static HashMap<Long, ActionTypeParamEntity> ACTION_TYPE_PARAM_ENTITIES_MAP;
    private static HashMap<Long, ActionTypeParamsEnum> ACTION_TYPE_PARAMS_ENUM_MAP;
    private static HashMap<ActionTypeParamsEnum, Long> ACTION_TYPE_PARAMS_ENUM_REVERSE_MAP;

    static {
        initActionTypeParamEntitiesMap();
        initActionTypeParamsEnumMaps();
    }

    private String name;
    private String description;
    private boolean booleanType;



    /* STATIC MAPS */

    public static void initActionTypeParamEntitiesMap() {

        ACTION_TYPE_PARAM_ENTITIES_MAP = new HashMap<>();

        ArrayList<ActionTypeParamEntity> actionTypeParamEntities = new ArrayList<ActionTypeParamEntity>();
        actionTypeParamEntities.add(new ActionTypeParamEntity(EXECUTABLE_PATH, "EXECUTABLE_PATH", "Executable path"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(USER, "USER", "Username"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(PASSWORD, "PASSWORD", "Password"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(SOURCE_FOLDER, "SOURCE_FOLDER", "Source folder"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(SOURCE_FILE, "SOURCE_FILE", "Source file"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(DESTINATION_FOLDER, "DESTINATION_FOLDER", "Destination folder"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(DESTINATION_FILE, "DESTINATION_FILE", "Destination file"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(NEW_FOLDER, "NEW_FOLDER", "New folder"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(FINAL_FOLDER, "FINAL_FOLDER", "Final folder"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_FILE_PATH, "ONE_C_DB_FILE_PATH", "1C file database - Path"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_SRV, "ONE_C_DB_SERVER_SRV", "1C server database - Server name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_REF, "ONE_C_DB_SERVER_REF", "1C server database - Database name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_UNLOAD_ONLY_CF, "ONE_C_UNLOAD_ONLY_CF", "Unload only CF", true));
        actionTypeParamEntities.add(new ActionTypeParamEntity(FTP_ADDRESS, "FTP_ADDRESS", "FTP address"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(PORT, "PORT", "Port"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(TIMEOUT, "TIMEOUT", "Timeout"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(FILE_NAME_PREFIX, "FILE_NAME_PREFIX", "File name prefix"));

        for (ActionTypeParamEntity actionTypeParamEntity : actionTypeParamEntities)
            ACTION_TYPE_PARAM_ENTITIES_MAP.put(actionTypeParamEntity.getId(), actionTypeParamEntity);

    }

    public static void initActionTypeParamsEnumMaps() {

        ArrayList<Integer> actionTypeParamsInt = new ArrayList<>(
                Arrays.asList(
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
                        FILE_NAME_PREFIX));

        ArrayList<ActionTypeParamsEnum> actionTypeParamsEnum = new ArrayList<>(
                Arrays.asList(
                        ActionTypeParamsEnum.EXECUTABLE_PATH,
                        ActionTypeParamsEnum.USER,
                        ActionTypeParamsEnum.PASSWORD,
                        ActionTypeParamsEnum.SOURCE_FOLDER,
                        ActionTypeParamsEnum.SOURCE_FILE,
                        ActionTypeParamsEnum.DESTINATION_FOLDER,
                        ActionTypeParamsEnum.DESTINATION_FILE,
                        ActionTypeParamsEnum.NEW_FOLDER,
                        ActionTypeParamsEnum.FINAL_FOLDER,
                        ActionTypeParamsEnum.ONE_C_DB_FILE_PATH,
                        ActionTypeParamsEnum.ONE_C_DB_SERVER_SRV,
                        ActionTypeParamsEnum.ONE_C_DB_SERVER_REF,
                        ActionTypeParamsEnum.ONE_C_UNLOAD_ONLY_CF,
                        ActionTypeParamsEnum.FTP_ADDRESS,
                        ActionTypeParamsEnum.PORT,
                        ActionTypeParamsEnum.TIMEOUT,
                        ActionTypeParamsEnum.FILE_NAME_PREFIX
                )
        );

        ACTION_TYPE_PARAMS_ENUM_MAP = new HashMap<>();
        ACTION_TYPE_PARAMS_ENUM_REVERSE_MAP = new HashMap<>();
        for (int i = 0; i < actionTypeParamsInt.size(); i++) {
            long curLong = actionTypeParamsInt.get(i);
            ActionTypeParamsEnum curEnum = actionTypeParamsEnum.get(i);

            ACTION_TYPE_PARAMS_ENUM_MAP.put(curLong, curEnum);
            ACTION_TYPE_PARAMS_ENUM_REVERSE_MAP.put(curEnum, curLong);
        }

    }

    public static HashMap<Long, ActionTypeParamEntity> getActionTypeParamEntitiesMap() {
        return ACTION_TYPE_PARAM_ENTITIES_MAP;
    }

    public static HashMap<Long, ActionTypeParamsEnum> getActionTypeParamsEnumMap() {
        return ACTION_TYPE_PARAMS_ENUM_MAP;
    }

    public static HashMap<ActionTypeParamsEnum, Long> getActionTypeParamsEnumReverseMap() {
        return ACTION_TYPE_PARAMS_ENUM_REVERSE_MAP;
    }



    /* CONSTRUCTORS */

    public ActionTypeParamEntity() {}

    public ActionTypeParamEntity(long id, String name, String description) {

        setId(id);
        setName(name);
        setDescription(description);

    }

    public ActionTypeParamEntity(long id, String name, String description, boolean booleanType) {

        this(id, name, description);
        setBooleanType(booleanType);

    }



    /* FUNCTIONAL */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
            setDescription(resultSet.getString(resultSet.findColumn("description")));
            setBooleanType(resultSet.getBoolean(resultSet.findColumn("booleanType")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /* GETTERS & SETTERS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBooleanType() {
        return booleanType;
    }

    public void setBooleanType(boolean booleanType) {
        this.booleanType = booleanType;
    }

}
