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
        if (!isUpdatingState) {
            isUpdatingState = true;  // Ustaw flagę na true, aby zapobiec rekurencji

            this.state = newState;

            if (pinGroup != null) {
                for (Pin connectedPin : pinGroup.getPins()) {
                    if (!connectedPin.isUpdatingState) {
                        connectedPin.setState(newState);
                    }
                }
            }

            isUpdatingState = false;  // Ustaw flagę z powrotem na false po zakończeniu
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
            // Tworzymy nową grupę tylko wtedy, gdy oba piny nie należą jeszcze do żadnej grupy
            PinGroup newGroup = new PinGroup();
            newGroup.addPin(this);
            newGroup.addPin(otherPin);
            this.pinGroup = newGroup;
            otherPin.setPinGroup(newGroup);
        } else if (this.pinGroup == null) {
            // Jeśli tylko jeden pin ma grupę, dołącz do tej grupy
            otherPin.getPinGroup().addPin(this);
            this.pinGroup = otherPin.getPinGroup();
        } else if (otherPin.getPinGroup() == null) {
            // Jeśli drugi pin nie ma grupy, dołącz do grupy pierwszego pinu
            this.pinGroup.addPin(otherPin);
            otherPin.setPinGroup(this.pinGroup);
        } else {
            // Jeśli oba piny mają grupy, scalamy te grupy
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
