package com.wordpress.nprogramming.domain;

public enum JobType {
    BOX("b"),
    COMMAND("c"),
    FILE_WATCHER("f");

    private final String rawValue;

    JobType(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return this.rawValue;
    }

    public static JobType fromString(String text) {
        if (text == null)
            return null;

        for (JobType type : JobType.values())
            if (text.equalsIgnoreCase(type.rawValue))
                return type;

        return null;
    }
}
