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

        pins.put(9, new Pin(9, false));  // Wejście A3
        pins.put(10, new Pin(10, false)); // Wejście B3
        pins.put(8, new Pin(8, true));   // Wyjście Y3

        pins.put(12, new Pin(12, false)); // Wejście A4
        pins.put(13, new Pin(13, false)); // Wejście B4
        pins.put(11, new Pin(11, true));  // Wyjście Y4

        return pins;
    }

    @Override
    public void performLogic() {
        // Pierwsza bramka NAND (A1, B1 -> Y1)
        performLogicForGate(1, 2, 3);
        // Druga bramka NAND (A2, B2 -> Y2)
        performLogicForGate(4, 5, 6);
        // Trzecia bramka NAND (A3, B3 -> Y3)
        performLogicForGate(9, 10, 8);
        // Czwarta bramka NAND (A4, B4 -> Y4)
        performLogicForGate(12, 13, 11);
    }

    private void performLogicForGate(int inputPinA, int inputPinB, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);  // Logika NAND
        } else {
            output.setState(PinState.HIGH); // Inaczej wyjście jest HIGH
        }
    }

}
