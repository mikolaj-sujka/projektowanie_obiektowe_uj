package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7431 extends Component {

    public Component7431(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Wejścia
        pins.put(1, new Pin(1, false));  // Wejście 1A (NOT)
        pins.put(3, new Pin(3, false));  // Wejście 2A (BUFOR)
        pins.put(5, new Pin(5, false));  // Wejście 3A (NAND)
        pins.put(6, new Pin(6, false));  // Wejście 3B (NAND)
        pins.put(11, new Pin(11, false));  // Wejście 3C (NAND)

        pins.put(13, new Pin(13, false));  // Wejście 4A (BUFOR)
        pins.put(15, new Pin(15, false));  // Wejście 5A (NOT)

        pins.put(10, new Pin(10, false));  // Wejście 6B (NAND)
        pins.put(9, new Pin(9, false));    // Wejście 6A (NAND)

        // Wyjścia
        pins.put(2, new Pin(2, true));  // Wyjście 1Y (NOT)
        pins.put(4, new Pin(4, true));  // Wyjście 2Y (BUFOR)
        pins.put(7, new Pin(7, true));  // Wyjście 3Y (NAND)

        pins.put(12, new Pin(12, true));  // Wyjście 4Y (BUFOR)
        pins.put(14, new Pin(14, true));  // Wyjście 5Y (NOT)

        pins.put(8, new Pin(8, true));   // Wyjście 6Y (NAND)

        return pins;
    }

    @Override
    public void performLogic() {
        performNotGate(1, 2);   // NOT: 1A -> 1Y
        performBufferGate(3, 4); // BUFOR: 2A -> 2Y
        performNandGate(5, 6, 7); // NAND: 3A, 3B -> 3Y

        performBufferGate(13, 12); // BUFOR: 4A -> 4Y
        performNotGate(15, 14);  // NOT: 5A -> 5Y

        performNandGate(11, 10, 9); // NAND: 6A, 6B -> 6Y
    }

    private void performNotGate(int inputPin, int outputPin) {
        Pin input = pins.get(inputPin);
        Pin output = pins.get(outputPin);

        if (input == null || output == null) {
            return;  // Sprawdzenie, czy pin istnieje, żeby uniknąć NullPointerException
        }

        if (input.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (input.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.HIGH);
        }
    }

    private void performBufferGate(int inputPin, int outputPin) {
        Pin input = pins.get(inputPin);
        Pin output = pins.get(outputPin);

        if (input == null || output == null) {
            return;  // Sprawdzenie, czy pin istnieje, żeby uniknąć NullPointerException
        }

        if (input.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else {
            output.setState(input.getState());
        }
    }

    private void performNandGate(int inputPinA, int inputPinB, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin output = pins.get(outputPin);

        if (inputA == null || inputB == null || output == null) {
            return;  // Sprawdzenie, czy pin istnieje, żeby uniknąć NullPointerException
        }

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.HIGH);
        }
    }
}
