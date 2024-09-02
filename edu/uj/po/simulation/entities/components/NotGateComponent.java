package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.ShortCircuitException;

public class NotGateComponent extends Component {

    public NotGateComponent(int id, int numberOfGates) {
        super(id);
        if (numberOfGates != 6) {
            throw new IllegalArgumentException("Układ 7404 ma dokładnie 6 bramek NOT.");
        }

        addPin(1, new Pin(1, false));  // 1A - Wejście
        addPin(2, new Pin(2, true));   // 1Y - Wyjście
        addPin(3, new Pin(3, false));  // 2A - Wejście
        addPin(4, new Pin(4, true));   // 2Y - Wyjście
        addPin(5, new Pin(5, false));  // 3A - Wejście
        addPin(6, new Pin(6, true));   // 3Y - Wyjście
        addPin(7, new Pin(7, false));  // GND - Zasilanie (pominąć w logice)

        addPin(8, new Pin(8, true));   // 4Y - Wyjście
        addPin(9, new Pin(9, false));  // 4A - Wejście
        addPin(10, new Pin(10, true)); // 5Y - Wyjście
        addPin(11, new Pin(11, false)); // 5A - Wejście
        addPin(12, new Pin(12, true)); // 6Y - Wyjście
        addPin(13, new Pin(13, false)); // 6A - Wejście
        addPin(14, new Pin(14, false)); // Vcc - Zasilanie (pominąć w logice)
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 2; i++) {
            Pin input = pins.get(i);
            Pin output = pins.get(pins.size() / 2 + i);

            if (isPowerPin(input.getPinNumber())  || isPowerPin(output.getPinNumber())) {
                continue;
            }

            if (input.getState() == PinState.HIGH) {
                output.setState(PinState.LOW);
            } else if (input.getState() == PinState.LOW) {
                output.setState(PinState.HIGH);
            } else {
                output.setState(PinState.UNKNOWN);
            }
        }
    }

    @Override
    public void validateConnection(Pin pin1, Pin pin2, int component1, int component2) throws ShortCircuitException {
        if (!pin1.isOutput() && !pin2.isOutput() && component1 == component2) {
            if (!(pin1.getPinNumber() == 14 && pin2.getPinNumber() == 7) && !(pin1.getPinNumber() == 7 && pin2.getPinNumber() == 14)) {
                throw new ShortCircuitException();
            }
        }
    }
}