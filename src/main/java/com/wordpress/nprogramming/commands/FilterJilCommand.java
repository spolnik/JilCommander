package com.wordpress.nprogramming.commands;

import com.wordpress.nprogramming.domain.Job;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterJilCommand implements JilCommand {

    private static final String MISSED_TXT = "_missed.txt";
    private static final String OPTION_NAME = "filter";

    @Override
    public void run(CommandLine commandLine, List<Job> jobList, String output) throws IOException {

        if (!commandLine.hasOption(OPTION_NAME))
            return;

        final String namePattern = commandLine.getOptionValue(OPTION_NAME);

        try (PrintWriter matchedWriter = new PrintWriter(output)) {
            try (PrintWriter missedWriter = new PrintWriter(getOutputForMissedNames(output))) {

                final Pattern regex = Pattern.compile(namePattern);

                for (Job job : jobList) {
                    final Matcher matcher = regex.matcher(job.getJobName());

                    if (matcher.find())
                        matchedWriter.println(job.toString());
                    else
                        missedWriter.println(job.toString());
                }
            }
        }

        System.out.println("File " + Paths.get(output).toAbsolutePath() + " is created.");
        System.out.println("File " + Paths.get(getOutputForMissedNames(output)).toAbsolutePath() + " is created.");
    }

    private String getOutputForMissedNames(String output) {
        return output + MISSED_TXT;
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public Option getOption() {
        return OptionBuilder
                .hasArg(true)
                .withDescription("Filter input file jils based on given job name pattern.")
                .create(OPTION_NAME);
    }
}
