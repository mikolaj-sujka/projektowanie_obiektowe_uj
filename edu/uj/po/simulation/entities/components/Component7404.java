package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component7404 extends Component {

    public Component7404(int id) {
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
        // Logika dla poszczególnych par pinów
        invertPin(1, 2);
        invertPin(3, 4);
        invertPin(5, 6);
        invertPin(9, 8);
        invertPin(11, 10);
        invertPin(13, 12);
    }

    private void invertPin(int inputPin, int outputPin) {
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
}