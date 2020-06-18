package com.hazelcast.commands;

import com.hazelcast.DataStructureType;
import picocli.CommandLine;

import static picocli.CommandLine.*;

@Command(name = "exit", description = "exit the program")
final class ExitCommand implements Runnable {
    @Override
    public void run() {
        System.exit(0);
    }
}
