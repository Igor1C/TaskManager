package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionTypeParamEntity extends BaseEntity {

    public static final int ONE_C_DB_FILE_PATH = 1;
    public static final int ONE_C_DB_SERVER_SRV = 2;
    public static final int ONE_C_DB_SERVER_REF = 3;
    public static final int USER = 4;
    public static final int PASSWORD = 5;
    public static final int DESTINATION_FOLDER = 6;
    public static final int FTP_ADDRESS = 7;
    public static final int PORT = 7;

    private static HashMap<Integer, ActionTypeParamEntity> PREDEFINED_MAP;

    static {
        initPredefinedMap();
    }

    private String name;
    private String description;



    public static void initPredefinedMap() {

        PREDEFINED_MAP = new HashMap<>();

        ArrayList<ActionTypeParamEntity> actionTypeParamEntities = new ArrayList<ActionTypeParamEntity>();
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_FILE_PATH, "ONE_C_DB_FILE_PATH", "1C file database - Path"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_SRV, "ONE_C_DB_SERVER_SRV", "1C server database - Server name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_REF, "ONE_C_DB_SERVER_REF", "1C server database - Database name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(USER, "USER", "Username"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(PASSWORD, "PASSWORD", "Password"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(DESTINATION_FOLDER, "DESTINATION_FOLDER", "1C database - backup folder"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(FTP_ADDRESS, "FTP_ADDRESS", "FTP address"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(PORT, "PORT", "Port"));

        for (ActionTypeParamEntity actionTypeParamEntity : actionTypeParamEntities)
            PREDEFINED_MAP.put((int) actionTypeParamEntity.getId(), actionTypeParamEntity);

    }



    public ActionTypeParamEntity() {}

    public ActionTypeParamEntity(long id, String name, String description) {

        setId(id);
        setName(name);
        setDescription(description);

    }



    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
            setDescription(resultSet.getString(resultSet.findColumn("description")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



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

    public static HashMap<Integer, ActionTypeParamEntity> getPredefinedMap() {
        return PREDEFINED_MAP;
    }

}
