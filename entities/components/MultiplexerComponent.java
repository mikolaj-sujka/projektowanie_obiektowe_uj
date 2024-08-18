package entities.components;

import entities.Component;
import entities.Pin;

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
        // Implementacja logiki multipleksera 8-to-1
        // np. Sygnał na wyjściu zależy od stanu wejść selektora
    }
}
