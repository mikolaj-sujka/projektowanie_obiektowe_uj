package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;

public class Pin {
    private final int pinNumber;
    private PinState state;
    private boolean isOutput;
    private PinGroup pinGroup;
    private boolean isUpdatingState;

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN;
        this.isUpdatingState = false;
        this.pinGroup = null;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public PinState getState() {
        return state;
    }

    public void setState(PinState newState) {
        if (this.state != newState) {
            this.state = newState;
            if (pinGroup != null) {
                for (Pin connectedPin : pinGroup.getPins()) {
                    if (!connectedPin.isOutput()) {
                        connectedPin.setState(newState);
                    }
                }
            }
        }
    }

    public boolean isOutput() {
        return isOutput;
    }

    public void connect(Pin otherPin) {
        if (this.pinGroup == otherPin.getPinGroup() && this.pinGroup != null) {
            return;
        }

        if (this.pinGroup == null && otherPin.getPinGroup() == null) {
            PinGroup newGroup = new PinGroup();
            newGroup.addPin(this);
            newGroup.addPin(otherPin);
            this.pinGroup = newGroup;
            otherPin.setPinGroup(newGroup);
        } else if (this.pinGroup == null) {
            otherPin.getPinGroup().addPin(this);
            this.pinGroup = otherPin.getPinGroup();
        } else if (otherPin.getPinGroup() == null) {
            this.pinGroup.addPin(otherPin);
            otherPin.setPinGroup(this.pinGroup);
        } else {
            PinGroup groupToMerge = otherPin.getPinGroup();
            for (Pin pin : groupToMerge.getPins()) {
                this.pinGroup.addPin(pin);
                pin.setPinGroup(this.pinGroup);
            }
        }
    }

    public PinGroup getPinGroup() {
        return pinGroup;
    }

    public void setPinGroup(PinGroup pinGroup) {
        this.pinGroup = pinGroup;
    }

    public boolean isConnected() {
        return pinGroup.getPins().size() > 1;
    }
}
