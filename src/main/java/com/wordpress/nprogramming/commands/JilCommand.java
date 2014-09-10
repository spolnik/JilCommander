package com.wordpress.nprogramming.commands;

import com.wordpress.nprogramming.domain.Job;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.io.IOException;
import java.util.List;

public interface JilCommand {

    void run(CommandLine commandLine, List<Job> jobList, String output) throws IOException;
    Option getOption();
}
