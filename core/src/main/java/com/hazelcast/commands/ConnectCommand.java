package com.hazelcast.commands;

import com.hazelcast.ClientProvider;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

@Command(name = "connect", description = "connect to a specific member in a cluster")
final class ConnectCommand implements Runnable {
    @Parameters(index = "0", description = "cluster name")
    private String cluster;
    @Parameters(index = "1", description = "member address in form of <host|ip>:<port>")
    private String member;

    private final ClientProvider clientProvider;
    private final Context context;

    public ConnectCommand(ClientProvider clientProvider, Context context) {
        this.clientProvider = clientProvider;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            clientProvider.create(cluster, member);
            context.withClusterAndMember(cluster, member);
        } catch (Exception e) {
            context.withError("Could not connect to [" + cluster + "] [" + member + "]");
        }
    }
}
