package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component7400 extends Component {
    public Component7400(int id) {
        super(id, createPins());

    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Tworzenie pinów z pominięciem Vcc i GND
        pins.put(1, new Pin(1, false));  // Wejście A1
        pins.put(2, new Pin(2, false));  // Wejście B1
        pins.put(3, new Pin(3, true));   // Wyjście Y1

        pins.put(4, new Pin(4, false));  // Wejście A2
        pins.put(5, new Pin(5, false));  // Wejście B2
        pins.put(6, new Pin(6, true));   // Wyjście Y2

        return pins;
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size(); i += 3) {
            Pin inputA = pins.get(i);
            Pin inputB = pins.get(i + 1);
            Pin output = pins.get(i + 2);

            if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
                output.setState(PinState.UNKNOWN);
            } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
                output.setState(PinState.LOW);
            } else {
                output.setState(PinState.HIGH);
            }
        }
    }
}
