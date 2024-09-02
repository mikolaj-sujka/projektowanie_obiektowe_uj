package edu.uj.po.simulation.entities;

import java.util.HashSet;
import java.util.Set;

public class PinGroup {
    private static int nextGroupId = 1;
    private final int groupId;
    private final Set<Pin> pins;
    private boolean hasOutput;

    public PinGroup() {
        this.groupId = nextGroupId++;
        this.pins = new HashSet<>();
        this.hasOutput = false;
    }

    public int getGroupId() {
        return groupId;
    }

    public Set<Pin> getPins() {
        return pins;
    }

    public boolean hasOutput() {
        return hasOutput;
    }

    public void addPin(Pin pin) {
        pins.add(pin);
        if (pin.isOutput()) {
            this.hasOutput = true;
        }
    }

    public void removePin(Pin pin) {
        pins.remove(pin);
        if (pin.isOutput() && pins.stream().noneMatch(Pin::isOutput)) {
            this.hasOutput = false;
        }
    }
}
