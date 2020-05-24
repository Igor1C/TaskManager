package com.igor1c.taskmanager.controllers.requests;

public class SaveTaskActionParamRequest extends IdNameRequest {

    long taskAction;
    String paramValue;
    boolean booleanParamValue;
    boolean useExtraParam;
    Long extraParamTaskAction;
    Long extraParamType;
    int indexInTaskAction;
    int taskActionIndexInUserTask;



    /* FIELDS OF THE DATABASE */

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

    public boolean isBooleanParamValue() {
        return booleanParamValue;
    }

    public void setBooleanParamValue(boolean booleanParamValue) {
        this.booleanParamValue = booleanParamValue;
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
        this.extraParamTaskAction = extraParamTaskAction;
    }

    public Long getExtraParamType() {
        return extraParamType;
    }

    public void setExtraParamType(Long extraParamType) {
        this.extraParamType = extraParamType;
    }



    /* FIELDS OF THE ENTITY */

    public int getIndexInTaskAction() {
        return indexInTaskAction;
    }

    public void setIndexInTaskAction(int indexInTaskAction) {
        this.indexInTaskAction = indexInTaskAction;
    }

    public int getTaskActionIndexInUserTask() {
        return taskActionIndexInUserTask;
    }

    public void setTaskActionIndexInUserTask(int taskActionIndexInUserTask) {
        this.taskActionIndexInUserTask = taskActionIndexInUserTask;
    }

}
