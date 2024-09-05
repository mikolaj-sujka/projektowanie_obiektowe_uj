package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component7431 extends Component {

    public Component7431(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(2, 4, 7, 9, 12, 14);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 3, 5, 6, 10, 11 ,13, 15);
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
