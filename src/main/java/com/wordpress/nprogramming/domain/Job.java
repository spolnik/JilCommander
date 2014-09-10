package com.wordpress.nprogramming.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class Job implements Comparable<Job>{

    private static final String INSERT_JOB_KEY = "insert_job";
    private static final String JOB_TYPE_KEY = "job_type";

    private final Map<String, String> properties;

    public Job(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getJobName() {
        return getProperty(INSERT_JOB_KEY);
    }

    public JobType getJobType() {
        return JobType.fromString(getProperty(JOB_TYPE_KEY));
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public String toString() {
        StringBuilder jil = new StringBuilder();

        jil.append("/* ---------- ").append(getJobName()).append(" ---------- */\n\n");
        jil.append("insert_job: ").append(getJobName()).append(" job_type: ");
        jil.append(getJobType().getRawValue()).append("\n");

        for (String key : properties.keySet()) {
            if (alreadyUsed(key))
                continue;

            jil.append(key).append(": ").append(getProperty(key)).append("\n");
        }

        return jil.toString();
    }

    private boolean alreadyUsed(String key) {
        return INSERT_JOB_KEY.equals(key.toLowerCase())
                || JOB_TYPE_KEY.equals(key.toLowerCase());
    }

    @Override
    public int compareTo(@NotNull Job job) {
        return getJobName().compareTo(job.getJobName());
    }
}

