package com.igor1c.database;

public interface TableOperations {

    void createTable();
    void createForeignKeys();
    void createExtraConstraints();
    void fillTable();

}
