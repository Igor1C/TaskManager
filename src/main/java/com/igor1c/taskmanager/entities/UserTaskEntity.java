package com.igor1c.taskmanager.entities;

public class UserTaskEntity extends BaseEntity {

    private String name;



    public UserTaskEntity() {}

    public UserTaskEntity(long id, String name) {

        setId(id);
        setName(name);

    }



    public BaseEntity createEntity() {
        return new UserTaskEntity();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
