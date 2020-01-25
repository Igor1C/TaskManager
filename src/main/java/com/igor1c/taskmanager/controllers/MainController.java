package com.igor1c.taskmanager.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.controllers.requests.*;
import com.igor1c.taskmanager.controllers.responses.BaseEntityListResponse;
import com.igor1c.taskmanager.database.ActionTypeParamsTable;
import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.database.TaskActionsTable;
import com.igor1c.taskmanager.database.UserTaskTable;
import com.igor1c.taskmanager.entities.*;
import javafx.concurrent.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@Scope("session")
public class MainController {

    UserTaskEntity userTaskEntity;

    @GetMapping("")
    public String main(Model model) {

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

        UserTaskTable userTasksTable = new UserTaskTable();
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

        UserTaskTable table = new UserTaskTable();
        userTaskEntity = (UserTaskEntity) table.selectById(idRequest.getId());
        table.fillEntity(userTaskEntity);

        /*TaskActionsTable taskActionsTable = new TaskActionsTable();

        ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.selectOrder("userTask=" + idRequest.getId(), "taskOrder");
        ActionTypesTable actionTypesTable = new ActionTypesTable();
        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        for (BaseEntity taskActionEntity : taskActionEntityArrayList) {
            TaskActionEntity currentTaskActonEntity = (TaskActionEntity) taskActionEntity;

            ActionTypeEntity actionTypeEntity = (ActionTypeEntity) actionTypesTable.selectById(currentTaskActonEntity.getActionType());

            ArrayList<BaseEntity> actionTypeParams = actionTypeParamsTable.select("actionType=" + actionTypeEntity.getId());
            actionTypeEntity.setActionTypeParams(actionTypeParams);
        }*/

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/getUserTaskFromSession")
    public ResponseEntity<?> getUserTaskFromSession() {

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/saveUserTask")
    public ResponseEntity<?> saveUserTask(@RequestBody IdNameRequest idNameRequest) {

        //if (saveUserTaskRequest.getId() == 0)
        //    userTaskEntity = new UserTaskEntity();

        userTaskEntity.setName(idNameRequest.getName());

        UserTaskTable table = new UserTaskTable();
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

        UserTaskTable table = new UserTaskTable();
        table.fullDelete(userTaskEntity);

        userTaskEntity = null;

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/modifyUserTaskEntity")
    public ResponseEntity<?> modifyUserTaskEntity(@RequestBody IdNameRequest idNameRequest) {

        userTaskEntity.setName(idNameRequest.getName());

        return ResponseEntity.ok(new String());

    }



    /* TASK ACTIONS */

    @PostMapping("/getActionTypes")
    public ResponseEntity<?> getActionTypes() {

        ActionTypesTable table = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = table.select();

        return ResponseEntity.ok(entityArrayList);

    }

    @PostMapping("/addTaskAction")
    public ResponseEntity<?> addTaskAction() {

        TaskActionEntity taskActionEntity = new TaskActionEntity();
        taskActionEntity.setUserTask(userTaskEntity.getId());

        ArrayList<BaseEntity> taskActionArrayList = userTaskEntity.getTaskActions();
        taskActionArrayList.add(taskActionEntity);

        int currentIndex = taskActionArrayList.indexOf(taskActionEntity);
        taskActionEntity.setIndexInUserTask(currentIndex);
        taskActionEntity.setTaskOrder(currentIndex + 1);

        return ResponseEntity.ok(taskActionEntity);

    }

    @Deprecated
    @PostMapping("/getTaskAction")
    public ResponseEntity<?> getTaskAction(@RequestBody IdIndexRequest idIndexRequest) {

        BaseEntity entity = userTaskEntity.getTaskActions().get(idIndexRequest.getIndex());
        return ResponseEntity.ok(entity);

    }

    @PostMapping("/saveTaskAction")
    public ResponseEntity<?> saveTaskAction(@RequestBody SaveTaskActionRequest saveTaskActionRequest) {

        TaskActionEntity entity = (TaskActionEntity) userTaskEntity.getTaskActions().get(saveTaskActionRequest.getIndexInUserTask());
        entity.setName(saveTaskActionRequest.getName());
        entity.setTaskOrder(saveTaskActionRequest.getOrder());
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

        //ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        //ArrayList<BaseEntity> actionTypeParams = actionTypeParamsTable.select("actionType=" + idIndexActionTypeRequest.getActionType());

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        TaskActionEntity taskActionEntity = (TaskActionEntity) userTaskEntity.getTaskActions().get(idIndexActionTypeRequest.getIndex());
        taskActionsTable.initWithBlankActionTypeParams(taskActionEntity);

        return ResponseEntity.ok(new String());

    }

}
