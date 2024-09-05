package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.PinState;
import java.util.List;

public class Component7434 extends Component {

    public Component7434(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(2, 4, 6, 8, 10, 12);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 3, 5, 9, 11, 13);
    }

    @Override
    public void performLogic() {
        // Iterujemy przez pary wejście-wyjście
        int[][] pinPairs = {
                {1, 2},  // 1A -> 1Y
                {3, 4},  // 2A -> 2Y
                {5, 6},  // 3A -> 3Y
                {9, 8},  // 4A -> 4Y
                {11, 10},// 5A -> 5Y
                {13, 12} // 6A -> 6Y
        };

        for (int[] pair : pinPairs) {
            Pin input = pins.get(pair[0]);
            Pin output = pins.get(pair[1]);

            // Sprawdzenie, czy piny istnieją, aby uniknąć NullPointerException
            if (input != null && output != null) {
                if (input.getState() == PinState.UNKNOWN) {
                    output.setState(PinState.UNKNOWN);
                } else if (input.getState() == PinState.HIGH) {
                    output.setState(PinState.LOW);  // Inwersja
                } else {
                    output.setState(PinState.HIGH); // Inwersja
                }
            }
        }
    }
}
