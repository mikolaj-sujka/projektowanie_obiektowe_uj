package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7444 extends Component {

    private static final Map<String, Integer> greyCodeMap = Map.of(
            "0010", 0,
            "0110", 1,
            "0111", 2,
            "0101", 3,
            "0100", 4,
            "1100", 5,
            "1101", 6,
            "1111", 7,
            "1110", 8,
            "1010", 9
    );

    public Component7444(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Wejścia
        pins.put(15, new Pin(15, false));  // Wejście A
        pins.put(14, new Pin(14, false));  // Wejście B
        pins.put(13, new Pin(13, false));  // Wejście C
        pins.put(12, new Pin(12, false));  // Wejście D

        // Wyjścia (pomijamy pin 8)
        pins.put(1, new Pin(1, true));   // Wyjście Y0
        pins.put(2, new Pin(2, true));   // Wyjście Y1
        pins.put(3, new Pin(3, true));   // Wyjście Y2
        pins.put(4, new Pin(4, true));   // Wyjście Y3
        pins.put(5, new Pin(5, true));   // Wyjście Y4
        pins.put(6, new Pin(6, true));   // Wyjście Y5
        pins.put(7, new Pin(7, true));   // Wyjście Y6
        pins.put(9, new Pin(9, true));   // Wyjście Y7
        pins.put(10, new Pin(10, true)); // Wyjście Y8
        pins.put(11, new Pin(11, true)); // Wyjście Y9

        return pins;
    }

    @Override
    public void performLogic() {
        // Sprawdzanie, czy którykolwiek z pinów wejściowych jest w stanie UNKNOWN
        for (int i = 12; i <= 15; i++) {
            if (pins.get(i).getState() == PinState.UNKNOWN) {
                setAllOutputsToUnknown();
                return;
            }
        }

        // Obliczanie wartości kodu Gray'a
        StringBuilder grayCode = new StringBuilder();
        grayCode.append(pins.get(15).getState() == PinState.HIGH ? "1" : "0"); // A
        grayCode.append(pins.get(14).getState() == PinState.HIGH ? "1" : "0"); // B
        grayCode.append(pins.get(13).getState() == PinState.HIGH ? "1" : "0"); // C
        grayCode.append(pins.get(12).getState() == PinState.HIGH ? "1" : "0"); // D

        int decimalValue = greyCodeMap.getOrDefault(grayCode.toString(), 10);

        // Ustawienie wszystkich wyjść na HIGH
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                pins.get(i).setState(PinState.HIGH);
            }
        }

        // Ustawienie odpowiedniego wyjścia na LOW
        if (decimalValue >= 0 && decimalValue <= 9) {
            pins.get(decimalValue + 1).setState(PinState.LOW); // Y0-Y9
        }
    }

    private void setAllOutputsToUnknown() {
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                pins.get(i).setState(PinState.UNKNOWN);
            }
        }
    }
}
