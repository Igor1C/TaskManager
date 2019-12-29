package com.igor1c.taskmanager.controllers;

import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class UserTaskController {

    @GetMapping("")
    public String main() {

        ActionTypesTable actionTypesTable = new ActionTypesTable();
        ArrayList<BaseEntity> entityArrayList = actionTypesTable.select();

        return "main.html";

    }

}
