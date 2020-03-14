package com.igor1c.taskmanager.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.controllers.requests.*;
import com.igor1c.taskmanager.controllers.responses.BaseEntityListResponse;
import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.database.TaskActionsTable;
import com.igor1c.taskmanager.database.UserTasksTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.TaskActionEntity;
import com.igor1c.taskmanager.entities.TaskActionParamEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.tasks.UserTaskController;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@Scope(value = "session")
public class MainController {

    private UserTaskEntity userTaskEntity;

    @GetMapping("")
    public String main() {

        return "main.html";

    }



    /* USER TASKS */

    @PostMapping("/addUserTask")
    public ResponseEntity<?> addUserTask() {

        userTaskEntity = new UserTaskEntity();
        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/getUserTasks")
    public ResponseEntity<?> getUserTasks() {

        UserTasksTable userTasksTable = new UserTasksTable();
        ArrayList<BaseEntity> entityArrayList = userTasksTable.select();

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        for (BaseEntity entity : entityArrayList) {
            ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.select("userTask=" + entity.getId());
            ((UserTaskEntity) entity).setTaskActions(taskActionEntityArrayList);
        }

        BaseEntityListResponse baseEntityListResponse = new BaseEntityListResponse();
        baseEntityListResponse.setBaseEntityList(entityArrayList);

        return ResponseEntity.ok(baseEntityListResponse);

    }

    @PostMapping("/getUserTask")
    public ResponseEntity<?> getUserTask(@RequestBody IdRequest idRequest) {

        UserTasksTable table = new UserTasksTable();
        userTaskEntity = (UserTaskEntity) table.selectById(idRequest.getId());
        table.fillEntity(userTaskEntity);

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/getUserTaskFromSession")
    public ResponseEntity<?> getUserTaskFromSession() {

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/saveUserTask")
    public ResponseEntity<?> saveUserTask(@RequestBody IdNameRequest idNameRequest) {

        userTaskEntity.setName(idNameRequest.getName());

        UserTasksTable table = new UserTasksTable();
        table.fullInsertUpdate(userTaskEntity);
        table.deleteUnusedTaskActions(userTaskEntity);

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/cancelUserTask")
    public ResponseEntity<?> cancelUserTask() {

        userTaskEntity = null;

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/deleteUserTask")
    public ResponseEntity<?> deleteUserTask(@RequestBody IdRequest idRequest) {

        UserTasksTable table = new UserTasksTable();
        table.fullDelete(userTaskEntity);

        userTaskEntity = null;

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/modifyUserTaskEntity")
    public ResponseEntity<?> modifyUserTaskEntity(@RequestBody IdNameRequest idNameRequest) {

        userTaskEntity.setName(idNameRequest.getName());

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/processUserTask")
    public ResponseEntity<?> processUserTask(@RequestBody IdRequest idRequest) {

        UserTaskController userTaskController = new UserTaskController(idRequest.getId());
        userTaskController.processUserTask();

        return ResponseEntity.ok(new String());

    }



    /* TASK ACTIONS */

    @PostMapping("/addTaskAction")
    public ResponseEntity<?> addTaskAction() {

        TaskActionEntity taskActionEntity = new TaskActionEntity();

        ArrayList<BaseEntity> taskActionArrayList = userTaskEntity.getTaskActions();
        taskActionArrayList.add(taskActionEntity);

        int currentIndex = taskActionArrayList.indexOf(taskActionEntity);
        taskActionEntity.setIndexInUserTask(currentIndex);
        taskActionEntity.setTaskOrder(currentIndex + 1);

        return ResponseEntity.ok(taskActionEntity);

    }

    @PostMapping("/saveTaskAction")
    public ResponseEntity<?> saveTaskAction(@RequestBody SaveTaskActionRequest saveTaskActionRequest) {

        TaskActionEntity entity = (TaskActionEntity) userTaskEntity.getTaskActions().get(saveTaskActionRequest.getIndexInUserTask());
        entity.setName(saveTaskActionRequest.getName());
        entity.setTaskOrder(saveTaskActionRequest.getTaskOrder());
        entity.setActionType(saveTaskActionRequest.getActionType());

        return ResponseEntity.ok(entity);

    }

    @PostMapping("/deleteTaskAction")
    public ResponseEntity<?> deleteTaskAction(@RequestBody IdIndexRequest idIndexRequest) {

        userTaskEntity.getTaskActions().remove(idIndexRequest.getIndex());
        userTaskEntity.renewTaskActionIndexes();

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/moveTaskAction")
    public ResponseEntity<?> moveTaskAction(@RequestBody IdIndexMoveUpRequest idIndexMoveUpRequest) {

        int index1 = idIndexMoveUpRequest.getIndex();
        int index2 = idIndexMoveUpRequest.isMoveUp() ? index1 - 1 : index1 + 1;

        ArrayList<BaseEntity> taskActionEntities = userTaskEntity.getTaskActions();

        if (index2 < 0
                || index2 >= taskActionEntities.size())
            return ResponseEntity.ok(new String());

        Collections.swap(taskActionEntities, index1, index2);
        userTaskEntity.renewTaskActionIndexes();

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/changeActionType")
    public ResponseEntity<?> changeActionType(@RequestBody IdIndexActionTypeRequest idIndexActionTypeRequest) {

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        TaskActionEntity taskActionEntity = (TaskActionEntity) userTaskEntity.getTaskActions().get(idIndexActionTypeRequest.getIndex());
        taskActionEntity.setActionType(idIndexActionTypeRequest.getActionType());
        taskActionsTable.initWithBlankActionTypeParams(taskActionEntity);

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/getSavedTaskActions")
    public ResponseEntity<?> getSavedTaskActions() {

        ArrayList<BaseEntity> currentTaskActions = userTaskEntity.getTaskActions();

        return ResponseEntity.ok(currentTaskActions);

    }



    /* TASK ACTION PARAMS */

    @PostMapping("/saveTaskActionParam")
    public ResponseEntity<?> saveTaskActionParam(@RequestBody SaveTaskActionParamRequest saveTaskActionParamRequest) {

        TaskActionEntity taskActionEntity = (TaskActionEntity) userTaskEntity.getTaskActions().get(saveTaskActionParamRequest.getTaskActionIndexInUserTask());

        TaskActionParamEntity taskActionParamEntity = (TaskActionParamEntity) taskActionEntity.getTaskActionParams().get(saveTaskActionParamRequest.getIndexInTaskAction());
        taskActionParamEntity.setParamValue(saveTaskActionParamRequest.getParamValue());
        taskActionParamEntity.setUseExtraParam(saveTaskActionParamRequest.isUseExtraParam());
        taskActionParamEntity.setExtraParamTaskAction(saveTaskActionParamRequest.getExtraParamTaskAction());
        taskActionParamEntity.setExtraParamType(saveTaskActionParamRequest.getExtraParamType());

        return ResponseEntity.ok(taskActionEntity);

    }



    /* ACTION TYPES */

    @PostMapping("/getActionTypes")
    public ResponseEntity<?> getActionTypes() {

        ActionTypesTable table = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = table.select();

        return ResponseEntity.ok(entityArrayList);

    }

}
