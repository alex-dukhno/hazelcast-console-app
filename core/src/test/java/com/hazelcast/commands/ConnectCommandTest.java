package com.hazelcast.commands;

import com.hazelcast.ClientProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConnectCommandTest {
    @Mock
    private ClientProvider provider;

    @Test
    void notConnectedPrompt() {
        CommandFactory factory = new CommandFactory(provider, new Context());
        assertThat(factory.output()).isEqualTo("[not connected]> ");
    }

    @Test
    void runConnectCommand()
            throws Exception {
        CommandFactory factory = new CommandFactory(provider, new Context());
        new CommandLine(factory.lookupCommand("connect"), factory).execute("cluster", "member");
        assertThat(factory.output()).isEqualTo("[cluster@member]> ");
    }

    @Test
    void couldNotConnect() {
        when(provider.create(anyString(), anyString())).thenThrow(RuntimeException.class);

        CommandFactory factory = new CommandFactory(provider, new Context());
        new CommandLine(factory.lookupCommand("connect"), factory).execute("cluster", "member");
        assertThat(factory.output()).isEqualTo("Could not connect to [cluster] [member]\n"
                + "[not connected]> ");
    }
}
