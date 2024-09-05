package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component7408 extends Component {
    public Component7408(int id) {
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
        performLogicForGate(1, 2, 3);  // Bramka 1 (1A, 1B -> 1Y)
        performLogicForGate(4, 5, 6);  // Bramka 2 (2A, 2B -> 2Y)
        performLogicForGate(9, 10, 8); // Bramka 3 (3A, 3B -> 3Y)
        performLogicForGate(12, 13, 11); // Bramka 4 (4A, 4B -> 4Y)
    }

    private void performLogicForGate(int pinA, int pinB, int pinY) {
        Pin inputA = pins.get(pinA);
        Pin inputB = pins.get(pinB);
        Pin output = pins.get(pinY);

        if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH) {
            output.setState(PinState.HIGH);
        } else if (inputA.getState() == PinState.LOW || inputB.getState() == PinState.LOW) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.UNKNOWN);
        }
    }
}
