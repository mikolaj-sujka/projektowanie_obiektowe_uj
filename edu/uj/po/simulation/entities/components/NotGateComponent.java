package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class NotGateComponent extends Component {

    public NotGateComponent(int id, int numberOfGates) {
        super(id);
        for (int i = 1; i <= numberOfGates; i++) {
            addPin(i, new Pin(i, false));  // Wejście
            addPin(numberOfGates + i, new Pin(numberOfGates + i, true)); // Wyjście
        }
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 2; i++) {
            Pin input = pins.get(i);
            Pin output = pins.get(pins.size() / 2 + i);
            if (input.getState() == PinState.HIGH) {
                output.setState(PinState.LOW);
            } else {
                output.setState(PinState.HIGH);
            }
        }
    }
}