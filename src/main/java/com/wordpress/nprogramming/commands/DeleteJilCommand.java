package com.wordpress.nprogramming.commands;

import com.wordpress.nprogramming.domain.Job;
import com.wordpress.nprogramming.domain.JobType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class DeleteJilCommand implements JilCommand {

    private static final String OPTION_NAME = "delete";
    private static final String DELETE_JOB_KEY = "delete_job";

    @Override
    public void run(CommandLine commandLine, List<Job> jobs, String output) throws IOException {

        if (!commandLine.hasOption(OPTION_NAME))
            return;

        try (PrintWriter printWriter = new PrintWriter(output)) {
            for (Job job : jobs) {
                if (job.getJobType() != JobType.BOX)
                    writeJob(printWriter, job);
            }

            for (Job job : jobs) {
                if (job.getJobType() == JobType.BOX)
                    writeJob(printWriter, job);
            }
        }

        System.out.println("File " + Paths.get(output).toAbsolutePath() + " is created.");
    }

    private void writeJob(PrintWriter printWriter, Job job) {
        printWriter.println(String.format("%s: %s", DELETE_JOB_KEY, job.getJobName()));
    }

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    public Option getOption() {
        return OptionBuilder
                .hasArg(false)
                .withDescription("Generate file with delete_job commands corresponding to insert_job file")
                .create(OPTION_NAME);
    }
}
