package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.PinState;

import java.util.HashMap;
import java.util.Map;

public class Component7410 extends Component {

    public Component7410(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Poprawiona numeracja pinów
        pins.put(1, new Pin(1, false));  // Wejście A1
        pins.put(2, new Pin(2, false));  // Wejście B1
        pins.put(13, new Pin(13, false));  // Wejście C1
        pins.put(12, new Pin(12, true));   // Wyjście Y1

        pins.put(5, new Pin(5, false));  // Wejście A2
        pins.put(3, new Pin(3, false));  // Wejście B2
        pins.put(4, new Pin(4, false));  // Wejście C2
        pins.put(6, new Pin(6, true));   // Wyjście Y2

        pins.put(9, new Pin(9, false));  // Wejście A3
        pins.put(10, new Pin(10, false)); // Wejście B3
        pins.put(11, new Pin(11, false)); // Wejście C3
        pins.put(8, new Pin(8, true));  // Wyjście Y3

        return pins;
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 13, 12); // Pierwsza bramka NAND
        performLogicForGate(5, 3, 4, 6);   // Druga bramka NAND
        performLogicForGate(9, 10, 11, 8); // Trzecia bramka NAND
    }

    private void performLogicForGate(int inputAPin, int inputBPin, int inputCPin, int outputPin) {
        Pin inputA = pins.get(inputAPin);
        Pin inputB = pins.get(inputBPin);
        Pin inputC = pins.get(inputCPin);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN || inputC.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH && inputC.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);  // Bramka NAND: HIGH + HIGH + HIGH = LOW
        } else {
            output.setState(PinState.HIGH); // Bramka NAND: każda inna kombinacja = HIGH
        }
    }
}