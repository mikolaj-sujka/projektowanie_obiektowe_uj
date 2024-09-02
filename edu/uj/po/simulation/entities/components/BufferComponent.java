package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class BufferComponent extends Component {

    public BufferComponent(int id, int numberOfBuffers) {
        super(id);
        for (int i = 1; i <= numberOfBuffers; i++) {
            addPin(i, new Pin(i, false));  // Wejście
            addPin(numberOfBuffers + i, new Pin(numberOfBuffers + i, true)); // Wyjście
        }
    }

    @Override
    public void performLogic() {
        for (int i = 1; i <= pins.size() / 2; i++) {
            Pin input = pins.get(i);
            Pin output = pins.get(pins.size() / 2 + i);

            if (isPowerPin(input.getPinNumber()) || isPowerPin(output.getPinNumber())) {
                continue;
            }

            output.setState(input.getState());
        }
    }
}
