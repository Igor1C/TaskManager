package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionTypeEntity extends BaseEntity {

    public static final int ONE_C_FILE_UNLOAD_DT = 1;
    public static final int ONE_C_SERVER_UNLOAD_DT = 2;
    public static final int SEVEN_Z_PACK_ARCHIVE = 3;
    public static final int FTP_UPLOAD_FILE = 4;

    private static HashMap<Integer, ActionTypeEntity> PREDEFINED_MAP;

    static {
        initPredefinedMap();
    }

    private String name;
    private String description;



    public static void initPredefinedMap() {

        PREDEFINED_MAP = new HashMap<Integer, ActionTypeEntity>();

        ArrayList<ActionTypeEntity> actionTypeEntities = new ArrayList<ActionTypeEntity>();
        actionTypeEntities.add(new ActionTypeEntity(ONE_C_FILE_UNLOAD_DT, "ONE_C_FILE_UNLOAD_DT", "1C file database - Unload DT"));
        actionTypeEntities.add(new ActionTypeEntity(ONE_C_SERVER_UNLOAD_DT, "ONE_C_SERVER_UNLOAD_DT", "1C server database - Unload DT"));
        actionTypeEntities.add(new ActionTypeEntity(SEVEN_Z_PACK_ARCHIVE, "SEVEN_Z_PACK_ARCHIVE", "7Z - Make archive"));
        actionTypeEntities.add(new ActionTypeEntity(FTP_UPLOAD_FILE, "FTP_UPLOAD_FILE", "FTP - Upload file"));

        for (ActionTypeEntity actionTypeEntity : actionTypeEntities)
            PREDEFINED_MAP.put((int) actionTypeEntity.getId(), actionTypeEntity);

    }



    public ActionTypeEntity() {}

    public ActionTypeEntity(long id, String name, String description) {

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



    public static HashMap<Integer, ActionTypeEntity> getPredefinedMap() {
        return PREDEFINED_MAP;
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

}
