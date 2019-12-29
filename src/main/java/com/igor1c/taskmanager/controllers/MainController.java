package com.igor1c.taskmanager.controllers;

import com.igor1c.taskmanager.database.ActionTypesTable;
import com.igor1c.taskmanager.entities.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
