package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component74138 extends Component {

    public Component74138(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(7, 9, 10, 11, 12, 13, 14, 15);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 3, 4, 5, 6);
    }

    @Override
    public void performLogic() {
        Pin G1 = pins.get(6);
        Pin G2A = pins.get(4);
        Pin G2B = pins.get(5);
        Pin A = pins.get(1);
        Pin B = pins.get(2);
        Pin C = pins.get(3);

        if (G1.getState() == PinState.LOW || G2A.getState() == PinState.HIGH || G2B.getState() == PinState.HIGH) {
            setAllOutputsToHigh();
            return;
        }

        int index = (booleanToInt(C.getState()) << 2) | (booleanToInt(B.getState()) << 1) | booleanToInt(A.getState());

        setAllOutputsToHigh();
        setOutputLow(index);
    }

    private void setAllOutputsToHigh() {
        for (int i = 15; i >= 7; i--) {
            if (i != 8) { // Pin 8 jest pomijany
                pins.get(i).setState(PinState.HIGH);
            }
        }
    }

    private void setOutputLow(int index) {
        int outputPin = 15 - index;  // Mapowanie indeksu na numer pinu wyjścia
        if (outputPin != 8) { // Pin 8 jest pomijany
            pins.get(outputPin).setState(PinState.LOW);
        }
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}