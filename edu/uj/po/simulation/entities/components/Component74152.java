package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.*;
import java.util.List;

public class Component74152 extends Component {

    public Component74152(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(6);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13);
    }

    @Override
    public void performLogic() throws UnknownStateException {
        Pin A = pins.get(10);
        Pin B = pins.get(9);
        Pin C = pins.get(8);
        Pin W = pins.get(6);

        if (A.getState() == PinState.UNKNOWN) {
            W.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), A.getPinNumber(), A.getState()));
        }
        if (B.getState() == PinState.UNKNOWN) {
            W.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), B.getPinNumber(), B.getState()));
        }
        if (C.getState() == PinState.UNKNOWN) {
            W.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), C.getPinNumber(), C.getState()));
        }

        int index = (booleanToInt(C.getState()) << 2) | (booleanToInt(B.getState()) << 1) | booleanToInt(A.getState());

        Pin selectedInput = getSelectedInput(index);

        if (selectedInput.getState() == PinState.UNKNOWN) {
            W.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), selectedInput.getPinNumber(), selectedInput.getState()));
        } else {
            PinState outputState = selectedInput.getState() == PinState.HIGH ? PinState.LOW : PinState.HIGH;
            W.setState(outputState);
        }
    }

    private Pin getSelectedInput(int index) {
        switch (index) {
            case 0: return pins.get(5); // D0
            case 1: return pins.get(4); // D1
            case 2: return pins.get(3); // D2
            case 3: return pins.get(2); // D3
            case 4: return pins.get(1); // D4
            case 5: return pins.get(13); // D5
            case 6: return pins.get(12); // D6
            case 7: return pins.get(11); // D7
            default: throw new IllegalStateException("Unexpected index: " + index);
        }
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}
