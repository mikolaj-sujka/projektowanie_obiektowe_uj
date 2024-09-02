package edu.uj.po.simulation.extensions;

public class UniqueIdGenerator {
    private static int nextComponentId = 1;

    public static synchronized int getNextId() {
        return nextComponentId++;

    }
}
