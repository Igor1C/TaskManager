package com.igor1c.taskmanager.helpers;

import com.igor1c.taskmanager.entities.BaseEntity;

import java.util.ArrayList;

public class EntityHelper {

    public static ArrayList<Long> generateIdArray(ArrayList<BaseEntity> baseEntityArray) {

        ArrayList<Long> idArray = new ArrayList<>();
        for (BaseEntity baseEntity : baseEntityArray) {
            long currentId = baseEntity.getId();
            if (currentId != 0)
                idArray.add(currentId);
        }

        return idArray;

    }

}
