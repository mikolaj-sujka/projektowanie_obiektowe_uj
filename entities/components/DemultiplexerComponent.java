package entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import entities.Component;
import entities.Pin;

public class DemultiplexerComponent extends Component {

    public DemultiplexerComponent(int id) {
        super(id);
        // Demultiplexer ma 3 wejścia selektora i 8 wyjść
        for (int i = 1; i <= 3; i++) {
            addPin(i, new Pin(i, false));  // Wejścia selektora
        }
        for (int i = 4; i <= 11; i++) {
            addPin(i, new Pin(i, true));  // Wyjścia demultipleksera
        }
    }

    @Override
    public void performLogic() {
        // Odczytaj wartość selektora (3-bitowy kod)
        int selectorValue = 0;
        if (pins.get(1).getState() == PinState.HIGH) selectorValue |= 1;
        if (pins.get(2).getState() == PinState.HIGH) selectorValue |= 2;
        if (pins.get(3).getState() == PinState.HIGH) selectorValue |= 4;

        // Wyłącz wszystkie wyjścia
        for (int i = 4; i <= 11; i++) {
            pins.get(i).setState(PinState.LOW);
        }

        // Ustaw wybrane wyjście na HIGH na podstawie wartości selektora
        pins.get(4 + selectorValue).setState(PinState.HIGH);
    }
}
