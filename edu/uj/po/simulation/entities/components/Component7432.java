package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7432 extends Component {

    public Component7432(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(1, new Pin(1, false));  // Wejście A1
        pins.put(2, new Pin(2, false));  // Wejście B1
        pins.put(3, new Pin(3, true));   // Wyjście Y1

        pins.put(4, new Pin(4, false));  // Wejście A2
        pins.put(5, new Pin(5, false));  // Wejście B2
        pins.put(6, new Pin(6, true));   // Wyjście Y2

        pins.put(8, new Pin(8, true));   // Wyjście Y3
        pins.put(9, new Pin(9, false));  // Wejście A3
        pins.put(10, new Pin(10, false)); // Wejście B3

        pins.put(11, new Pin(11, true)); // Wyjście Y4
        pins.put(12, new Pin(12, false)); // Wejście A4
        pins.put(13, new Pin(13, false)); // Wejście B4

        return pins;
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 3);   // Bramka 1
        performLogicForGate(4, 5, 6);   // Bramka 2
        performLogicForGate(9, 10, 8);  // Bramka 3
        performLogicForGate(12, 13, 11); // Bramka 4
    }

    private void performLogicForGate(int pinA, int pinB, int pinY) {
        Pin inputA = pins.get(pinA);
        Pin inputB = pins.get(pinB);
        Pin output = pins.get(pinY);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH || inputB.getState() == PinState.HIGH) {
            output.setState(PinState.HIGH);
        } else {
            output.setState(PinState.LOW);
        }
    }
}