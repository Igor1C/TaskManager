package com.igor1c.taskmanager.database;

import java.util.ArrayList;

public interface TableOperations<E> {

    void createTable();
    void createForeignKeys();
    void createExtraConstraints();

    void fillTable();

    ArrayList<E> select();
    void insert(E entity);

}
