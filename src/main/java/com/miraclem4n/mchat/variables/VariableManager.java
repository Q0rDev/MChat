package com.miraclem4n.mchat.variables;

import com.miraclem4n.mchat.api.API;
import com.miraclem4n.mchat.types.IndicatorType;
import com.miraclem4n.mchat.util.MessageUtil;

import java.util.*;

/**
 * Manager for Variables.
 */
public class VariableManager {
    private NavigableMap<String, Object> hVarMap;
    private NavigableMap<String, Object> nVarMap;
    private NavigableMap<String, Object> lVarMap;

    public VariableManager() {
        hVarMap = new TreeMap<String, Object>();
        nVarMap = new TreeMap<String, Object>();
        lVarMap = new TreeMap<String, Object>();
    }

    /**
     * Adds Variables to Designated Priority.
     * @param strings Variables being added.
     * @param value Value of Variables.
     * @param priority Priority to be resolved at.
     */
    public void addVars(String[] strings, Object value, ResolvePriority priority) {
        if (strings != null && value != null) {
            addVars(Arrays.asList(strings), value, priority);
        }
    }

    /**
     * Adds Variables to Designated Priority.
     * @param list Variables being added.
     * @param value Value of Variables.
     * @param priority Priority to be resolved at.
     */
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

    /**
     * Variable Sorter.
     * @param priority Which Map Priority to be sorted.
     */
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

    /**
     * Variable Replacer.
     * @param format String to be replaced.
     * @param doColour Whether or not to colour replacement value.
     * @param priority Which Map priority to replace.
     * @return String with Variables replaced.
     */
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

    /**
     * Custom Variable Replacer.
     * @param pName Player's Name.
     * @param format String to be replaced.
     * @return String with Custom Variables replaced.
     */
    public String replaceCustVars(String pName, String format) {
        if (!API.getVarMapQueue().isEmpty()) {
            API.getVarMap().putAll(API.getVarMapQueue());
            API.getVarMapQueue().clear();
        }

        Set<Map.Entry<String, Object>> varSet = API.getVarMap().entrySet();

        for (Map.Entry<String, Object> entry : varSet) {
            String pKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace(pName + "|", "");
            String value = entry.getValue().toString();

            if (format.contains(pKey)) {
                format = format.replace(pKey, MessageUtil.addColour(value));
            }
        }

        for (Map.Entry<String, Object> entry : varSet) {
            String gKey = IndicatorType.CUS_VAR.getValue() + entry.getKey().replace("%^global^%|", "");
            String value = entry.getValue().toString();

            if (format.contains(gKey)) {
                format = format.replace(gKey, MessageUtil.addColour(value));
            }
        }

        return format;
    }
}
