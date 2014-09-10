package com.wordpress.nprogramming;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.wordpress.nprogramming.commands.*;
import com.wordpress.nprogramming.parsing.JobParser;
import com.wordpress.nprogramming.parsing.JobParserImpl;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class JilCommanderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JobParser.class).to(JobParserImpl.class).asEagerSingleton();
        bind(new TypeLiteral<List<JilCommand>>() {}).toInstance(getCommands());
        bind(CommandLineParser.class).to(BasicParser.class).asEagerSingleton();
        bind(JilCommander.class).to(JilCommanderApp.class).asEagerSingleton();
    }

    private List<JilCommand> getCommands() {
        List<JilCommand> commands = new ArrayList<>();

        commands.add(new SortJilCommand());
        commands.add(new DeleteJilCommand());
        commands.add(new FilterJilCommand());
        commands.add(new GlobalVariableJilCommand());

        return Collections.unmodifiableList(commands);
    }
}
