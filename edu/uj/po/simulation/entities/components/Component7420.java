package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.PinState;

import java.util.HashMap;
import java.util.Map;

public class Component7420 extends Component {

    public Component7420(int id) {
        super(id, createPins());
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Pierwsza bramka NAND
        pins.put(1, new Pin(1, false));  // Wejście 1A
        pins.put(2, new Pin(2, false));  // Wejście 1B
        pins.put(4, new Pin(4, false));  // Wejście 1C
        pins.put(5, new Pin(5, false));  // Wejście 1D
        pins.put(6, new Pin(6, true));   // Wyjście 1Y

        // Druga bramka NAND
        pins.put(9, new Pin(9, false));  // Wejście 2A
        pins.put(10, new Pin(10, false)); // Wejście 2B
        pins.put(12, new Pin(12, false)); // Wejście 2C
        pins.put(13, new Pin(13, false)); // Wejście 2D
        pins.put(8, new Pin(8, true));   // Wyjście 2Y

        return pins;
    }

    @Override
    public void performLogic() {
        performLogicForGate(1, 2, 4, 5, 6);  // Pierwsza bramka NAND
        performLogicForGate(9, 10, 12, 13, 8);  // Druga bramka NAND
    }

    private void performLogicForGate(int inputPinA, int inputPinB, int inputPinC, int inputPinD, int outputPin) {
        Pin inputA = pins.get(inputPinA);
        Pin inputB = pins.get(inputPinB);
        Pin inputC = pins.get(inputPinC);
        Pin inputD = pins.get(inputPinD);
        Pin output = pins.get(outputPin);

        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN ||
                inputC.getState() == PinState.UNKNOWN || inputD.getState() == PinState.UNKNOWN) {
            output.setState(PinState.UNKNOWN);
        } else if (inputA.getState() == PinState.HIGH && inputB.getState() == PinState.HIGH &&
                inputC.getState() == PinState.HIGH && inputD.getState() == PinState.HIGH) {
            output.setState(PinState.LOW);
        } else {
            output.setState(PinState.HIGH);
        }
    }
}