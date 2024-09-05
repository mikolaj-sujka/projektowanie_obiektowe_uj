package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component7402 extends Component {

    public Component7402(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(3, 6, 10, 13);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 4, 5, 8, 9, 11, 12);
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 3; i++) {
            Pin input1 = pins.get(i * 2 - 1);
            Pin input2 = pins.get(i * 2);
            Pin output = pins.get(pins.size() / 3 + i);

            if (input1.getState() == PinState.LOW && input2.getState() == PinState.LOW) {
                output.setState(PinState.HIGH);
            } else {
                output.setState(PinState.LOW);
            }
        }
    }
}
