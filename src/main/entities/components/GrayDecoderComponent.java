package src.main.entities.components;

import src.main.edu.uj.po.simulation.interfaces.PinState;
import src.main.entities.Component;
import src.main.entities.Pin;

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
        // Wyłącz wszystkie wyjścia
        for (int i = 5; i <= 14; i++) {
            pins.get(i).setState(PinState.LOW);
        }

        // Odczytaj wartość wejść (4-bitowy kod Gray'a)
        int grayValue = 0;
        if (pins.get(1).getState() == PinState.HIGH) grayValue |= 1;
        if (pins.get(2).getState() == PinState.HIGH) grayValue |= 2;
        if (pins.get(3).getState() == PinState.HIGH) grayValue |= 4;
        if (pins.get(4).getState() == PinState.HIGH) grayValue |= 8;

        // Przekształć kod Gray'a na kod binarny
        int binaryValue = grayToBinary(grayValue);

        // Ustaw odpowiednie wyjście na HIGH
        if (binaryValue >= 0 && binaryValue <= 9) {
            pins.get(5 + binaryValue).setState(PinState.HIGH);
        }
    }

    private int grayToBinary(int gray) {
        int binary = gray;
        while ((gray >>= 1) != 0) {
            binary ^= gray;
        }
        return binary;
    }
}
