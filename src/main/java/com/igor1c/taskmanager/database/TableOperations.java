package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;

import java.util.ArrayList;

public interface TableOperations<E> {

    void createTable();
    void createForeignKeys();
    void createExtraConstraints();

    void fillTable();

    ArrayList<BaseEntity> select();
    ArrayList<BaseEntity> select(String whereConditions);
    void insert(E entity);

}
