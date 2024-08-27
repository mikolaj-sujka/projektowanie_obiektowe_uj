package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

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
        // Wyłącz wszystkie wyjścia
        for (int i = 5; i <= 14; i++) {
            pins.get(i).setState(PinState.LOW);
        }

        // Odczytaj wartość wejść (4-bitowy kod BCD)
        int bcdValue = 0;
        if (pins.get(1).getState() == PinState.HIGH) bcdValue |= 1;
        if (pins.get(2).getState() == PinState.HIGH) bcdValue |= 2;
        if (pins.get(3).getState() == PinState.HIGH) bcdValue |= 4;
        if (pins.get(4).getState() == PinState.HIGH) bcdValue |= 8;

        if (bcdValue <= 9) {
            pins.get(5 + bcdValue).setState(PinState.HIGH);
        }
    }
}
