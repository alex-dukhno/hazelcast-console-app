package com.hazelcast.commands;

import com.hazelcast.ClientProvider;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateNamespaceCommandTest {
    @Mock
    private ClientProvider provider;

    @Test
    void createMapNamespace() {
        HazelcastInstance client = mock(HazelcastInstance.class);
        when(provider.get(anyString(), anyString())).thenReturn(client);
        CommandFactory factory = new CommandFactory(provider, new Context());
        new CommandLine(factory.lookupCommand("connect"), factory).execute("cluster", "member");
        new CommandLine(factory.lookupCommand("ns"), factory).execute("myMap", "map");
        assertThat(factory.currentContext().toString()).isEqualTo("[cluster@member]/maps/myMap> ");
    }

    @Test
    void createNamespace_whenNotConnected() {
        when(provider.get(any(), any())).thenReturn(null);
        CommandFactory factory = new CommandFactory(provider, new Context());
        new CommandLine(factory.lookupCommand("ns"), factory).execute("myMap", "map");
        assertThat(factory.currentContext().toString()).isEqualTo("You have to connect to a member in a cluster before creating namespace\n"
                + "[not connected]> ");
    }
}
