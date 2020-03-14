package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.database.ActionTypeParamsTable;
import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.database.ParamRelationsTable;
import com.igor1c.taskmanager.database.TaskActionsTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskActionParamEntity extends BaseEntity {

    private long taskAction;
    private long actionTypeParam;
    private String paramValue;
    private boolean useExtraParam;
    private Long extraParamTaskAction;
    private Long extraParamType;

    private boolean autoGeneration;
    private int indexInTaskAction;
    private String actionTypeParamDescription;



    /* CONSTRUCTORS */

    public TaskActionParamEntity() {}

    public TaskActionParamEntity(long taskAction, long actionTypeParam, String paramValue, boolean useExtraParam, Long extraParamTaskAction, Long extraParamType) {

        setActionTypeParam(actionTypeParam);
        setTaskAction(taskAction);
        setParamValue(paramValue);
        setUseExtraParam(useExtraParam);
        setExtraParamTaskAction(extraParamTaskAction);
        setExtraParamType(extraParamType);

    }

    public TaskActionParamEntity(long taskAction, long actionTypeParam, String paramValue, boolean useExtraParam, Long extraParamTaskAction, Long extraParamType, boolean autoGeneration) {

        this(taskAction, actionTypeParam, paramValue, useExtraParam, extraParamTaskAction, extraParamType);
        setAutoGeneration(autoGeneration);

    }

    public TaskActionParamEntity(long id, long taskAction, long actionTypeParam, String paramValue, boolean useExtraParam, Long extraParamTaskAction, Long extraParamType) {

        this(taskAction, actionTypeParam, paramValue, useExtraParam, extraParamTaskAction, extraParamType);
        setId(id);

    }



    /* METHODS OF THE PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setActionTypeParam(resultSet.getLong(resultSet.findColumn("actionTypeParam")));
            setTaskAction(resultSet.getLong(resultSet.findColumn("taskAction")));
            setParamValue(resultSet.getString(resultSet.findColumn("paramValue")));
            setUseExtraParam(resultSet.getBoolean(resultSet.findColumn("useExtraParam")));
            setExtraParamTaskAction(resultSet.getLong(resultSet.findColumn("extraParamTaskAction")));
            setExtraParamType(resultSet.getLong(resultSet.findColumn("extraParamType")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public long getActionTypeParam() {
        return actionTypeParam;
    }

    public ActionTypeParamsEnum getActionTypeParamEnum() {

        long actionTypeParamId = getActionTypeParam();
        return ActionTypeParamEntity.getActionTypeParamsEnumMap().get(actionTypeParamId);

    }

    public void setActionTypeParam(long actionTypeParam) {
        this.actionTypeParam = actionTypeParam;
    }

    public long getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(long taskAction) {
        this.taskAction = taskAction;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }



    /* GETTERS & SETTERS OF THE CLASS FIELDS */

    public boolean isAutoGeneration() {
        return autoGeneration;
    }

    public void setAutoGeneration(boolean autoGeneration) {
        this.autoGeneration = autoGeneration;
    }

    public int getIndexInTaskAction() {
        return indexInTaskAction;
    }

    public void setIndexInTaskAction(int indexInTaskAction) {
        this.indexInTaskAction = indexInTaskAction;
    }

    public String getActionTypeParamDescription() {
        return actionTypeParamDescription;
    }

    public void setActionTypeParamDescription(String actionTypeParamDescription) {
        this.actionTypeParamDescription = actionTypeParamDescription;
    }

    public boolean isUseExtraParam() {
        return useExtraParam;
    }

    public void setUseExtraParam(boolean useExtraParam) {
        this.useExtraParam = useExtraParam;
    }

    public Long getExtraParamTaskAction() {
        return extraParamTaskAction;
    }

    public void setExtraParamTaskAction(Long extraParamTaskAction) {

        if (extraParamTaskAction == null
                || extraParamTaskAction == 0)
            this.extraParamTaskAction = null;
        else
            this.extraParamTaskAction = extraParamTaskAction;

    }

    public Long getExtraParamType() {
        return extraParamType;
    }

    public ActionTypeParamsEnum getExtraParamTypeEnum() {

        Long extraParamTypeId = getExtraParamType();

        if (extraParamTypeId == null) {
            return null;
        } else {
            return ActionTypeParamEntity.getActionTypeParamsEnumMap().get(extraParamTypeId);
        }

    }

    public void setExtraParamType(Long extraParamType) {

        if (extraParamType == null
                || extraParamType == 0)
            this.extraParamType = null;
        else
            this.extraParamType = extraParamType;

    }



    /* GETTERS WITH NO FIELDS */

    public ArrayList<BaseEntity> getExtraParamActionTypes() {

        if (extraParamTaskAction != null) {
            TaskActionsTable taskActionsTable = new TaskActionsTable();
            TaskActionEntity taskActionEntity = (TaskActionEntity) taskActionsTable.selectById(extraParamTaskAction);

            ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
            ArrayList<BaseEntity> actionTypesList = actionTypeParamsTable.selectByActionTypeId(taskActionEntity.getActionType());

            return actionTypesList;
        } else {
            return null;
        }

    }

}
