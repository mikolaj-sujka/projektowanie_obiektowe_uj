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
            // Łączenie dwóch grup pinów
            PinGroup groupToMerge = otherPin.getPinGroup();

            // Usuwanie duplikatów pinów oraz zapobieganie wielokrotnym pinom wyjściowym
            for (Pin pin : groupToMerge.getPins()) {
                if (!this.pinGroup.getPins().contains(pin)) {
                    if (pin.isOutput()) {
                        // Sprawdzenie, czy grupa już zawiera pin wyjściowy, jeśli tak, pomijamy
                        boolean hasOutputPin = this.pinGroup.getPins().stream().anyMatch(Pin::isOutput);
                        if (!hasOutputPin) {
                            this.pinGroup.addPin(pin);
                            pin.setPinGroup(this.pinGroup);
                        }
                    } else {
                        this.pinGroup.addPin(pin);
                        pin.setPinGroup(this.pinGroup);
                    }
                }
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
