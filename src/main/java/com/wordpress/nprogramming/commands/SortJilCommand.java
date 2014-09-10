package com.wordpress.nprogramming.commands;

import com.wordpress.nprogramming.domain.Job;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortJilCommand implements JilCommand {

    private static final String OPTION_NAME = "sort";

    @Override
    public void run(CommandLine commandLine, List<Job> jobs, String output) throws IOException {

        if (!commandLine.hasOption(OPTION_NAME))
            return;

        List<Job> sortedJobs = new ArrayList<>(jobs);
        Collections.sort(sortedJobs);

        try (PrintWriter printWriter = new PrintWriter(output)) {
            for (Job job : sortedJobs)
                printWriter.println(job.toString());
        }

        System.out.println("File " + Paths.get(output).toAbsolutePath() +  " is created.");
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public Option getOption() {
        return OptionBuilder
                .hasArg(false)
                .withDescription("Generate file with jils sorted by name.")
                .create(OPTION_NAME);
    }
}
