package entities.components;

import entities.Component;
import entities.Pin;

public class BcdDecoderComponent extends Component {

    public BcdDecoderComponent(int id) {
        super(id);
        // BCD Decoder ma 4 wejścia (4 bity) i 10 wyjść
        for (int i = 1; i <= 4; i++) {
            addPin(i, new Pin(i, false));  // Wejścia BCD
        }
        for (int i = 5; i <= 14; i++) {
            addPin(i, new Pin(i, true));  // Wyjścia dekodera
        }
    }

    @Override
    public void performLogic() {
        // Implementacja dekodowania BCD na 10 wyjść
        // np. 0001 (BCD) -> Wyjście 1 aktywne
        // Można użyć switch-case lub if-else
    }
}
