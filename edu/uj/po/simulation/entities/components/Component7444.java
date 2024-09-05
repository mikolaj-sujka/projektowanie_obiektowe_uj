package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
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
            Pin pin = pins.get(i);
            if (pin == null || pin.getState() == PinState.UNKNOWN) {
                setAllOutputsToUnknown();
                return;
            }
        }

        StringBuilder grayCode = new StringBuilder();
        grayCode.append(pins.get(15).getState() == PinState.HIGH ? "1" : "0"); // A
        grayCode.append(pins.get(14).getState() == PinState.HIGH ? "1" : "0"); // B
        grayCode.append(pins.get(13).getState() == PinState.HIGH ? "1" : "0"); // C
        grayCode.append(pins.get(12).getState() == PinState.HIGH ? "1" : "0"); // D

        int decimalValue = greyCodeMap.getOrDefault(grayCode.toString(), -1);

        setAllOutputsToHigh();

        if (decimalValue >= 0 && decimalValue <= 9 && decimalValue != 7) {
            Pin selectedOutput = pins.get(decimalValue + 1);
            if (selectedOutput != null) {
                selectedOutput.setState(PinState.LOW);
            } else {
                throw new IllegalStateException("Pin " + (decimalValue + 1) + " not found");
            }
        }
    }

    private void setAllOutputsToUnknown() {
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                Pin pin = pins.get(i);
                if (pin != null) {
                    pin.setState(PinState.UNKNOWN);
                }
            }
        }
    }

    private void setAllOutputsToHigh() {
        for (int i = 1; i <= 11; i++) {
            if (i != 8) { // Pomijamy pin 8
                Pin pin = pins.get(i);
                if (pin != null) {
                    pin.setState(PinState.HIGH);
                }
            }
        }
    }
}
