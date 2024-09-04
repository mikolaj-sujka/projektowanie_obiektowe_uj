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

        pins.put(15, new Pin(15, false));  // Wejście A
        pins.put(14, new Pin(14, false));  // Wejście B
        pins.put(13, new Pin(13, false));  // Wejście C
        pins.put(12, new Pin(12, false));  // Wejście D

        pins.put(1, new Pin(1, true));   // Wyjście 0
        pins.put(2, new Pin(2, true));   // Wyjście 1
        pins.put(3, new Pin(3, true));   // Wyjście 2
        pins.put(4, new Pin(4, true));   // Wyjście 3
        pins.put(5, new Pin(5, true));   // Wyjście 4
        pins.put(6, new Pin(6, true)); // Wyjście 5
        pins.put(7, new Pin(7, true)); // Wyjście 6
        pins.put(9, new Pin(9, true)); // Wyjście 7
        pins.put(10, new Pin(10, true)); // Wyjście 8
        pins.put(11, new Pin(11, true)); // Wyjście 9

        return pins;
    }

    @Override
    public void performLogic() {
        int grayValue = 0;

        // Sprawdzanie stanu wejść i obliczanie wartości w kodzie GRAY'a
        if (pins.get(15).getState() == PinState.HIGH) {
            grayValue |= 1 << 3; // A = 1
        }
        if (pins.get(14).getState() == PinState.HIGH) {
            grayValue |= 1 << 2; // B = 1
        }
        if (pins.get(13).getState() == PinState.HIGH) {
            grayValue |= 1 << 1; // C = 1
        }
        if (pins.get(12).getState() == PinState.HIGH) {
            grayValue |= 1; // D = 1
        }

        int binaryValue = grayToBinary(grayValue);

        // Ustawienie wszystkich wyjść na HIGH, pomijając pin 8
        for (int i = 1; i <= 11; i++) {
            if (i != 8) {
                pins.get(i).setState(PinState.HIGH);
            }
        }

        // Ustawienie odpowiedniego wyjścia na LOW
        if (binaryValue >= 0 && binaryValue <= 9) {
            pins.get(binaryValue + 1).setState(PinState.LOW); // Piny Y0-Y9 (pomijamy pin 8)
        }
    }

    private int grayToBinary(int gray) {
        int binary = gray;
        while ((gray >>= 1) != 0) {
            binary ^= gray;
        }
        return binary;
    }
}
