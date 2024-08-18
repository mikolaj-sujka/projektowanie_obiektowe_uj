package entities.components;

import entities.Component;
import entities.Pin;

public class GrayDecoderComponent extends Component {

    public GrayDecoderComponent(int id) {
        super(id);
        // Gray Code Decoder ma 4 wejścia i 10 wyjść
        for (int i = 1; i <= 4; i++) {
            addPin(i, new Pin(i, false));  // Wejścia Gray Code
        }
        for (int i = 5; i <= 14; i++) {
            addPin(i, new Pin(i, true));  // Wyjścia dekodera
        }
    }

    @Override
    public void performLogic() {
        // Implementacja dekodowania Gray Code na 10 wyjść
        // Można użyć switch-case lub if-else
    }
}
