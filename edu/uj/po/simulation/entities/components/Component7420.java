package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.PinState;
import java.util.List;

public class Component7420 extends Component {

    public Component7420(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(6, 8);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 13, 5, 4, 9, 10, 12);
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 4, 5, 6);  // Pierwsza bramka NAND
        performLogicForGate(9, 10, 12, 13, 8);  // Druga bramka NAND
    }

    private void performLogicForGate(int inputPinA, int inputPinB, int inputPinC, int inputPinD, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin inputC = pins.get(inputPinC);
        Pin inputD = pins.get(inputPinD);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN ||
                inputC.getState() == PinState.UNKNOWN || inputD.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH &&
                inputC.getState() == PinState.HIGH && inputD.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.HIGH);
        }
    }
}