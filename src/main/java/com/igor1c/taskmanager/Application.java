package com.igor1c.taskmanager;

import com.igor1c.database.ActionTypesTable;

public class Application {

    public static void main(String ... args) {

        ActionTypesTable actionTypesTable = new ActionTypesTable();
        actionTypesTable.createTable();

    }

}
