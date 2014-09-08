package com.wordpress.nprogramming;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class JilCommander {
    public static void main(String[] args) throws ParseException, IOException {

        BasicParser parser = new BasicParser();
        CommandLine cl = parser.parse(getOptions(), args);

        if (cl.hasOption('h')) {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("OptionsTip", getOptions());
        } else if (cl.hasOption("sort") && cl.hasOption("out")) {
            final String jilPath = cl.getOptionValue("sort");
            final String outputPath = cl.getOptionValue("out");

            final Path path = Paths.get(jilPath);
            checkState(Files.exists(path), "File doesn't exist: " + path.toString());

            final JobParserImpl jobParser = new JobParserImpl();
            final List<Job> jobList = jobParser.parse(new String(Files.readAllBytes(path)));

            Collections.sort(jobList, new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    return o1.getJobName().compareTo(o2.getJobName());
                }
            });

            final PrintWriter printWriter = new PrintWriter(outputPath);

            for (Job job : jobList)
                printWriter.println(job.toString());

            System.out.println("File " + Paths.get(outputPath).toAbsolutePath() +  " is created.");
        } else {
            HelpFormatter f = new HelpFormatter();
            f.printHelp("OptionsTip", getOptions());
        }
    }

    private static Options getOptions() {
        Options opt = new Options();

        opt.addOption("h", false, "Print help for this application");
        opt.addOption("sort", true, "File with jils to sort by name.");
        opt.addOption("out", true, "Destination file.");

        return opt;
    }
}
