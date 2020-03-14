package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ActionTypeEntity extends BaseEntity {

    public static final int ONE_C_FILE_UNLOAD_DT = 1;
    public static final int ONE_C_SERVER_UNLOAD_DT = 2;
    public static final int SEVEN_Z_PACK_ARCHIVE = 3;
    public static final int FTP_UPLOAD_FILE = 4;
    public static final int WAIT_FOR_FILE = 5;
    public static final int WAIT_TIME = 6;
    public static final int CREATE_FOLDER = 7;
    public static final int DELETE_FOLDER = 8;

    private static HashMap<Long, ActionTypeEntity> ACTION_TYPE_ENTITIES_MAP;
    private static HashMap<Long, ActionTypesEnum> ACTION_TYPES_ENUM_MAP;
    private static HashMap<ActionTypesEnum, Long> ACTION_TYPES_ENUM_REVERSE_MAP;

    static {
        initActionTypeEntitiesMap();
        initActionTypesEnumMaps();
    }

    private String name;
    private String description;

    private ArrayList<BaseEntity> actionTypeParams = new ArrayList<>();



    /* STATIC MAPS */

    public static void initActionTypeEntitiesMap() {

        ACTION_TYPE_ENTITIES_MAP = new HashMap<>();

        ArrayList<ActionTypeEntity> actionTypeEntities = new ArrayList<ActionTypeEntity>();
        actionTypeEntities.add(new ActionTypeEntity(ONE_C_FILE_UNLOAD_DT, "ONE_C_FILE_UNLOAD_DT", "1C file database - Unload DT"));
        actionTypeEntities.add(new ActionTypeEntity(ONE_C_SERVER_UNLOAD_DT, "ONE_C_SERVER_UNLOAD_DT", "1C server database - Unload DT"));
        actionTypeEntities.add(new ActionTypeEntity(SEVEN_Z_PACK_ARCHIVE, "SEVEN_Z_PACK_ARCHIVE", "7Z - Make archive"));
        actionTypeEntities.add(new ActionTypeEntity(FTP_UPLOAD_FILE, "FTP_UPLOAD_FILE", "FTP - Upload file"));
        actionTypeEntities.add(new ActionTypeEntity(WAIT_FOR_FILE, "WAIT_FOR_FILE", "Wait for file"));
        actionTypeEntities.add(new ActionTypeEntity(WAIT_TIME, "WAIT_TIME", "Wait time"));
        actionTypeEntities.add(new ActionTypeEntity(CREATE_FOLDER, "CREATE_FOLDER", "Create folder"));
        actionTypeEntities.add(new ActionTypeEntity(DELETE_FOLDER, "DELETE_FOLDER", "Delete folder"));

        for (ActionTypeEntity actionTypeEntity : actionTypeEntities)
            ACTION_TYPE_ENTITIES_MAP.put(actionTypeEntity.getId(), actionTypeEntity);

    }

    public static void initActionTypesEnumMaps() {

        ArrayList<Integer> actionTypesInt = new ArrayList<>(
                Arrays.asList(
                        ONE_C_FILE_UNLOAD_DT,
                        ONE_C_SERVER_UNLOAD_DT,
                        SEVEN_Z_PACK_ARCHIVE,
                        FTP_UPLOAD_FILE,
                        WAIT_FOR_FILE,
                        WAIT_TIME,
                        CREATE_FOLDER,
                        DELETE_FOLDER
                )
        );

        ArrayList<ActionTypesEnum> actionTypesEnum = new ArrayList<>(
                Arrays.asList(
                        ActionTypesEnum.ONE_C_FILE_UNLOAD_DT,
                        ActionTypesEnum.ONE_C_SERVER_UNLOAD_DT,
                        ActionTypesEnum.SEVEN_Z_PACK_ARCHIVE,
                        ActionTypesEnum.FTP_UPLOAD_FILE,
                        ActionTypesEnum.WAIT_FOR_FILE,
                        ActionTypesEnum.WAIT_TIME,
                        ActionTypesEnum.CREATE_FOLDER,
                        ActionTypesEnum.DELETE_FOLDER
                )
        );

        ACTION_TYPES_ENUM_MAP = new HashMap<>();
        ACTION_TYPES_ENUM_REVERSE_MAP = new HashMap<>();
        for (int i = 0; i < actionTypesInt.size(); i++) {
            long curLong = actionTypesInt.get(i);
            ActionTypesEnum curEnum = actionTypesEnum.get(i);

            ACTION_TYPES_ENUM_MAP.put(curLong, curEnum);
            ACTION_TYPES_ENUM_REVERSE_MAP.put(curEnum, curLong);
        }

    }

    public static HashMap<Long, ActionTypeEntity> getActionTypeEntitiesMap() {
        return ACTION_TYPE_ENTITIES_MAP;
    }

    public static HashMap<Long, ActionTypesEnum> getActionTypesEnumMap() {
        return ACTION_TYPES_ENUM_MAP;
    }

    public static HashMap<ActionTypesEnum, Long> getActionTypesEnumReverseMap() {
        return ACTION_TYPES_ENUM_REVERSE_MAP;
    }

    public static ActionTypeEntity getActionTypeEntity(long id) {
        return getActionTypeEntitiesMap().get(id);
    }



    /* CONSTRUCTORS */

    public ActionTypeEntity() {}

    public ActionTypeEntity(long id, String name, String description) {

        setId(id);
        setName(name);
        setDescription(description);

    }



    /* FUNCTIONAL */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
            setDescription(resultSet.getString(resultSet.findColumn("description")));
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

    public ArrayList<BaseEntity> getActionTypeParams() {
        return actionTypeParams;
    }

    public void setActionTypeParams(ArrayList<BaseEntity> actionTypeParams) {
        this.actionTypeParams = actionTypeParams;
    }

}
