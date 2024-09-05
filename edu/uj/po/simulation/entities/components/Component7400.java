package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component7400 extends Component {
    public Component7400(int id) {
        super(id, false, createInputPins(), createOutputPins());

    }

    private static List<Integer> createOutputPins() {
        return List.of(3, 6, 8, 11);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 4, 5, 9, 10, 12, 13);
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 3);
        performLogicForGate(4, 5, 6);
        performLogicForGate(9, 10, 8);
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
