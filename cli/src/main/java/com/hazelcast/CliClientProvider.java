package com.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

import java.util.HashMap;
import java.util.Map;

class CliClientProvider
        implements ClientProvider {
    private final Map<String, HazelcastInstance> clients = new HashMap<>();

    @Override
    public HazelcastInstance create(String cluster, String member) {
        return clients.computeIfAbsent(cluster + member, _key -> HazelcastClient
                .getOrCreateHazelcastClient(new ClientConfig().setInstanceName(member).setClusterName(cluster)));
    }

    @Override
    public HazelcastInstance get(String cluster, String member) {
        return clients.get(cluster + member);
    }
}
