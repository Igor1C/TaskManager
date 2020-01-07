package com.igor1c.taskmanager.controllers;

import com.igor1c.taskmanager.controllers.requests.IdRequest;
import com.igor1c.taskmanager.controllers.requests.SaveUserTaskRequest;
import com.igor1c.taskmanager.controllers.responses.BaseEntityListResponse;
import com.igor1c.taskmanager.controllers.responses.GetActionTypeInfoResponse;
import com.igor1c.taskmanager.database.ActionTypeParamsTable;
import com.igor1c.taskmanager.database.ActionTypesTable;
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

        ActionTypesTable actionTypesTable = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = actionTypesTable.select();

        model.addAttribute("actionTypes", entityArrayList);

        return "main.html";

    }

    @PostMapping("/getActionTypeInfo")
    public ResponseEntity<?> getActionTypeInfo(@RequestBody IdRequest idRequest) {

        long actionTypeId = idRequest.getId();

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ArrayList<BaseEntity> entityArrayList = actionTypeParamsTable.selectByActionTypeId(actionTypeId);

        ActionTypesTable actionTypesTable = new ActionTypesTable();

        GetActionTypeInfoResponse getActionTypeInfoResponse = new GetActionTypeInfoResponse();
        getActionTypeInfoResponse.setActionTypeEntity(actionTypesTable.selectById(actionTypeId));
        getActionTypeInfoResponse.setBaseEntityList(entityArrayList);

        return ResponseEntity.ok(getActionTypeInfoResponse);

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

        UserTaskTable table = new UserTaskTable();
        userTaskEntity = (UserTaskEntity) table.selectById(idRequest.getId());

        return ResponseEntity.ok(userTaskEntity);

    }

    @PostMapping("/saveUserTask")
    public ResponseEntity<?> saveUserTask(@RequestBody SaveUserTaskRequest saveUserTaskRequest) {

        long id = saveUserTaskRequest.getId();

        UserTaskTable table = new UserTaskTable();
        if (saveUserTaskRequest.getId() == 0) {
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

}
