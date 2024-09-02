package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.ShortCircuitException;

import java.util.HashMap;
import java.util.Map;

public class Component7404 extends Component {

    public Component7404(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(1, new Pin(1, false));  // Wejście A1
        pins.put(2, new Pin(2, true));   // Wyjście Y1

        pins.put(3, new Pin(3, false));  // Wejście A2
        pins.put(4, new Pin(4, true));   // Wyjście Y2

        pins.put(5, new Pin(5, false));  // Wejście A3
        pins.put(6, new Pin(6, true));   // Wyjście Y3

        pins.put(9, new Pin(9, false));  // Wejście A4
        pins.put(8, new Pin(8, true));   // Wyjście Y4

        pins.put(11, new Pin(11, false)); // Wejście A5
        pins.put(10, new Pin(10, true));  // Wyjście Y5

        pins.put(13, new Pin(13, false)); // Wejście A6
        pins.put(12, new Pin(12, true));  // Wyjście Y6

        return pins;
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size(); i += 2) {
            Pin input = pins.get(i);
            Pin output = pins.get(i + 1);

            if (input.getState() == PinState.UNKNOWN) {
                output.setState(PinState.UNKNOWN);
            } else if (input.getState() == PinState.HIGH) {
                output.setState(PinState.LOW);
            } else {
                output.setState(PinState.HIGH);
            }
        }
    }
}