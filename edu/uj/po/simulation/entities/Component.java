package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import edu.uj.po.simulation.observers.IPinStateObserver;

import java.util.*;

public class Component {
    protected int id;
    protected boolean isPinHeader; // Flaga, czy to jest listwa kołkowa
    protected Map<Integer, Pin> pins;
    private List<IPinStateObserver> observers = new ArrayList<>();

    public Component(int id, Map<Integer, Pin> pins, boolean isPinHeader) {
        this.id = id;
        this.pins = pins;
        this.isPinHeader = isPinHeader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pin getPin(int pinNumber)  {
        return pins.get(pinNumber);
    }

    public boolean isPinHeader() {
        return isPinHeader;
    }

    public Map<Integer, Pin> getPins() {
        return pins;
    }

    public void performLogic() throws UnknownStateException {
        // empty
    }

    public void setAndPropagatePinState(int pinNumber, PinState state) throws UnknownPin {
        Pin pin = pins.get(pinNumber);
        if (pin == null) {
            throw new UnknownPin(id, pinNumber); // Wyjątek, jeśli pin nie istnieje
        }

        pin.setState(state);

        // Propagacja stanu, jeśli pin jest połączony z innymi pinami
        if (pin.getPinGroup() != null) {
            for (Pin connectedPin : pin.getPinGroup().getPins()) {
                if (!connectedPin.equals(pin)) {
                    connectedPin.setState(state);  // Ustaw stan na połączonych pinach
                }
            }
        }
    }
}
