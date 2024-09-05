package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.UnknownStateException;

import java.util.HashMap;
import java.util.Map;

public class Component74152 extends Component {

    public Component74152(int id) {
        super(id, createPins(), false);
    }

    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Tworzymy piny wejściowe D0 - D7
        for (int i = 1; i <= 8; i++) {
            pins.put(i, new Pin(i, false)); // Wejścia D0 - D7
        }

        // Tworzymy piny dla sygnałów sterujących S1, S2, S3 oraz G (Enable)
        pins.put(9, new Pin(9, false));  // Wejście S1
        pins.put(10, new Pin(10, false)); // Wejście S2
        pins.put(11, new Pin(11, false)); // Wejście S3
        pins.put(12, new Pin(12, false)); // Wejście G (Enable)

        // Wyjście Y
        pins.put(13, new Pin(13, true));  // Wyjście Y

        return pins;
    }

    @Override
    public void performLogic() throws UnknownStateException {
        Pin S1 = pins.get(9);
        Pin S2 = pins.get(10);
        Pin S3 = pins.get(11);
        Pin G = pins.get(12);
        Pin Y = pins.get(13);

        // Jeśli G (Enable) jest HIGH, wyjście Y jest zawsze LOW
        if (G.getState() == PinState.HIGH) {
            Y.setState(PinState.LOW);
            return;
        }

        // Sprawdzamy, czy S1, S2, S3 są w stanie UNKNOWN
        if (S1.getState() == PinState.UNKNOWN) {
            Y.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), S1.getPinNumber(), S1.getState()));
        }
        if (S2.getState() == PinState.UNKNOWN) {
            Y.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), S2.getPinNumber(), S2.getState()));
        }
        if (S3.getState() == PinState.UNKNOWN) {
            Y.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), S3.getPinNumber(), S3.getState()));
        }

        // Obliczamy indeks na podstawie stanów S1, S2, S3
        int index = (booleanToInt(S3.getState()) << 2) | (booleanToInt(S2.getState()) << 1) | booleanToInt(S1.getState());

        // Sprawdzamy stan wybranego wejścia D0-D7
        Pin selectedInput = pins.get(1 + index);

        // Jeśli wybrane wejście jest w stanie UNKNOWN, ustawiamy wyjście Y na UNKNOWN
        if (selectedInput.getState() == PinState.UNKNOWN) {
            Y.setState(PinState.UNKNOWN);
            throw new UnknownStateException(new ComponentPinState(getId(), selectedInput.getPinNumber(), selectedInput.getState()));
        } else {
            // Ustawiamy wyjście Y na stan wybranego wejścia
            Y.setState(selectedInput.getState());
        }
    }

    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}
