package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class Component7482 extends Component {

    public Component7482(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(2, new Pin(2, false));  // Wejście A1
        pins.put(14, new Pin(14, false)); // Wejście A2
        pins.put(3, new Pin(3, false)); // Wejście B1
        pins.put(13, new Pin(13, false)); // Wejście B2
        pins.put(5, new Pin(5, false)); // C0

        pins.put(1, new Pin(1, true)); // Wyjście S1
        pins.put(10, new Pin(10, true)); // Wyjście C2
        pins.put(12, new Pin(12, true)); // Wyjście S2

        return pins;
    }

    @Override
    public void performLogic() throws UnknownStateException {
        Pin inputA1 = pins.get(2);
        Pin inputB1 = pins.get(3);
        Pin inputC0 = pins.get(5);
        Pin inputA2 = pins.get(14);
        Pin inputB2 = pins.get(13);

        Pin outputS1 = pins.get(1);
        Pin outputS2 = pins.get(12);
        Pin outputC2 = pins.get(10);

        // If any input pin is in UNKNOWN state, throw an exception
        if (inputA1.getState() == PinState.UNKNOWN || inputB1.getState() == PinState.UNKNOWN ||
                inputC0.getState() == PinState.UNKNOWN || inputA2.getState() == PinState.UNKNOWN ||
                inputB2.getState() == PinState.UNKNOWN) {
            outputS1.setState(PinState.UNKNOWN);
            outputS2.setState(PinState.UNKNOWN);
            outputC2.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), inputA1.getPinNumber(), inputA1.getState()));
        }

        // Perform logic as usual
        boolean a1 = inputA1.getState() == PinState.HIGH;
        boolean b1 = inputB1.getState() == PinState.HIGH;
        boolean c0 = inputC0.getState() == PinState.HIGH;
        boolean a2 = inputA2.getState() == PinState.HIGH;
        boolean b2 = inputB2.getState() == PinState.HIGH;

        boolean s1 = a1 ^ b1 ^ c0;
        boolean carry1 = (a1 && b1) || (b1 && c0) || (a1 && c0);

        boolean s2 = a2 ^ b2 ^ carry1;
        boolean carry2 = (a2 && b2) || (b2 && carry1) || (a2 && carry1);

        outputS1.setState(s1 ? PinState.HIGH : PinState.LOW);
        outputS2.setState(s2 ? PinState.HIGH : PinState.LOW);
        outputC2.setState(carry2 ? PinState.HIGH : PinState.LOW);
    }
}