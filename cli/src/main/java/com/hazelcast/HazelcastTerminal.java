package com.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import io.airlift.airline.Cli;
import io.airlift.airline.Command;
import io.airlift.airline.Help;
import io.airlift.airline.Option;

import java.util.Scanner;

public class HazelcastTerminal {
    private static HazelcastInstance client =
            HazelcastClient.getOrCreateHazelcastClient(
                    new ClientConfig()
                            .setClusterName("Cluster-1")
                            .setInstanceName("192.168.0.5:5701"));

    public static void main(String[] args) {
        String namespace = "default";
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("hazelcast console")
                .withDescription("the stupid content tracker")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, CreateNamespaceCommand.class);

        Cli<Runnable> gitParser = builder.build();

        gitParser.parse(args).run();
        while (running) {
            System.out.print("hazelcast[" + namespace + "] > ");
            try {
                final String command = scanner.nextLine();
                gitParser.parse(command.split("\\s+")).run();
                System.out.println("You entered: [" + command + "]");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Command(name = "ns", description = "creates specified data structure on hazelcast cluster")
    public static class CreateNamespaceCommand implements Runnable {
        @Option(name = "-n", description = "name of data structure")
        public String name;
        @Option(name = "-t", description = "type of data structure")
        public String type;

        @Override
        public void run() {
            if ("map".equals(type)) {
                client.getMap(name);
            } else {
                System.out.println("do nothing");
            }
        }
    }
}
