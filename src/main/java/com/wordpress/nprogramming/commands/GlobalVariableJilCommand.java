package com.wordpress.nprogramming.commands;

import com.wordpress.nprogramming.domain.Job;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalVariableJilCommand implements JilCommand {

    private static final Pattern GV_REGEX = Pattern.compile("\\$\\$\\((.+?)\\)");
    private static final String OPTION_NAME = "gv";

    @Override
    public void run(CommandLine commandLine, List<Job> jobList, String output) throws IOException {

        if (!commandLine.hasOption(OPTION_NAME))
            return;

        final Set<String> globalVariables = new HashSet<>();

        for (Job job : jobList) {
            final Matcher matcher = GV_REGEX.matcher(job.toString());

            if (matcher.find())
                for (int groupId = 1; groupId <= matcher.groupCount(); groupId++)
                    globalVariables.add(matcher.group(groupId));
        }

        try (PrintWriter writer = new PrintWriter(output)) {
            for (String globalVariable : globalVariables)
                writer.println(globalVariable);
        }

        System.out.println("File " + Paths.get(output).toAbsolutePath() + " is created.");
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public Option getOption() {
        return OptionBuilder
                .hasArg(false)
                .withDescription("Generate file with all used global variables")
                .create(OPTION_NAME);
    }
}
