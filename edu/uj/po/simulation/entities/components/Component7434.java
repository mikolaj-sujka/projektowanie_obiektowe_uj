package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.PinState;

import java.util.HashMap;
import java.util.Map;

public class Component7434 extends Component {

    public Component7434(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Wejścia
        pins.put(1, new Pin(1, false));  // 1A
        pins.put(3, new Pin(3, false));  // 2A
        pins.put(5, new Pin(5, false));  // 3A
        pins.put(9, new Pin(9, false));  // 4A
        pins.put(11, new Pin(11, false)); // 5A
        pins.put(13, new Pin(13, false)); // 6A

        // Wyjścia
        pins.put(2, new Pin(2, true));   // 1Y
        pins.put(4, new Pin(4, true));   // 2Y
        pins.put(6, new Pin(6, true));   // 3Y
        pins.put(8, new Pin(8, true));   // 4Y
        pins.put(10, new Pin(10, true)); // 5Y
        pins.put(12, new Pin(12, true)); // 6Y

        return pins;
    }

    @Override
    public void performLogic() {
        // Iterujemy przez pary wejście-wyjście
        int[][] pinPairs = {
                {1, 2},  // 1A -> 1Y
                {3, 4},  // 2A -> 2Y
                {5, 6},  // 3A -> 3Y
                {9, 8},  // 4A -> 4Y
                {11, 10},// 5A -> 5Y
                {13, 12} // 6A -> 6Y
        };

        for (int[] pair : pinPairs) {
            Pin input = pins.get(pair[0]);
            Pin output = pins.get(pair[1]);

            // Sprawdzenie, czy piny istnieją, aby uniknąć NullPointerException
            if (input != null && output != null) {
                if (input.getState() == PinState.UNKNOWN) {
                    output.setState(PinState.UNKNOWN);
                } else if (input.getState() == PinState.HIGH) {
                    output.setState(PinState.LOW);  // Inwersja
                } else {
                    output.setState(PinState.HIGH); // Inwersja
                }
            }
        }
    }
}
