package com.wordpress.nprogramming;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JobParserImpl implements JobParser {

    private static final Pattern JIL_REGEX = Pattern.compile("^([A-Za-z-_]+):\\s*(.*)$");

    @Override
    public Job parse(String jil) {
        String newJil = jil.replaceFirst("job_type:", "\njob_type:");

        final String[] jilLines = newJil.split("\n");
        Map<String,String> properties = new HashMap<>();

        for (String rawLine : jilLines) {
            String line = rawLine.trim();
            final Matcher matcher = JIL_REGEX.matcher(line);

            if (!matcher.find()) {
                continue;
            }

            properties.put(matcher.group(1), matcher.group(2));
        }

        return new Job(properties);
    }
}
