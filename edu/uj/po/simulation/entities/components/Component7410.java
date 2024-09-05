package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.PinState;
import java.util.List;

public class Component7410 extends Component {

    public Component7410(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(12, 6, 8);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 13, 5, 3, 4, 9, 10, 11);
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