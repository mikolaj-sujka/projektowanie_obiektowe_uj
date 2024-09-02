package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.PinState;

import java.util.HashMap;
import java.util.Map;

public class Component7434 extends Component {

    public Component7434(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        for (int i = 1; i <= 6; i++) {
            pins.put(i, new Pin(i, false));  // Wejścia
            pins.put(i + 6, new Pin(i + 6, true)); // Wyjścia
        }

        return pins;
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= 6; i++) {
            Pin input = pins.get(i);
            Pin output = pins.get(i + 6);

            // Logika bufora - przekazuje stan wejścia na wyjście bez zmian
            if (input.getState() == PinState.UNKNOWN) {
                output.setState(PinState.UNKNOWN);
            } else if (input.getState() == PinState.HIGH) {
                output.setState(PinState.HIGH);
            } else {
                output.setState(PinState.LOW);
            }
        }
    }
}
