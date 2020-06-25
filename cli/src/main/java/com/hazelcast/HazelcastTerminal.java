package com.hazelcast;

import com.hazelcast.commands.CommandEngine;
import com.hazelcast.commands.CommandFactory;

import java.util.Scanner;

public class HazelcastTerminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandFactory factory = CommandFactory.exitAllowed(new CliClientProvider());
        CommandEngine engine = new CommandEngine(factory);
        for (;;) {
            try {
                System.out.print(factory.currentContext().toString());
                engine.execute(scanner.nextLine());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
