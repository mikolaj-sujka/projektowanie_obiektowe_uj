package entities;

import edu.uj.po.simulation.interfaces.UnknownPin;

import java.util.HashMap;
import java.util.Map;

public abstract class Component {
    protected int id; // Unikalny identyfikator komponentu
    protected Map<Integer, Pin> pins; // Mapa pinów przypisanych do komponentu

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

    public void addPin(int pinNumber, Pin pin) {
        pins.put(pinNumber, pin);
    }

    public abstract void performLogic(); // Metoda abstrakcyjna do wykonania logiki specyficznej dla komponentu
}
