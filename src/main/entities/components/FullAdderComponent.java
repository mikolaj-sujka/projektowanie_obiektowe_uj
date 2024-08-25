package src.main.entities.components;

import src.main.edu.uj.po.simulation.interfaces.PinState;
import src.main.entities.Component;
import src.main.entities.Pin;

public class FullAdderComponent extends Component {

    public FullAdderComponent(int id) {
        super(id);
        // Full Adder ma 3 wejścia (2 bity + carry in) i 2 wyjścia (sum i carry out)
        for (int i = 1; i <= 3; i++) {
            addPin(i, new Pin(i, false));  // Wejścia
        }
        addPin(4, new Pin(4, true));  // Sum
        addPin(5, new Pin(5, true));  // Carry Out
    }

    @Override
    public void performLogic() {
        Pin input1 = pins.get(1);
        Pin input2 = pins.get(2);
        Pin carryIn = pins.get(3);
        Pin sum = pins.get(4);
        Pin carryOut = pins.get(5);

        // Implementacja logiki pełnego sumatora
        boolean sumResult = (input1.getState() == PinState.HIGH) ^
                (input2.getState() == PinState.HIGH) ^
                (carryIn.getState() == PinState.HIGH);

        boolean carryOutResult = ((input1.getState() == PinState.HIGH) && (input2.getState() == PinState.HIGH)) ||
                ((input1.getState() == PinState.HIGH) && (carryIn.getState() == PinState.HIGH)) ||
                ((input2.getState() == PinState.HIGH) && (carryIn.getState() == PinState.HIGH));

        sum.setState(sumResult ? PinState.HIGH : PinState.LOW);
        carryOut.setState(carryOutResult ? PinState.HIGH : PinState.LOW);
    }
}
