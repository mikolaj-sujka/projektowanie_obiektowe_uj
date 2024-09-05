package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.ShortCircuitException;

public class Pin {
    private final int pinNumber;
    private PinState state;
    private final boolean isOutput;
    private PinGroup pinGroup;
    private boolean isUpdatingState;
    private boolean isPinHeaderPin = false;

    public Pin(int pinNumber, boolean isOutput) {
        this.pinNumber = pinNumber;
        this.isOutput = isOutput;
        this.state = PinState.UNKNOWN;
        this.isUpdatingState = false;
        this.pinGroup = null;
    }

    public void connect(Pin otherPin) throws ShortCircuitException {
        if (this.pinGroup != null && this.pinGroup == otherPin.getPinGroup()) {
            return;
        }

        if (this.pinGroup == null && otherPin.getPinGroup() == null) {
            PinGroup newGroup = new PinGroup();

            if (this.isOutput() && otherPin.isOutput() && !this.isPinHeaderPin && !otherPin.isPinHeaderPin) {
                throw new ShortCircuitException();
            }

            newGroup.addPin(this);
            newGroup.addPin(otherPin);
            this.pinGroup = newGroup;
            otherPin.setPinGroup(newGroup);
        }
        else if (this.pinGroup == null && otherPin.getPinGroup() != null) {
            if (this.isOutput() && otherPin.getPinGroup().hasOutputPin() && !this.isPinHeaderPin) {
                throw new ShortCircuitException();
            }

            otherPin.getPinGroup().addPin(this);
            this.pinGroup = otherPin.getPinGroup();
        }
        else if (otherPin.getPinGroup() == null && this.pinGroup != null) {
            if (otherPin.isOutput() && this.pinGroup.hasOutputPin() && !otherPin.isPinHeaderPin) {
                throw new ShortCircuitException();
            }

            this.pinGroup.addPin(otherPin);
            otherPin.setPinGroup(this.pinGroup);
        }
        else {
            PinGroup groupToMerge = otherPin.getPinGroup();

            if (this.pinGroup.hasOutputPin() && groupToMerge.hasOutputPin() && !this.isPinHeaderPin && !otherPin.isPinHeaderPin) {
                throw new ShortCircuitException();
            }

            for (Pin pin : groupToMerge.getPins()) {
                if (!this.pinGroup.getPins().contains(pin)) {
                    this.pinGroup.addPin(pin);
                    pin.setPinGroup(this.pinGroup);
                }
            }

            groupToMerge.clearPins();
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

    public void setIsPinHeaderPin(boolean isPinHeaderPin) {
        this.isPinHeaderPin = isPinHeaderPin;
    }
}
