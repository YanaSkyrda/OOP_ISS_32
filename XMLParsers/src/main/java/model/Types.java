package model;

import java.util.HashMap;
import java.util.Map;

public enum Types {
    CHOCOLATE("CHOCOLATE"), CANDYBARS("CANDYBARS");

    public final String label;

    private Types(String label) {
        this.label = label;
    }
    private static final Map<String, Types> BY_LABEL = new HashMap<>();

    static {
        for (Types e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }


    public static Types valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
