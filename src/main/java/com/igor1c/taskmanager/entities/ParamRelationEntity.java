package com.igor1c.taskmanager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParamRelationEntity extends BaseEntity {

    private long actionType;
    private long actionTypeParam;
    private boolean autoGeneration;



    /* CONSTRUCTORS */

    public ParamRelationEntity() {}

    public ParamRelationEntity(long actionType, long actionTypeParam, boolean autoGeneration) {

        setActionType(actionType);
        setActionTypeParam(actionTypeParam);
        setAutoGeneration(autoGeneration);

    }

    public ParamRelationEntity(long id, long actionType, long actionTypeParam, boolean autoGeneration) {

        this(actionType, actionTypeParam, autoGeneration);
        setId(id);

    }



    /* METHODS OF THE PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setActionType(resultSet.getLong(resultSet.findColumn("actionType")));
            setActionTypeParam(resultSet.getLong(resultSet.findColumn("actionTypeParam")));
            setAutoGeneration(resultSet.getBoolean(resultSet.findColumn("autoGeneration")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public long getActionType() {
        return actionType;
    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public long getActionTypeParam() {
        return actionTypeParam;
    }

    public void setActionTypeParam(long actionTypeParam) {
        this.actionTypeParam = actionTypeParam;
    }

    public boolean isAutoGeneration() {
        return autoGeneration;
    }

    public void setAutoGeneration(boolean autoGeneration) {
        this.autoGeneration = autoGeneration;
    }

}
