package edu.uj.po.simulation.entities;

import java.util.HashSet;
import java.util.Set;

public class PinGroup {
    private static int nextGroupId = 1;
    private final int groupId;
    private final Set<Pin> pins;

    public PinGroup() {
        this.groupId = nextGroupId++;
        this.pins = new HashSet<>();
    }

    public int getGroupId() {
        return groupId;
    }

    public Set<Pin> getPins() {
        return pins;
    }

    public void addPin(Pin pin) {
        pins.add(pin);
    }

    public boolean hasOutputPin() {
        return pins.stream().anyMatch(Pin::isOutput);
    }

    public void clearPins() {
        pins.clear();
    }
}
