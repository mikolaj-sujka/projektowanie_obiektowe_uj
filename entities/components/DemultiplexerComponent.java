package entities.components;

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
        // Implementacja logiki demultipleksera 3-to-8
        // np. 000 -> Aktywuje wyjście 1, 001 -> Aktywuje wyjście 2 itd.
    }
}
