package src.main.entities.components;

import src.main.edu.uj.po.simulation.interfaces.PinState;
import src.main.entities.Component;
import src.main.entities.Pin;

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