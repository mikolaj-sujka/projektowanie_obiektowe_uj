package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class AndGateComponent extends Component {
    public AndGateComponent(int id, int numberOfGates) {
        super(id);
        for (int i = 0; i < numberOfGates; i++) {
            addPin(1 + 3 * i, new Pin(1 + 3 * i, false)); // input 1
            addPin(2 + 3 * i, new Pin(2 + 3 * i, false)); // input 2
            addPin(3 + 3 * i, new Pin(3 + 3 * i, true));  // output
        }
    }

    @Override
    public void performLogic() {
        for (int i = 0; i < pins.size() / 3; i++) {
            Pin input1 = pins.get(1 + 3 * i);
            Pin input2 = pins.get(2 + 3 * i);
            Pin output = pins.get(3 + 3 * i);

            // Upewnij się, że tylko jeśli oba wejścia mają stan HIGH, wyjście ma stan HIGH
            if (input1.getState() == PinState.HIGH && input2.getState() == PinState.HIGH) {
                output.setState(PinState.HIGH);
            } else if (input1.getState() == PinState.LOW && input2.getState() == PinState.LOW){
                output.setState(PinState.LOW);
            } else {
                output.setState(PinState.UNKNOWN);
            }
        }
    }
}
