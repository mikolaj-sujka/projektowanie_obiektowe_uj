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

        // Wejścia
        pins.put(6, new Pin(6, false));  // Wejście G1
        pins.put(4, new Pin(4, false));  // Wejście G2A
        pins.put(5, new Pin(5, false));  // Wejście G2B
        pins.put(1, new Pin(1, false));  // Wejście A
        pins.put(2, new Pin(2, false));  // Wejście B
        pins.put(3, new Pin(3, false));  // Wejście C

        // Wyjścia Y0–Y7 (piny 15–9)
        pins.put(15, new Pin(15, true)); // Wyjście Y0
        pins.put(14, new Pin(14, true)); // Wyjście Y1
        pins.put(13, new Pin(13, true)); // Wyjście Y2
        pins.put(12, new Pin(12, true)); // Wyjście Y3
        pins.put(11, new Pin(11, true)); // Wyjście Y4
        pins.put(10, new Pin(10, true)); // Wyjście Y5
        pins.put(9, new Pin(9, true));   // Wyjście Y6
        pins.put(7, new Pin(7, true));   // Wyjście Y7

        return pins;
    }

    @Override
    public void performLogic() {
        Pin G1 = pins.get(6);
        Pin G2A = pins.get(4);
        Pin G2B = pins.get(5);
        Pin A = pins.get(1);
        Pin B = pins.get(2);
        Pin C = pins.get(3);

        // Jeśli G1 jest LOW, G2A lub G2B są HIGH, to wszystkie wyjścia są HIGH
        if (G1.getState() == PinState.LOW || G2A.getState() == PinState.HIGH || G2B.getState() == PinState.HIGH) {
            for (int i = 15; i >= 7; i--) {
                if (i != 8) { // Pomijamy pin 8
                    pins.get(i).setState(PinState.HIGH);
                }
            }
            return;
        }

        // Obliczenie indeksu wyjścia na podstawie stanów A, B i C
        int index = (booleanToInt(C.getState()) << 2) | (booleanToInt(B.getState()) << 1) | booleanToInt(A.getState());

        // Ustawienie wszystkich wyjść na HIGH
        for (int i = 15; i >= 7; i--) {
            if (i != 8) { // Pomijamy pin 8
                pins.get(i).setState(PinState.HIGH);
            }
        }

        // Ustawienie wybranego wyjścia na LOW
        int outputPin = 15 - index;
        if (outputPin != 8) { // Pomijamy pin 8
            pins.get(outputPin).setState(PinState.LOW);
        }
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}