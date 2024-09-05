package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import java.util.List;

public class Component7442 extends Component {

    public Component7442(int id) {
        super(id,false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(1, 2, 3, 4, 5, 6, 7, 9, 10, 11);
    }

    private static List<Integer> createInputPins() {
        return List.of(12, 13, 14, 15);
    }

    @Override
    public void performLogic() {
        for (int i = 12; i <= 15; i++) {
            if (pins.get(i).getState() == PinState.UNKNOWN) {
                setAllOutputsToUnknown();
                return;
            }
        }

        int value = 0;
        if (pins.get(15).getState() == PinState.HIGH) {
            value += 1;
        }
        if (pins.get(14).getState() == PinState.HIGH) {
            value += 2;
        }
        if (pins.get(13).getState() == PinState.HIGH) {
            value += 4;
        }
        if (pins.get(12).getState() == PinState.HIGH) {
            value += 8;
        }

        for (int i = 1; i <= 11; i++) {
            if (i != 8) {  // Pin 8 jest nieużywany, należy go pominąć
                pins.get(i).setState(PinState.HIGH);
            }
        }

        if (value >= 0 && value <= 9) {
            if (value >= 7) {
                pins.get(value + 2).setState(PinState.LOW); // Przesunięcie o +2, aby ominąć pin 8
            } else {
                pins.get(value + 1).setState(PinState.LOW); // Y0-Y6 są bezpośrednio zmapowane
            }
        }
    }

    private void setAllOutputsToUnknown() {
        for (int i = 1; i <= 11; i++) {
            if (i != 8) {  // Pin 8 jest nieużywany, należy go pominąć
                pins.get(i).setState(PinState.UNKNOWN);
            }
        }
    }
}
