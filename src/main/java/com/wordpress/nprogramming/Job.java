package com.wordpress.nprogramming;

import java.util.Map;

public final class Job {
    private final Map<String, String> properties;

    public Job(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getJobName() {
        return getProperty("insert_job");
    }

    public JobType getJobType() {
        return JobType.fromString(getProperty("job_type"));
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String toString() {
        StringBuilder jil = new StringBuilder();

        jil.append("/* ---------- ").append(getJobName()).append(" ---------- */\n\n");
        jil.append("insert_job: ").append(getJobName()).append("\n");

        for (String key : properties.keySet())
        {
            if ("insert_job".equals(key.toLowerCase()))
                continue;

            jil.append(key).append(": ").append(getProperty(key)).append("\n");
        }

        return jil.toString();
    }
}

