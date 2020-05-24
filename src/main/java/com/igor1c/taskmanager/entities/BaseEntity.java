package com.igor1c.taskmanager.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class BaseEntity {

    protected long id;



    /* CONSTRUCTORS */

    public BaseEntity() {}

    public BaseEntity(ResultSet resultSet) {
        fillFromResultSet(resultSet);
    }



    /* METHODS OF PROCESSING */

    public void fillFromResultSet(ResultSet resultSet) {}



    /* JSON */

    public void insertUpdateFromJsonObject(JSONObject jsonObject) {}

    public JSONObject toJsonObject() {
        return new JSONObject();
    }

    protected static ArrayList<BaseEntity> jsonArrayToArray(JSONArray jsonArray, String tableName) {

        ArrayList<BaseEntity> baseEntityArrayList = new ArrayList<>();
        int length = jsonArray.length();

        for (int i = 0; i < length; i++) {
            JSONObject entityJsonObject = jsonArray.getJSONObject(i);

            BaseEntity baseEntity = EntityFactory.createEntity(tableName);
            baseEntity.insertUpdateFromJsonObject(entityJsonObject);
            baseEntityArrayList.add(baseEntity);
        }

        return baseEntityArrayList;

    }

    protected static JSONArray arrayToJsonArray(ArrayList<BaseEntity> baseEntityArrayList) {

        JSONArray jsonArray = new JSONArray();
        for (BaseEntity baseEntity : baseEntityArrayList) {
            jsonArray.put(baseEntity.toJsonObject());
        }

        return jsonArray;

    }

    public static String beautifyJson(JSONObject jsonObject) {

        String jsonString = jsonObject.toString();
        String formattedJson;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try {
            JsonNode tree = objectMapper.readTree(jsonString);
            formattedJson = objectMapper.writeValueAsString(tree);
        } catch (IOException e) {
            formattedJson = jsonString;
            e.printStackTrace();
        }

        return formattedJson;

    }



    /* GETTERS & SETTERS OF THE DATABASE FIELDS */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
