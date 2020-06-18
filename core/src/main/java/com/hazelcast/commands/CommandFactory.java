package com.hazelcast.commands;

import com.hazelcast.ClientProvider;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public final class CommandFactory
        implements CommandLine.IFactory {
    private final Map<String, Class<? extends Runnable>> commands = new HashMap<>();
    private final Context context;
    private final ClientProvider provider;

    public static CommandFactory exitAllowed(ClientProvider provider) {
        CommandFactory factory = new CommandFactory(provider, new Context());
        factory.commands.put("exit", ExitCommand.class);
        return factory;
    }

    public CommandFactory(ClientProvider provider, Context context) {
        commands.put("connect", ConnectCommand.class);
        commands.put("ns", CreateNamespaceCommand.class);
        this.provider = provider;
        this.context = context;
    }

    @Override
    public <C> C create(Class<C> cls)
            throws Exception {
        if (cls.equals(ConnectCommand.class)) {
            return (C) new ConnectCommand(provider, context);
        } else if (cls.equals(CreateNamespaceCommand.class)) {
            return (C) new CreateNamespaceCommand(provider.get(context.getCluster(), context.getMember()), context);
        } else if (cls.equals(ExitCommand.class)) {
            return (C) new ExitCommand();
        }
        return null;
    }

    public Class<? extends Runnable> lookupCommand(String name) {
        return commands.get(name);
    }

    public String output() {
        return context.toString();
    }

}
