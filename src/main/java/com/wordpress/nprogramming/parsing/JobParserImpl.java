package com.wordpress.nprogramming.parsing;

import com.wordpress.nprogramming.domain.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JobParserImpl implements JobParser {

    private static final Pattern JIL_REGEX = Pattern.compile("^([A-Za-z-_]+):\\s*(.*)$");

    @Override
    public List<Job> parse(String input) {

        final String newJil = input.replaceAll("job_type:", "\njob_type:");
        final String[] jils = newJil.split("(?=insert_job:)");

        List<Job> jobs = new ArrayList<>();

        for (String jil : jils) {
            final Job job = parseJob(jil);
            if (job == null)
                continue;

            jobs.add(job);
        }

        return jobs;
    }

    private Job parseJob(String jil) {

        final String[] jilLines = jil.split("\r?\n");
        final Map<String,String> properties = new HashMap<>();

        for (String rawLine : jilLines) {
            final String line = rawLine.trim();
            final Matcher matcher = JIL_REGEX.matcher(line);

            if (!matcher.find())
                continue;

            properties.put(matcher.group(1), matcher.group(2));
        }

        if (properties.size() == 0)
            return null;

        return new Job(properties);
    }
}
