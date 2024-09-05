package edu.uj.po.simulation.entities;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import java.util.*;

public class Component {
    protected int id;
    protected boolean isPinHeader; // Flaga, czy to jest listwa kołkowa
    protected Map<Integer, Pin> pins;

    public Component(int id, boolean isPinHeader, List<Integer> inputPins, List<Integer> outputPins) {
        this.id = id;
        this.pins = createPins(inputPins, outputPins);
        this.isPinHeader = isPinHeader;
    }

    private Map<Integer, Pin> createPins(List<Integer> inputPins, List<Integer> outputPins) {
        Map<Integer, Pin> pinMap = new HashMap<>();

        if (!inputPins.isEmpty()) {
            for (Integer pinNumber : inputPins) {
                pinMap.put(pinNumber, new Pin(pinNumber, false));
            }
        }

        if (!outputPins.isEmpty()) {
            for (Integer pinNumber : outputPins) {
                pinMap.put(pinNumber, new Pin(pinNumber, true));
            }
        }

        return pinMap;
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

        if (pin.getPinGroup() != null) {
            for (Pin connectedPin : pin.getPinGroup().getPins()) {
                if (!connectedPin.equals(pin)) {
                    connectedPin.setState(state);  // Ustaw stan na połączonych pinach
                }
            }
        }
    }
}
