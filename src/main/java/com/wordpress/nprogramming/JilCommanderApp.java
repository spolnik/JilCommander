package com.wordpress.nprogramming;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.wordpress.nprogramming.commands.JilCommand;
import com.wordpress.nprogramming.domain.Job;
import com.wordpress.nprogramming.parsing.JobParser;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class JilCommanderApp implements JilCommander {

    private final JobParser jobParser;
    private final CommandLineParser commandLineParser;

    @Inject
    public JilCommanderApp(JobParser jobParser, CommandLineParser commandLineParser) {
        this.jobParser = jobParser;
        this.commandLineParser = commandLineParser;
    }

    public static void main(String[] args) throws ParseException, IOException {

        Injector injector = Guice.createInjector(new JilCommanderModule());

        final List<JilCommand> commands = injector.getInstance(new Key<List<JilCommand>>() {});
        final JilCommander jilCommander = injector.getInstance(JilCommander.class);

        jilCommander.run(args, commands);
    }

    @Override
    public void run(String[] args, List<JilCommand> commands) throws ParseException, IOException {

        CommandLine commandLine = getCommandLine(args, commands);

        if (isInOutParamsMissingOrHelpRequested(commandLine) || commandLine.getArgList().isEmpty()) {
            printHelp();
            return;
        }

        final List<Job> jobs = getJobs(commandLine);
        final String output = commandLine.getOptionValue("out");

        for (JilCommand command : commands)
            command.run(commandLine, jobs, output);
    }

    private CommandLine getCommandLine(String[] args, List<JilCommand> commands) throws ParseException {
        Options options = getBaseOptions();

        for (JilCommand command : commands)
            options.addOption(command.getOption());

        return commandLineParser.parse(options, args);
    }

    private List<Job> getJobs(CommandLine commandLine) throws IOException {
        final String input = commandLine.getOptionValue("in");
        final Path inputPath = Paths.get(input);
        checkState(Files.exists(inputPath), "File doesn't exist: " + inputPath.toString());

        return jobParser.parse(new String(Files.readAllBytes(inputPath)));
    }

    private boolean isInOutParamsMissingOrHelpRequested(CommandLine cl) {
        return cl.hasOption('h') || !cl.hasOption("in") || !cl.hasOption("out");
    }

    private void printHelp() {
        new HelpFormatter().printHelp("OptionsTip", getBaseOptions());
    }

    private Options getBaseOptions() {
        Options opt = new Options();

        opt.addOption("h", false, "Print help.");
        opt.addOption("in", true, "Input jil file.");
        opt.addOption("out", true, "Output file.");

        return opt;
    }
}
