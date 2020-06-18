package com.hazelcast;

import com.hazelcast.core.HazelcastInstance;

public interface ClientProvider {
    HazelcastInstance create(String cluster, String member);

    HazelcastInstance get(String cluster, String member);
}
