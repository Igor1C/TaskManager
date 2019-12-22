package com.igor1c.database;

public interface TableOperations<E> {

    void createTable();
    void createForeignKeys();
    void createExtraConstraints();

    void fillTable();

    void insert(E entity);

}
