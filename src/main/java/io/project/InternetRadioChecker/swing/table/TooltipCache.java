package io.project.InternetRadioChecker.swing.table;

import java.util.concurrent.ConcurrentHashMap;

public class TooltipCache {

    private final ConcurrentHashMap<Integer, String> tooltipMap;

    public TooltipCache() {
        tooltipMap = new ConcurrentHashMap<>();
    }

    public void add(Integer key, String value) {
        tooltipMap.put(key, value);
    }

    public String getValue(Integer key) {
        return tooltipMap.get(key);
    }

}
