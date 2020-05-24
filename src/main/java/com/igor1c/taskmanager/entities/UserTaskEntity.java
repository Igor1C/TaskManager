package com.igor1c.taskmanager.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.igor1c.taskmanager.database.UserTasksTable;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserTaskEntity extends BaseEntity {

    private String name;

    private ArrayList<BaseEntity> taskActions = new ArrayList<>();
    private ArrayList<BaseEntity> userTaskSchedules = new ArrayList<>();
    private ArrayList<BaseEntity> userTaskExecutions = new ArrayList<>();

    private HashMap<Long, Long> taskActionsMap; // Key: taskActionId, value: taskActionIndex
    private HashMap<Long, Long> taskActionsReverseMap; // Key: taskActionIndex, value: taskActionId



    /* CONSTRUCTORS */

    public UserTaskEntity() {}

    public UserTaskEntity(String name) {
        setName(name);
    }

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    /* METHODS OF PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {

        try {
            setId(resultSet.getLong(resultSet.findColumn("id")));
            setName(resultSet.getString(resultSet.findColumn("name")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void renewTaskActionIndexes() {

        for (int i = 0; i < taskActions.size(); i++) {
            TaskActionEntity taskActionEntity = (TaskActionEntity) taskActions.get(i);
            taskActionEntity.setIndexInUserTask(i);
            taskActionEntity.setTaskOrder(i + 1);
        }

    }

    public void processTaskActionsMap() {

        fillTaskActionsMap();

        for (BaseEntity baseEntity : taskActions) {
            TaskActionEntity taskActionEntity = (TaskActionEntity) baseEntity;
            taskActionEntity.setTaskActionsMap(getTaskActionsMap());
            taskActionEntity.setTaskActionsReverseMap(getTaskActionsReverseMap());
            taskActionEntity.processTaskActionsMap();
        }

    }



    /* JSON */

    @Override
    public void insertUpdateFromJsonObject(JSONObject jsonObject) {

        UserTasksTable userTasksTable = new UserTasksTable();

        setName(jsonObject.getString("name"));
        setId(userTasksTable.fullInsertUpdate(this));

        JSONArray taskActionJsonArray = jsonObject.getJSONArray("taskActions");
        for (int i = 0; i < taskActionJsonArray.length(); i++) {
            JSONObject taskActionJsonObject = taskActionJsonArray.getJSONObject(i);

            TaskActionEntity taskActionEntity = new TaskActionEntity();
            taskActionEntity.setUserTask(getId());
            taskActionEntity.setTaskActionsMap(getTaskActionsMap());
            taskActionEntity.setTaskActionsReverseMap(getTaskActionsReverseMap());
            taskActionEntity.insertUpdateFromJsonObject(taskActionJsonObject);

            getTaskActions().add(taskActionEntity);
            fillTaskActionsMap();
        }

        setUserTaskSchedules(BaseEntity.jsonArrayToArray(   jsonObject.getJSONArray("userTaskSchedules"),
                                                            EntityFactory.USER_TASK_SCHEDULES));
        for (BaseEntity baseEntity : getUserTaskSchedules()) {
            ((UserTaskScheduleEntity) baseEntity).setUserTask(getId());
        }
        userTasksTable.fullInsertUpdate(this);

    }

    @Override
    public JSONObject toJsonObject() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("name", getName());
        jsonObject.put("taskActions", arrayToJsonArray(getTaskActions()));
        jsonObject.put("userTaskSchedules", arrayToJsonArray(getUserTaskSchedules()));

        return jsonObject;

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    /* GETTERS & SETTERS OF THE CLASS FIELDS */

    public ArrayList<BaseEntity> getTaskActions() {
        return taskActions;
    }

    public int getTaskActionsSize() {
        return getTaskActions().size();
    }

    public void setTaskActions(ArrayList<BaseEntity> taskActions) {

        this.taskActions = taskActions;
        processTaskActionsMap();

    }

    public ArrayList<BaseEntity> getUserTaskSchedules() {
        return userTaskSchedules;
    }

    public void setUserTaskSchedules(ArrayList<BaseEntity> userTaskSchedules) {
        this.userTaskSchedules = userTaskSchedules;
    }

    public ArrayList<BaseEntity> getUserTaskExecutions() {
        return userTaskExecutions;
    }

    public void setUserTaskExecutions(ArrayList<BaseEntity> userTaskExecutions) {
        this.userTaskExecutions = userTaskExecutions;
    }

    public UserTaskExecutionEntity getLastExecution() {

        if (userTaskExecutions.size() > 0) {
            return (UserTaskExecutionEntity) userTaskExecutions.get(0);
        } else {
            return null;
        }

    }

    public String getLastExecutionString() {

        UserTaskExecutionEntity userTaskExecutionEntity = getLastExecution();
        if (userTaskExecutionEntity == null) {
            return "";
        } else {
            return userTaskExecutionEntity.getExecutionDateString();
        }

    }

    public String getLastExecutionStringWithoutFillingEntity() {

        UserTasksTable.fillEntityWithUserTaskExecutions(this);
        return getLastExecutionString();

    }

    public HashMap<Long, Long> getTaskActionsMap() {
        return taskActionsMap;
    }

    public HashMap<Long, Long> getTaskActionsReverseMap() {
        return taskActionsReverseMap;
    }

    private void fillTaskActionsMap() {

        taskActionsMap = new HashMap<>();
        taskActionsReverseMap = new HashMap<>();

        long l = 0;
        for (BaseEntity baseEntity : taskActions) {
            taskActionsMap.put(baseEntity.getId(), l);
            taskActionsReverseMap.put(l, baseEntity.getId());
            l++;
        }

    }



    /* GETTERS WITH NO FIELDS */

    public ArrayList<BaseEntity> getSavedTaskActions() {

        ArrayList<BaseEntity> taskActions = getTaskActions();
        ArrayList<BaseEntity> savedTaskActions = new ArrayList<>();

        for (BaseEntity taskAction : taskActions) {
            if (taskAction.getId() != 0)
                savedTaskActions.add(taskAction);
        }

        return savedTaskActions;

    }

    public UserTaskScheduleEntity getFirstUserTaskSchedule() {

        if (userTaskSchedules.size() == 0) {
            userTaskSchedules.add(new UserTaskScheduleEntity());
        }

        return (UserTaskScheduleEntity) userTaskSchedules.get(0);

    }

    public void setFirstUserTaskSchedule(UserTaskScheduleEntity userTaskScheduleEntity) {

        if (userTaskSchedules.size() > 0) {
            userTaskSchedules.set(0, userTaskScheduleEntity);
        }

    }

}
