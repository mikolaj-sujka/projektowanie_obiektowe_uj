package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component74152 extends Component {

    public Component74152(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        for (int i = 1; i <= 8; i++) {
            pins.put(i, new Pin(i, false)); // Wejścia D0 - D7
        }

        pins.put(9, new Pin(9, false));  // Wejście S1
        pins.put(10, new Pin(10, false)); // Wejście S2
        pins.put(11, new Pin(11, false)); // Wejście S3
        pins.put(12, new Pin(12, false)); // Wejście G
        pins.put(13, new Pin(13, true));  // Wyjście Y

        return pins;
    }

    @Override
    public void performLogic() {
        Pin S1 = pins.get(9);
        Pin S2 = pins.get(10);
        Pin S3 = pins.get(11);
        Pin G = pins.get(12);
        Pin Y = pins.get(13);

        if (G.getState() == PinState.HIGH) {
            Y.setState(PinState.LOW); // Jeśli G jest HIGH, Y jest LOW
            return;
        }

        int index = (booleanToInt(S3.getState()) << 2) | (booleanToInt(S2.getState()) << 1) | booleanToInt(S1.getState());

        Pin selectedInput = pins.get(1 + index);

        if (selectedInput.getState() == PinState.UNKNOWN) {
            Y.setState(PinState.UNKNOWN);
        } else {
            Y.setState(selectedInput.getState());
        }
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}
