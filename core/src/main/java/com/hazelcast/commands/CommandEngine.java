package com.hazelcast.commands;

import picocli.CommandLine;

public class CommandEngine {
    private final CommandFactory factory;

    public CommandEngine(CommandFactory factory) {
        this.factory = factory;
    }

    public Context execute(String userEnteredLine) {
        final String[] tokens = userEnteredLine.split("\\s+");
        final String command = tokens[0];
        final String[] arguments = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, arguments, 0, arguments.length);
        new CommandLine(factory.lookupCommand(command), factory).execute(arguments);
        return factory.currentContext();
    }
}
