package com.igor1c.taskmanager.entities;

import com.igor1c.taskmanager.database.TaskActionsTable;
import com.igor1c.taskmanager.database.UserTasksTable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskActionEntity extends BaseEntity {

    private String name = "";
    private long actionType;
    private long userTask;
    private long taskOrder;

    private int indexInUserTask;
    private ArrayList<BaseEntity> taskActionParams = new ArrayList<>();
    private HashMap<ActionTypeParamsEnum, TaskActionParamEntity> taskActionParamsMap;
    private HashMap<Long, Long> taskActionsMap; // Key: taskActionId, value: taskActionIndex
    private HashMap<Long, Long> taskActionsReverseMap; // Key: taskActionIndex, value: taskActionId



    /* CONSTRUCTORS */

    public TaskActionEntity() {}

    public TaskActionEntity(String name, long actionType, long userTask, long taskOrder) {

        setName(name);
        setActionType(actionType);
        setUserTask(userTask);
        setTaskOrder(taskOrder);

    }

    public TaskActionEntity(long id, String name, long actionType, long userTask, long taskOrder) {

        this(name, actionType, userTask, taskOrder);
        setId(id);

    }



    /* METHODS OF THE PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
            setActionType(resultSet.getLong(resultSet.findColumn("actionType")));
            setUserTask(resultSet.getLong(resultSet.findColumn("userTask")));
            setTaskOrder(resultSet.getLong(resultSet.findColumn("taskOrder")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void processTaskActionsMap() {

        fillTaskActionParamsMap();

        for (BaseEntity baseEntity : taskActionParams) {
            TaskActionParamEntity taskActionParamEntity = (TaskActionParamEntity) baseEntity;
            taskActionParamEntity.setTaskActionsMap(getTaskActionsMap());
            taskActionParamEntity.setTaskActionsReverseMap(getTaskActionsReverseMap());
        }

    }



    /* JSON */

    @Override
    public void insertUpdateFromJsonObject(JSONObject jsonObject) {

        setName(jsonObject.getString("name"));
        setActionType(ActionTypeEntity
                        .getActionTypesEnumReverseMap()
                        .get(ActionTypesEnum
                                .getValueFromString(jsonObject.getString("actionType"))));
        setTaskOrder(jsonObject.getLong("taskOrder"));

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        setId(taskActionsTable.insertUpdate(this));

        JSONArray taskActionParamJsonArray = jsonObject.getJSONArray("taskActionParams");
        for (int i = 0; i < taskActionParamJsonArray.length(); i++) {
            JSONObject taskActionParamJsonObject = taskActionParamJsonArray.getJSONObject(i);

            TaskActionParamEntity taskActionParamEntity = new TaskActionParamEntity();
            taskActionParamEntity.setTaskAction(getId());
            taskActionParamEntity.setTaskActionsMap(getTaskActionsMap());
            taskActionParamEntity.setTaskActionsReverseMap(getTaskActionsReverseMap());
            taskActionParamEntity.insertUpdateFromJsonObject(taskActionParamJsonObject);

            getTaskActionParams().add(taskActionParamEntity);
        }

    }

    @Override
    public JSONObject toJsonObject() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("name", getName());
        jsonObject.put("actionType", getActionTypeEnum());
        jsonObject.put("taskOrder", getTaskOrder());
        jsonObject.put("indexInUserTask", getIndexInUserTask());
        jsonObject.put("taskActionParams", arrayToJsonArray(getTaskActionParams()));

        return jsonObject;

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getActionType() {
        return actionType;
    }

    public String getActionTypeName() {

        long currentActionType = getActionType();
        if (currentActionType != 0)
            return ActionTypeEntity.getActionTypeEntity(currentActionType).getName();
        else
            return "";

    }

    public ActionTypesEnum getActionTypeEnum() {

        long currentActionType = getActionType();
        return ActionTypeEntity.getActionTypesEnumMap().get(currentActionType);

    }

    public String getActionTypeDescription() {

        long currentActionType = getActionType();
        if (currentActionType != 0)
            return ActionTypeEntity.getActionTypeEntity(currentActionType).getDescription();
        else
            return "";

    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public long getUserTask() {
        return userTask;
    }

    public void setUserTask(long userTask) {
        this.userTask = userTask;
    }

    public long getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(long taskOrder) {
        this.taskOrder = taskOrder;
    }



    /* GETTERS & SETTERS OF THE CLASS FIELDS */

    public int getIndexInUserTask() {
        return indexInUserTask;
    }

    public void setIndexInUserTask(int indexInUserTask) {
        this.indexInUserTask = indexInUserTask;
    }

    public ArrayList<BaseEntity> getTaskActionParams() {
        return taskActionParams;
    }

    public void setTaskActionParams(ArrayList<BaseEntity> taskActionParams) {

        this.taskActionParams = taskActionParams;
        processTaskActionsMap();

    }

    public HashMap<ActionTypeParamsEnum, TaskActionParamEntity> getTaskActionParamsMap() {
        return taskActionParamsMap;
    }

    private void fillTaskActionParamsMap() {

        taskActionParamsMap = new HashMap<>();
        for (BaseEntity taskActionParamBaseEntity : taskActionParams) {
            TaskActionParamEntity taskActionParamEntity = (TaskActionParamEntity) taskActionParamBaseEntity;
            taskActionParamsMap.put(taskActionParamEntity.getActionTypeParamEnum(), taskActionParamEntity);
        }

    }

    public HashMap<Long, Long> getTaskActionsMap() {
        return taskActionsMap;
    }

    public void setTaskActionsMap(HashMap<Long, Long> taskActionsMap) {
        this.taskActionsMap = taskActionsMap;
    }

    public void setTaskActionParamsMap(HashMap<ActionTypeParamsEnum, TaskActionParamEntity> taskActionParamsMap) {
        this.taskActionParamsMap = taskActionParamsMap;
    }

    public HashMap<Long, Long> getTaskActionsReverseMap() {
        return taskActionsReverseMap;
    }

    public void setTaskActionsReverseMap(HashMap<Long, Long> taskActionsReverseMap) {
        this.taskActionsReverseMap = taskActionsReverseMap;
    }

}
