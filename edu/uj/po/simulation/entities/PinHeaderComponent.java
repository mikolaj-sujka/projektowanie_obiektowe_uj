package edu.uj.po.simulation.entities;

import java.util.HashMap;
import java.util.Map;

public class PinHeaderComponent extends Component {
    private boolean isOutput = false;
    public PinHeaderComponent(int id, int size, boolean isOutput) {
        super(id,createPins(size, isOutput));
        this.isOutput = isOutput;
    }

    private static Map<Integer, Pin> createPins(int size, boolean isOutput) {
        Map<Integer, Pin> pins = new HashMap<Integer, Pin>();

        for (int i = 1; i <= size; i++) {
            Pin newPin = new Pin(i, isOutput);
            pins.put(newPin.getPinNumber(), newPin);
        }
        return pins;
    }

    @Override
    public void performLogic() {
        // Pin header nie wykonuje żadnej logiki
    }

    public boolean isOutput() {
        return this.isOutput;
    }
}
