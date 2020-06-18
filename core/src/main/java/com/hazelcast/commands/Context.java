package com.hazelcast.commands;

import com.hazelcast.DataStructureType;

public final class Context {
    private String cluster;
    private String member;
    private DataStructureType type;
    private String name;
    private String error;

    public Context withClusterAndMember(String cluster, String member) {
        this.cluster = cluster;
        this.member = member;
        return this;
    }

    public String getCluster() {
        return cluster;
    }

    public String getMember() {
        return member;
    }

    public String message() {
        if (error != null) {
            return error;
        }
        return null;
    }

    public String prompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (cluster != null) {
            sb.append(cluster);
            if (member != null) {
                sb.append("@");
                sb.append(member);
            }
        } else {
            sb.append("not connected");
        }
        sb.append("]");
        if (type != null) {
            sb.append("/").append(type).append("s").append("/").append(name);
        }
        sb.append("> ");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (message() != null) {
            sb.append(message()).append('\n');
        }
        sb.append(prompt());
        return sb.toString();
    }

    public Context withError(String error) {
        this.error = error;
        return this;
    }

    public Context withDataStructure(DataStructureType type, String name) {
        this.type = type;
        this.name = name;
        return this;
    }
}
