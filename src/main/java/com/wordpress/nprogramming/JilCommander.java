package com.wordpress.nprogramming;

import com.wordpress.nprogramming.commands.JilCommand;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.List;

interface JilCommander {
    void run(String[] args, List<JilCommand> commands) throws ParseException, IOException;
}
