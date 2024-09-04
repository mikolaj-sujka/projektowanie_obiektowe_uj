package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7442 extends Component {

    public Component7442(int id) {
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
        int value = 0;

        // Sprawdzanie stanu wejść i obliczanie wartości binarnej
        if (pins.get(15).getState() == PinState.HIGH) {
            value |= 1 << 0; // A = 1
        }
        if (pins.get(14).getState() == PinState.HIGH) {
            value |= 1 << 1; // B = 1
        }
        if (pins.get(13).getState() == PinState.HIGH) {
            value |= 1 << 2; // C = 1
        }
        if (pins.get(12).getState() == PinState.HIGH) {
            value |= 1 << 3; // D = 1
        }

        // Ustawienie wszystkich wyjść na HIGH, pomijając pin 8
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pin 8 jest nieużywany, należy go pominąć
                pins.get(i).setState(PinState.HIGH);
            }
        }

        // Jeśli wartość jest w zakresie 0-9, ustaw odpowiednie wyjście na LOW
        if (value >= 0 && value <= 9) {
            if (value >= 7) {
                // Piny są przesunięte po pinie 7
                pins.get(value + 2).setState(PinState.LOW); // Przesunięcie o +2 zamiast +1, aby ominąć pin 8
            } else {
                pins.get(value + 1).setState(PinState.LOW); // Y0-Y6 są bezpośrednio zmapowane
            }
        }
    }
}
