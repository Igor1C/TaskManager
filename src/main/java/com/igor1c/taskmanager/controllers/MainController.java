package com.igor1c.taskmanager.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.igor1c.taskmanager.controllers.requests.GetActionTypeInfoRequest;
import com.igor1c.taskmanager.controllers.requests.SaveUserTaskRequest;
import com.igor1c.taskmanager.controllers.responses.BaseEntityListResponse;
import com.igor1c.taskmanager.controllers.responses.GetActionTypeInfoResponse;
import com.igor1c.taskmanager.database.ActionTypeParamsTable;
import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.database.UserTasksTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {

    @GetMapping("")
    public String main(Model model) {

        ActionTypesTable actionTypesTable = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = actionTypesTable.select();

        model.addAttribute("actionTypes", entityArrayList);

        return "main.html";

    }

    @PostMapping("/getActionTypeInfo")
    public ResponseEntity<?> getActionTypeInfo(@Valid @RequestBody GetActionTypeInfoRequest getActionTypeInfoRequest) {

        long actionTypeId = getActionTypeInfoRequest.getId();

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

        UserTasksTable userTasksTable = new UserTasksTable();
        ArrayList<BaseEntity> entityArrayList = userTasksTable.select();

        BaseEntityListResponse baseEntityListResponse = new BaseEntityListResponse();
        baseEntityListResponse.setBaseEntityList(entityArrayList);

        return ResponseEntity.ok(baseEntityListResponse);

    }

    @PostMapping("/saveUserTask")
    public void saveUserTask(@Valid @RequestBody SaveUserTaskRequest saveUserTaskRequest) {

        int id = saveUserTaskRequest.getId();
        String name = saveUserTaskRequest.getName();

        if (saveUserTaskRequest.getId() == 0) {
            UserTasksTable userTasksTable = new UserTasksTable();
            UserTaskEntity userTaskEntity = new UserTaskEntity(name);
        }

    }

}
