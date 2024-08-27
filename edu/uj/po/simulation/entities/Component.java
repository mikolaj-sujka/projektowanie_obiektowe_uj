package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.observers.IPinStateObserver;

import java.util.*;

public abstract class Component {
    protected int id; // Unikalny identyfikator komponentu
    protected Map<Integer, Pin> pins; // Mapa pinów przypisanych do komponentu
    private List<IPinStateObserver> observers = new ArrayList<>();

    public Component(int id) {
        this.id = id;
        this.pins = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pin getPin(int pinNumber) throws UnknownPin {
        Pin pin = pins.get(pinNumber);
        if (pin == null) {
            throw new UnknownPin(id, pinNumber);
        }
        return pin;
    }

    public Set<Pin> getPins() {
        return new HashSet<>(pins.values()); // Convert the values of the map to a Set and return it
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
