package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7402 extends Component {

    public Component7402(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(1, new Pin(1, false));  // Wejście A1
        pins.put(2, new Pin(2, false));  // Wejście B1
        pins.put(3, new Pin(3, true));   // Wyjście Y1

        pins.put(4, new Pin(4, false));  // Wejście A2
        pins.put(5, new Pin(5, false));  // Wejście B2
        pins.put(6, new Pin(6, true));   // Wyjście Y2

        pins.put(8, new Pin(8, false));  // Wejście A3
        pins.put(9, new Pin(9, false));  // Wejście B3
        pins.put(10, new Pin(10, true));  // Wyjście Y3

        pins.put(11, new Pin(11, false)); // Wejście A4
        pins.put(12, new Pin(12, false)); // Wejście B4
        pins.put(13, new Pin(13, true));  // Wyjście Y4

        return pins;
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 3; i++) {
            Pin input1 = pins.get(i * 2 - 1);
            Pin input2 = pins.get(i * 2);
            Pin output = pins.get(pins.size() / 3 + i);

            if (input1.getState() == PinState.LOW && input2.getState() == PinState.LOW) {
                output.setState(PinState.HIGH);
            } else {
                output.setState(PinState.LOW);
            }
        }
    }
}
