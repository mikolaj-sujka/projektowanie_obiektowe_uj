package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class OrGateComponent extends Component {

    public OrGateComponent(int id, int numberOfGates) {
        super(id);
        for (int i = 1; i <= numberOfGates; i++) {
            addPin(i * 2 - 1, new Pin(i * 2 - 1, false)); // Wejście 1
            addPin(i * 2, new Pin(i * 2, false));         // Wejście 2
            addPin(numberOfGates * 2 + i, new Pin(numberOfGates * 2 + i, true)); // Wyjście
        }
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 3; i++) {
            Pin input1 = pins.get(i * 2 - 1);
            Pin input2 = pins.get(i * 2);
            Pin output = pins.get(pins.size() / 3 + i);
            if (input1.getState() == PinState.HIGH || input2.getState() == PinState.HIGH) {
                output.setState(PinState.HIGH);
            } else {
                output.setState(PinState.LOW);
            }
        }
    }
}