package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import java.util.List;
import java.util.Map;

public class Component7444 extends Component {

    private static final Map<String, Integer> greyCodeMap = Map.of(
            "0010", 0,
            "0110", 1,
            "0111", 2,
            "0101", 3,
            "0100", 4,
            "1100", 5,
            "1101", 6,
            "1111", 7,
            "1110", 8,
            "1010", 9
    );

    public Component7444(int id) {
        super(id, false, createInputPins(), createOutputPins());
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

        StringBuilder greyCode = new StringBuilder();
        greyCode.append(pins.get(15).getState() == PinState.HIGH ? "1" : "0"); // A
        greyCode.append(pins.get(14).getState() == PinState.HIGH ? "1" : "0"); // B
        greyCode.append(pins.get(13).getState() == PinState.HIGH ? "1" : "0"); // C
        greyCode.append(pins.get(12).getState() == PinState.HIGH ? "1" : "0"); // D

        int decimalValue = greyCodeMap.getOrDefault(greyCode.toString(), -1);

        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                pins.get(i).setState(PinState.HIGH);
            }
        }

        if (decimalValue >= 0 && decimalValue <= 9) {
            pins.get(decimalValue + 1).setState(PinState.LOW); // Y0-Y9
        }
    }

    private void setAllOutputsToUnknown() {
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                pins.get(i).setState(PinState.UNKNOWN);
            }
        }
    }
}
