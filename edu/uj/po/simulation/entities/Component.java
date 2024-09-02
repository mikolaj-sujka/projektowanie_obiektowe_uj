package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.ShortCircuitException;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.observers.IPinStateObserver;

import java.util.*;

public abstract class Component {
    protected int id;
    protected Map<Integer, Pin> pins;
    private List<IPinStateObserver> observers = new ArrayList<>();

    public Component(int id, Map<Integer, Pin> pins) {
        this.id = id;
        this.pins = pins;
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

    public Map<Integer, Pin> getPins() {
        return pins;
    }

    public void addPin(int pinNumber, Pin pin) {
        pins.put(pinNumber, pin);
    }

    public abstract void performLogic(); // Metoda abstrakcyjna do wykonania logiki specyficznej dla komponentu

    public void addObserver(IPinStateObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(PinState state) {
        for (IPinStateObserver observer : observers) {
            observer.updatePinState(state);
        }
    }

    public void setPinState(int pinId, PinState state) {
        Pin pin = pins.get(pinId);
        if (pin != null && pin.getState() != state) {
            pin.setState(state);
            notifyObservers(state);
        }
    }
}
