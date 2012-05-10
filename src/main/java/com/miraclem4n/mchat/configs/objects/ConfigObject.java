package com.miraclem4n.mchat.configs.objects;

import java.util.ArrayList;

public class ConfigObject {
    Object object;

    public ConfigObject(Object obj) {
        object = obj;
    }

    public Boolean toBoolean() {
        return object instanceof Boolean ? (Boolean) object : false;
    }

    public String toString() {
        return object != null ? object.toString() : null;
    }

    public Integer toInteger() {
        return object instanceof Number ? (Integer) object : 0;
    }

    public Double toDouble() {
        return object instanceof Number ? (Double) object : 0;
    }

    public ArrayList<String> toStringList() {
        ArrayList<String> list = new ArrayList<String>();

        if (object instanceof ArrayList) {
            ArrayList aList = (ArrayList) object;

            for (Object obj : aList)
                list.add((String) obj);

        }

        return list;
    }
}
