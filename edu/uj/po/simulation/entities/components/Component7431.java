package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7431 extends Component {

    public Component7431(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Tworzenie pinów wejściowych
        pins.put(1, new Pin(1, false));  // Wejście dla NOT (1A)
        pins.put(3, new Pin(3, false));  // Wejście dla BUFOR (2A)
        pins.put(5, new Pin(5, false));  // Wejście dla NAND (3A)
        pins.put(6, new Pin(6, false));  // Wejście dla NAND (3B)
        pins.put(11, new Pin(11, false));  // Wejście dla NAND (6A)
        pins.put(10, new Pin(10, false));  // Wejście dla NAND (6B)
        pins.put(13, new Pin(13, false));  // Wejście dla BUFOR (4A)
        pins.put(15, new Pin(15, false));  // Wejście dla NOT (5A)

        // Tworzenie pinów wyjściowych
        pins.put(2, new Pin(2, true));  // Wyjście dla NOT (1Y)
        pins.put(4, new Pin(4, true));  // Wyjście dla BUFOR (2Y)
        pins.put(7, new Pin(7, true));  // Wyjście dla NAND (3Y)
        pins.put(12, new Pin(12, true));  // Wyjście dla BUFOR (4Y)
        pins.put(14, new Pin(14, true));  // Wyjście dla NOT (5Y)
        pins.put(9, new Pin(9, true));   // Wyjście dla NAND (6Y)

        return pins;
    }

    @Override
    public void performLogic() {
        // Implementacja bramek logicznych
        performNandGate(5, 6, 7);  // NAND: 3A, 3B -> 3Y
        performNandGate(11, 10, 9); // NAND: 6A, 6B -> 6Y

        performNotGate(1, 2);   // NOT: 1A -> 1Y
        performNotGate(15, 14);  // NOT: 5A -> 5Y

        performBufferGate(3, 4); // BUFOR: 2A -> 2Y
        performBufferGate(13, 12); // BUFOR: 4A -> 4Y
    }

    // Logika bramek NAND
    private void performNandGate(int inputPinA, int inputPinB, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin output = pins.get(outputPin);

        // Obsługa przypadków UNKNOWN
        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);  // NAND: jeśli oba wejścia są HIGH, wyjście jest LOW
        } else {
            output.setState(PinState.HIGH); // W przeciwnym razie wyjście jest HIGH
        }
    }

    // Logika bramek NOT
    private void performNotGate(int inputPin, int outputPin) {
        Pin input = pins.get(inputPin);
        Pin output = pins.get(outputPin);

        // Obsługa przypadków UNKNOWN
        if (input.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (input.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);  // NOT: HIGH -> LOW
        } else {
            output.setState(PinState.HIGH); // NOT: LOW -> HIGH
        }
    }

    // Logika bramek BUFOR
    private void performBufferGate(int inputPin, int outputPin) {
        Pin input = pins.get(inputPin);
        Pin output = pins.get(outputPin);

        // Obsługa przypadków UNKNOWN
        if (input.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else {
            output.setState(input.getState()); // BUFOR: przekazuje stan z wejścia na wyjście
        }
    }
}
