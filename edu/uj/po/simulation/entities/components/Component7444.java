package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7444 extends Component {

    public Component7444(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        pins.put(1, new Pin(1, false));  // Wejście A
        pins.put(2, new Pin(2, false));  // Wejście B
        pins.put(3, new Pin(3, false));  // Wejście C
        pins.put(4, new Pin(4, false));  // Wejście D

        pins.put(5, new Pin(5, true));   // Wyjście 0
        pins.put(6, new Pin(6, true));   // Wyjście 1
        pins.put(7, new Pin(7, true));   // Wyjście 2
        pins.put(8, new Pin(8, true));   // Wyjście 3
        pins.put(9, new Pin(9, true));   // Wyjście 4
        pins.put(10, new Pin(10, true)); // Wyjście 5
        pins.put(11, new Pin(11, true)); // Wyjście 6
        pins.put(12, new Pin(12, true)); // Wyjście 7
        pins.put(13, new Pin(13, true)); // Wyjście 8
        pins.put(14, new Pin(14, true)); // Wyjście 9

        return pins;
    }

    @Override
    public void performLogic() {
        // Dekodowanie kodu GRAY'a na 1 z 10
        int grayCode = 0;
        for (int i = 1; i <= 4; i++) {
            Pin input = pins.get(i);
            if (input.getState() == PinState.UNKNOWN) {
                pins.values().forEach(pin -> pin.setState(PinState.UNKNOWN));
                return;
            }
            if (input.getState() == PinState.HIGH) {
                grayCode |= 1 << (i - 1);
            }
        }

        int binaryCode = grayToBinary(grayCode);

        pins.values().forEach(pin -> pin.setState(PinState.LOW));
        if (binaryCode >= 0 && binaryCode <= 9) {
            pins.get(5 + binaryCode).setState(PinState.HIGH);
        }
    }

    private int grayToBinary(int gray) {
        int binary = 0;
        for (; gray != 0; gray = gray >> 1) {
            binary ^= gray;
        }
        return binary;
    }
}
