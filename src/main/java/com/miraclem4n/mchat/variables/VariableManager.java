package com.miraclem4n.mchat.variables;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.*;

public class VariableManager {
    NavigableMap<String, Object> varMap;

    public VariableManager() {
        varMap = new TreeMap<String, Object>();
    }

    public void addVars(String[] strings, Object value) {
        if (strings != null) {
            for (String string : strings) {
                varMap.put(IndicatorType.MISC_VAR.getValue() + string, value);
            }
        }
    }

    public void sortVars() {
        varMap = varMap.descendingMap();
    }

    public String replaceVars(String format, Boolean doColour) {
        for (Map.Entry<String, Object> entry : varMap.entrySet()) {
            String value = entry.getValue().toString();

            if (doColour) {
                value = MessageUtil.addColour(value);
            }

            format = format.replace(entry.getKey(), value);
        }

        return format;
    }

    public String replaceCustVars(String pName, String format) {
        if (!API.varMapQueue.isEmpty()) {
            API.varMap.putAll(API.varMapQueue);
            API.varMapQueue.clear();
        }

        Set<Map.Entry<String, String>> varSet = API.varMap.entrySet();

        for (Map.Entry<String, String> entry : varSet) {
            String pKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue();

            if (format.contains(pKey)) {
                format = format.replace(pKey, MessageUtil.addColour(value));
            }
        }

        for (Map.Entry<String, String> entry : varSet) {
            String gKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace("%^global^%|", "");
            String value = entry.getValue();

            if (format.contains(gKey)) {
                format = format.replace(gKey, MessageUtil.addColour(value));
            }
        }

        return format;
    }
}
