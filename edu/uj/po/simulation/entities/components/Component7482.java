package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.interfaces.*;
import java.util.List;

public class Component7482 extends Component {

    public Component7482(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(1, 10, 12);
    }

    private static List<Integer> createInputPins() {
        return List.of(2, 3, 5, 13, 14);
    }

    @Override
    public void performLogic() throws UnknownStateException {
        Pin inputA1 = pins.get(2);
        Pin inputB1 = pins.get(3);
        Pin inputC0 = pins.get(5);
        Pin inputA2 = pins.get(14);
        Pin inputB2 = pins.get(13);

        Pin outputS1 = pins.get(1);
        Pin outputS2 = pins.get(12);
        Pin outputC2 = pins.get(10);

        if (inputA1.getState() == PinState.UNKNOWN || inputB1.getState() == PinState.UNKNOWN ||
                inputC0.getState() == PinState.UNKNOWN || inputA2.getState() == PinState.UNKNOWN ||
                inputB2.getState() == PinState.UNKNOWN) {
            outputS1.setState(PinState.UNKNOWN);
            outputS2.setState(PinState.UNKNOWN);
            outputC2.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), inputA1.getPinNumber(), inputA1.getState()));
        }

        boolean a1 = inputA1.getState() == PinState.HIGH;
        boolean b1 = inputB1.getState() == PinState.HIGH;
        boolean c0 = inputC0.getState() == PinState.HIGH;
        boolean a2 = inputA2.getState() == PinState.HIGH;
        boolean b2 = inputB2.getState() == PinState.HIGH;

        boolean s1 = a1 ^ b1 ^ c0;
        boolean carry1 = (a1 && b1) || (b1 && c0) || (a1 && c0);

        boolean s2 = a2 ^ b2 ^ carry1;
        boolean carry2 = (a2 && b2) || (b2 && carry1) || (a2 && carry1);

        outputS1.setState(s1 ? PinState.HIGH : PinState.LOW);
        outputS2.setState(s2 ? PinState.HIGH : PinState.LOW);
        outputC2.setState(carry2 ? PinState.HIGH : PinState.LOW);
    }
}