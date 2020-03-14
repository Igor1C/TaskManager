package com.igor1c.taskmanager.tasks;

import com.igor1c.taskmanager.entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class TaskActionController implements TaskActionProcessing {

    private UserTaskEntity userTaskEntity;
    private TaskActionEntity taskActionEntity;
    private HashMap<ActionTypeParamsEnum, TaskActionParamEntity> paramEntities;
    private HashMap<Long, TaskActionEntity> taskActionsMap;



    /* CONSTRUCTORS */

    public TaskActionController() {}

    public TaskActionController(UserTaskEntity userTaskEntity, TaskActionEntity taskActionEntity) {

        setUserTaskEntity(userTaskEntity);
        setTaskActionEntity(taskActionEntity);

    }



    /* MAIN FUNCTIONAL */

    public abstract void process();



    /* AUXILIARY FUNCTIONAL */

    protected String appendBackslash(String inputString) {

        if (!inputString.substring(inputString.length() - 1).equals("\\"))
            inputString += "\\";
        return inputString;

    }



    /* GETTERS & SETTERS */

    public UserTaskEntity getUserTaskEntity() {
        return userTaskEntity;
    }

    public void setUserTaskEntity(UserTaskEntity userTaskEntity) {

        this.userTaskEntity = userTaskEntity;
        fillTaskActionsMap();

    }

    public TaskActionEntity getTaskActionEntity() {
        return taskActionEntity;
    }

    public void setTaskActionEntity(TaskActionEntity taskActionEntity) {

        this.taskActionEntity = taskActionEntity;
        processTaskActionParamsToHashMap(taskActionEntity.getTaskActionParams());

    }

    public HashMap<ActionTypeParamsEnum, TaskActionParamEntity> getParamEntities() {
        return paramEntities;
    }

    private void processTaskActionParamsToHashMap(ArrayList<BaseEntity> taskActionParamEntities) {

        paramEntities = new HashMap<>();
        HashMap<Long, ActionTypeParamsEnum> actionTypeParamsEnumMap = ActionTypeParamEntity.getActionTypeParamsEnumMap();
        for (BaseEntity baseEntity : taskActionParamEntities) {
            TaskActionParamEntity taskActionParamEntity = (TaskActionParamEntity) baseEntity;
            paramEntities.put(  actionTypeParamsEnumMap.get(taskActionParamEntity.getActionTypeParam()),
                                taskActionParamEntity);
        }

    }

    private void fillTaskActionsMap() {

        taskActionsMap = new HashMap<>();
        for (BaseEntity taskActionBaseEntity : userTaskEntity.getTaskActions()) {
            taskActionsMap.put(taskActionBaseEntity.getId(), (TaskActionEntity) taskActionBaseEntity);
        }

    }

    protected String getParamValue(ActionTypeParamsEnum actionTypeParamsEnum) {

        TaskActionParamEntity taskActionParamEntity = paramEntities.get(actionTypeParamsEnum);
        if (taskActionParamEntity.isUseExtraParam()) {
            long taskActionId = taskActionParamEntity.getExtraParamTaskAction();
            TaskActionEntity taskActionEntity = taskActionsMap.get(taskActionId);

            ActionTypeParamsEnum extraParamType = taskActionParamEntity.getExtraParamTypeEnum();
            TaskActionParamEntity extraTaskActionParamEntity = taskActionEntity.getTaskActionParamsMap().get(extraParamType);
            return extraTaskActionParamEntity.getParamValue();
        } else {
            return taskActionParamEntity.getParamValue();
        }

    }

    protected void setParamValue(ActionTypeParamsEnum actionTypeParamsEnum, String paramValue) {

        TaskActionParamEntity taskActionParamEntity = paramEntities.get(actionTypeParamsEnum);
        taskActionParamEntity.setParamValue(paramValue);

    }

}
