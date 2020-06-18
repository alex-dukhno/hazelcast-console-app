package com.hazelcast;

import com.hazelcast.commands.CommandFactory;
import picocli.CommandLine;

import java.util.Scanner;

public class HazelcastTerminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandFactory factory = CommandFactory.exitAllowed(new CliClientProvider());
        for (;;) {
            try {
                System.out.print(factory.output());
                final String userEnteredLine = scanner.nextLine();
                String[] tokens = userEnteredLine.split("\\s+");
                final String command = tokens[0];
                final String[] arguments = new String[tokens.length - 1];
                System.arraycopy(tokens, 1, arguments, 0, arguments.length);
                new CommandLine(factory.lookupCommand(command), factory).execute(arguments);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
