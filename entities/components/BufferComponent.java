package entities.components;

import entities.Component;
import entities.Pin;

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
            output.setState(input.getState()); // Przekazuje stan wejścia na wyjście
        }
    }
}
