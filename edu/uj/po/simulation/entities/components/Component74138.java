package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component74138 extends Component {

    public Component74138(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(1, new Pin(1, false));  // Wejście G1
        pins.put(2, new Pin(2, false));  // Wejście G2A
        pins.put(3, new Pin(3, false));  // Wejście G2B
        pins.put(4, new Pin(4, false));  // Wejście A
        pins.put(5, new Pin(5, false));  // Wejście B
        pins.put(6, new Pin(6, false));  // Wejście C

        for (int i = 7; i <= 14; i++) {
            pins.put(i, new Pin(i, true)); // Wyjścia Y0 - Y7
        }

        return pins;
    }

    @Override
    public void performLogic() {
        Pin G1 = pins.get(1);
        Pin G2A = pins.get(2);
        Pin G2B = pins.get(3);
        Pin A = pins.get(4);
        Pin B = pins.get(5);
        Pin C = pins.get(6);

        if (G1.getState() == PinState.LOW || G2A.getState() == PinState.HIGH || G2B.getState() == PinState.HIGH) {
            pins.values().forEach(pin -> pin.setState(PinState.HIGH)); // Wszystkie wyjścia w stanie HIGH
            return;
        }

        int index = (booleanToInt(C.getState()) << 2) | (booleanToInt(B.getState()) << 1) | booleanToInt(A.getState());

        pins.values().forEach(pin -> pin.setState(PinState.HIGH));
        pins.get(7 + index).setState(PinState.LOW); // Ustawienie odpowiedniego wyjścia na LOW
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}
