package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionTypeParamEntity extends BaseEntity {

    public static final int ONE_C_DB_FILE_PATH = 1;
    public static final int ONE_C_DB_SERVER_SRV = 2;
    public static final int ONE_C_DB_SERVER_REF = 3;
    public static final int ONE_C_DB_USER = 4;
    public static final int ONE_C_DB_PASSWORD = 5;

    private static HashMap<Integer, ActionTypeParamEntity> PREDEFINED_MAP;

    static {
        initPredefinedMap();
    }

    private String name;
    private String description;



    public static void initPredefinedMap() {

        PREDEFINED_MAP = new HashMap<Integer, ActionTypeParamEntity>();

        ArrayList<ActionTypeParamEntity> actionTypeParamEntities = new ArrayList<ActionTypeParamEntity>();
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_FILE_PATH, "ONE_C_DB_FILE_PATH", "1C file database - Path"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_SRV, "ONE_C_DB_SERVER_SRV", "1C server database - Server name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_SERVER_REF, "ONE_C_DB_SERVER_REF", "1C server database - Database name"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_USER, "ONE_C_DB_USER", "1C database - Username"));
        actionTypeParamEntities.add(new ActionTypeParamEntity(ONE_C_DB_PASSWORD, "ONE_C_DB_PASSWORD", "1C database - Password"));

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
