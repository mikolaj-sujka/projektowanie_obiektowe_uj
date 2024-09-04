package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;

public class Pin {
    private final int pinNumber;
    private PinState state;
    private final boolean isOutput;
    private PinGroup pinGroup;
    private boolean isUpdatingState;

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN;
        this.isUpdatingState = false;
        this.pinGroup = null;
    }

    public void connect(Pin otherPin) {
        // Sprawdzenie, czy piny są już połączone w tej samej grupie
        if (this.pinGroup != null && this.pinGroup == otherPin.getPinGroup()) {
            return;
        }

        // Oba piny nie są połączone z żadną grupą
        if (this.pinGroup == null && otherPin.getPinGroup() == null) {
            PinGroup newGroup = new PinGroup();
            newGroup.addPin(this);
            newGroup.addPin(otherPin);
            this.pinGroup = newGroup;
            otherPin.setPinGroup(newGroup);
        } else if (this.pinGroup == null) {
            // Dodanie this do grupy otherPin
            if (!otherPin.getPinGroup().getPins().contains(this)) {
                otherPin.getPinGroup().addPin(this);
                this.pinGroup = otherPin.getPinGroup();
            }
        } else if (otherPin.getPinGroup() == null) {
            // Dodanie otherPin do grupy this
            if (!this.pinGroup.getPins().contains(otherPin)) {
                this.pinGroup.addPin(otherPin);
                otherPin.setPinGroup(this.pinGroup);
            }
        } else {
            // Łączenie dwóch grup pinów (scalanie grup)
            PinGroup groupToMerge = otherPin.getPinGroup();
            for (Pin pin : groupToMerge.getPins()) {
                if (!this.pinGroup.getPins().contains(pin)) {
                    this.pinGroup.addPin(pin);
                    pin.setPinGroup(this.pinGroup);
                }
            }
        }
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

    public int getPinNumber() {
        return pinNumber;
    }

    public PinState getState() {
        return state;
    }

    public PinGroup getPinGroup() {
        return pinGroup;
    }

    public void setPinGroup(PinGroup pinGroup) {
        this.pinGroup = pinGroup;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public boolean isConnected() {
        return pinGroup != null && pinGroup.getPins().size() > 1;
    }
}
