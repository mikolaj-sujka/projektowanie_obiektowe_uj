package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7408 extends Component {
    public Component7408(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Definicja pinów dla układu 7408 (4x AND)
        pins.put(1, new Pin(1, false));  // Wejście 1A
        pins.put(2, new Pin(2, false));  // Wejście 1B
        pins.put(3, new Pin(3, true));   // Wyjście 1Y

        pins.put(4, new Pin(4, false));  // Wejście 2A
        pins.put(5, new Pin(5, false));  // Wejście 2B
        pins.put(6, new Pin(6, true));   // Wyjście 2Y

        pins.put(8, new Pin(8, true));   // Wyjście 3Y
        pins.put(9, new Pin(9, false));  // Wejście 3A
        pins.put(10, new Pin(10, false)); // Wejście 3B

        pins.put(11, new Pin(11, true)); // Wyjście 4Y
        pins.put(12, new Pin(12, false)); // Wejście 4A
        pins.put(13, new Pin(13, false)); // Wejście 4B

        // Pomijamy Vcc (pin 14) i GND (pin 7)
        return pins;
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 3);  // Bramka 1 (1A, 1B -> 1Y)
        performLogicForGate(4, 5, 6);  // Bramka 2 (2A, 2B -> 2Y)
        performLogicForGate(9, 10, 8); // Bramka 3 (3A, 3B -> 3Y)
        performLogicForGate(12, 13, 11); // Bramka 4 (4A, 4B -> 4Y)
    }

    private void performLogicForGate(int pinA, int pinB, int pinY) {
        Pin inputA = pins.get(pinA);
        Pin inputB = pins.get(pinB);
        Pin output = pins.get(pinY);

        if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.HIGH);
        } else if (inputA.getState() == PinState.LOW || inputB.getState() == PinState.LOW) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.UNKNOWN);
        }
    }
}
