package com.miraclem4n.mchat.variables;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.*;

public class VariableManager {
    NavigableMap<String, Object> hVarMap;
    NavigableMap<String, Object> nVarMap;
    NavigableMap<String, Object> lVarMap;

    public VariableManager() {
        hVarMap = new TreeMap<String, Object>();
        nVarMap = new TreeMap<String, Object>();
        lVarMap = new TreeMap<String, Object>();
    }

    public void addVars(String[] strings, Object value, ResolvePriority priority) {
        if (strings != null && value != null) {
            addVars(Arrays.asList(strings), value, priority);
        }
    }

    public void addVars(List<String> list, Object value, ResolvePriority priority) {
        if (list != null && value != null) {
            for (String string : list) {
                switch(priority) {
                    case FIRST:
                        hVarMap.put(IndicatorType.MISC_VAR.getValue() + string, value);
                        break;
                    case NORMAL:
                        nVarMap.put(IndicatorType.MISC_VAR.getValue() + string, value);
                        break;
                    case LAST:
                        lVarMap.put(IndicatorType.MISC_VAR.getValue() + string, value);
                        break;
                    default:
                        nVarMap.put(IndicatorType.MISC_VAR.getValue() + string, value);
                        break;
                }
            }
        }
    }

    public void sortVars(ResolvePriority priority) {
        switch(priority) {
            case FIRST:
                hVarMap = hVarMap.descendingMap();
                break;
            case NORMAL:
                nVarMap = nVarMap.descendingMap();
                break;
            case LAST:
                lVarMap = lVarMap.descendingMap();
                break;
            default:
                hVarMap = hVarMap.descendingMap();
                nVarMap = nVarMap.descendingMap();
                lVarMap = lVarMap.descendingMap();
                break;
        }
    }

    public String replaceVars(String format, Boolean doColour, ResolvePriority priority) {
        NavigableMap<String, Object> varMap;

        switch(priority) {
            case FIRST:
                varMap = hVarMap;
                break;
            case NORMAL:
                varMap = nVarMap;
                break;
            case LAST:
                varMap = lVarMap;
                break;
            default:
                format = replaceVars(format, doColour, ResolvePriority.FIRST);
                format = replaceVars(format, doColour, ResolvePriority.NORMAL);
                format = replaceVars(format, false, ResolvePriority.LAST);

                return format;
        }

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
