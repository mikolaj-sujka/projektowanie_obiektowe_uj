package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class MultiplexerComponent extends Component {

    public MultiplexerComponent(int id) {
        super(id);
        // Multiplexer ma 8 wejść i 1 wyjście
        for (int i = 1; i <= 8; i++) {
            addPin(i, new Pin(i, false));  // Wejścia multipleksera
        }
        addPin(9, new Pin(9, true));  // Wyjście multipleksera
    }

    @Override
    public void performLogic() {
        // Odczytaj wartość selektora (3-bitowy kod, zakładając 8-to-1)
        int selectorValue = 0;
        if (pins.get(1).getState() == PinState.HIGH) selectorValue |= 1;
        if (pins.get(2).getState() == PinState.HIGH) selectorValue |= 2;
        if (pins.get(3).getState() == PinState.HIGH) selectorValue |= 4;

        // Ustaw wyjście na stan wybranego wejścia
        Pin selectedInput = pins.get(selectorValue + 1);
        pins.get(9).setState(selectedInput.getState());
    }
}
