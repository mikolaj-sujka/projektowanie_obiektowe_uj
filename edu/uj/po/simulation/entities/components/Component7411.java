package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.PinState;
import java.util.List;

public class Component7411 extends Component {

    public Component7411(int id) {
        super(id,false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(12, 6, 8);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 13, 5, 3, 4, 9, 10, 11);
    }

    @Override
    public void performLogic() {
        // Logika bramek AND
        performLogicForGate(1, 2, 13, 12); // Bramka 1
        performLogicForGate(3, 4, 5, 6); // Bramka 2
        performLogicForGate(9, 10, 11, 8); // Bramka 3
    }

    private void performLogicForGate(int inputPinA, int inputPinB, int inputPinC, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin inputC = pins.get(inputPinC);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN || inputC.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH && inputC.getState() == PinState.HIGH) {
            output.setState(PinState.HIGH);
        } else {
            output.setState(PinState.LOW);
        }
    }
}
