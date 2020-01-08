package com.igor1c.taskmanager.controllers;

import com.igor1c.taskmanager.controllers.requests.IdRequest;
import com.igor1c.taskmanager.controllers.requests.SaveTaskActionRequest;
import com.igor1c.taskmanager.controllers.requests.SaveUserTaskRequest;
import com.igor1c.taskmanager.controllers.responses.BaseEntityListResponse;
import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.database.TaskActionsTable;
import com.igor1c.taskmanager.database.UserTaskTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Controller
@Scope("session")
public class MainController {

    UserTaskEntity userTaskEntity;

    @GetMapping("")
    public String main(Model model) {

        return "main.html";

    }

    @PostMapping("/getUserTasks")
    public ResponseEntity<?> getUserTasks() {

        UserTaskTable userTasksTable = new UserTaskTable();
        ArrayList<BaseEntity> entityArrayList = userTasksTable.select();

        BaseEntityListResponse baseEntityListResponse = new BaseEntityListResponse();
        baseEntityListResponse.setBaseEntityList(entityArrayList);

        return ResponseEntity.ok(baseEntityListResponse);

    }

    @PostMapping("/getUserTask")
    public ResponseEntity<?> getUserTask(@RequestBody IdRequest idRequest) {

        TaskActionsTable taskActionsTable = new TaskActionsTable();
        ArrayList<BaseEntity> taskActionEntityArrayList = taskActionsTable.select("userTask=" + idRequest.getId());

        UserTaskTable table = new UserTaskTable();
        userTaskEntity = (UserTaskEntity) table.selectById(idRequest.getId());
        userTaskEntity.setTaskActions(taskActionEntityArrayList);

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/saveUserTask")
    public ResponseEntity<?> saveUserTask(@RequestBody SaveUserTaskRequest saveUserTaskRequest) {

        long id = saveUserTaskRequest.getId();

        UserTaskTable table = new UserTaskTable();
        if (id == 0) {
            userTaskEntity = new UserTaskEntity(saveUserTaskRequest.getName());
            id = table.insert(userTaskEntity);
            userTaskEntity.setId(id);
        } else {
            userTaskEntity.setName(saveUserTaskRequest.getName());
            table.update(userTaskEntity);
        }

        return ResponseEntity.ok(id);

    }

    @PostMapping("/cancelUserTask")
    public ResponseEntity<?> cancelUserTask() {

        userTaskEntity = null;

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/deleteUserTask")
    public ResponseEntity<?> deleteUserTask(@RequestBody IdRequest idRequest) {

        UserTaskTable table = new UserTaskTable();
        table.deleteById(idRequest.getId());

        userTaskEntity = null;

        return ResponseEntity.ok(new String());

    }

    @PostMapping("/getActionTypes")
    public ResponseEntity<?> getActionTypes() {

        ActionTypesTable table = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = table.select();

        return ResponseEntity.ok(entityArrayList);

    }

    @PostMapping("/saveTaskAction")
    public ResponseEntity<?> saveTaskAction(@RequestBody SaveTaskActionRequest saveTaskActionRequest) {

        long id = saveTaskActionRequest.getId();
        long actionType = saveTaskActionRequest.getActionType();

        ActionTypesTable table = new ActionTypesTable();
        if (id == 0) {

        } else {

        }

        return ResponseEntity.ok(id);

    }

}
