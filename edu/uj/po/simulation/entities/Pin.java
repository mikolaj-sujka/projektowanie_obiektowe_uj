package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;

import java.util.HashSet;
import java.util.Set;

public class Pin {
    private final int pinNumber;
    private PinState state;
    private final boolean isOutput;
    private Set<Pin> connectedPins;
    private boolean isUpdatingState;

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN;
        this.isUpdatingState = false;
        this.connectedPins = new HashSet<Pin>();
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public PinState getState() {
        return state;
    }

    public void setState(PinState newState) {
        if (!isUpdatingState) {
            isUpdatingState = true;  // Ustaw flagę na true, aby zapobiec rekurencji

            this.state = newState;

            for (Pin connectedPin : connectedPins) {
                if (!connectedPin.isUpdatingState) {
                    connectedPin.setState(newState);
                }
            }

            isUpdatingState = false;  // Ustaw flagę z powrotem na false po zakończeniu
        }
    }

    public boolean isOutput() {
        return isOutput;
    }

    public void connect(Pin otherPin) {
        this.connectedPins.add(otherPin);
        otherPin.connectedPins.add(this);
    }

    public void disconnect() {
        for (Pin connectedPin : connectedPins) {
            connectedPin.connectedPins.remove(this);
        }
        connectedPins.clear();
    }

    public boolean isConnected() {
        return !connectedPins.isEmpty();
    }

    public Set<Pin> getConnectedPins() {
        return connectedPins;
    }
}
