package com.hazelcast.commands;

import com.hazelcast.DataStructureType;
import com.hazelcast.core.HazelcastInstance;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "ns", description = "creates specified data structure on hazelcast cluster")
final class CreateNamespaceCommand implements Runnable {
    @Parameters(index = "0", description = "name of data structure")
    private String name;
    @Parameters(index = "1", description = "type of data structure")
    private DataStructureType type;

    private final HazelcastInstance client;
    private final Context context;

    public CreateNamespaceCommand(HazelcastInstance client, Context context) {
        this.client = client;
        this.context = context;
    }

    @Override
    public void run() {
        if (client == null) {
            context.withError("You have to connect to a member in a cluster before creating namespace");
        } else {
            if (DataStructureType.map.equals(type)) {
                client.getMap(name);
                context.withDataStructure(type, name);
            }
        }
    }
}
