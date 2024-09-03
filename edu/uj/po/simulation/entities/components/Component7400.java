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

        pins.put(9, new Pin(9, false)); // Wejscie A3
        pins.put(10, new Pin(10, false)); // Wejscia B3
        pins.put(8, new Pin(8, true)); // Wejscie Y3

        pins.put(11, new Pin(11, true)); // Wyjscie Y4
        pins.put(12, new Pin(12, true)); // Wejscia A4
        pins.put(13, new Pin(13, true)); // Wejscia B4

        return pins;
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 3);  // Pierwsza bramka NAND (1A, 1B -> 1Y)
        performLogicForGate(4, 5, 6);  // Druga bramka NAND (2A, 2B -> 2Y)
        performLogicForGate(9, 10, 8); // Trzecia bramka NAND (3A, 3B -> 3Y)
        performLogicForGate(12, 13, 11); // Czwarta bramka NAND (4A, 4B -> 4Y)
    }
    private void performLogicForGate(int inputPinA, int inputPinB, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.LOW); // Logika NAND: jeśli oba wejścia są HIGH, wyjście jest LOW
        } else {
            output.setState(PinState.HIGH); // W przeciwnym razie wyjście jest HIGH
        }
    }

}
