package com.igor1c.taskmanager;

import com.igor1c.database.ActionTypeParamsTable;
import com.igor1c.database.ActionTypesTable;
import com.igor1c.database.TableOperations;

import java.util.ArrayList;

public class Application {

    public static void main(String ... args) {

        createDatabase();

    }

    public static void createDatabase() {

        ArrayList<TableOperations> tableOperationsArray = new ArrayList<TableOperations>();
        tableOperationsArray.add(new ActionTypesTable());
        tableOperationsArray.add(new ActionTypeParamsTable());

        for (TableOperations tableOperations : tableOperationsArray) {
            tableOperations.createTable();
            tableOperations.createForeignKeys();
            tableOperations.createExtraConstraints();
        }

    }

}
