package com.igor1c.database;

import com.igor1c.entities.ActionTypeEntity;
import com.igor1c.entities.BaseEntity;

public class ActionTypesTable extends BaseEntity implements TableOperations {

    public ActionTypesTable() {
        super("actionTypes");
    }


    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL\n" +
                        ");";

    }

    public void createForeignKeys() {

    }

    public void createExtraConstraints() {

    }

}
