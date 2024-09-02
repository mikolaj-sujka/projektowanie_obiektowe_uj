package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

import java.util.HashMap;
import java.util.Map;

public class Component7482 extends Component {

    public Component7482(int id) {
        super(id, createPins());
    }


    private static Map<Integer, Pin> createPins() {
        Map<Integer, Pin> pins = new HashMap<>();

        // Wejścia
        pins.put(1, new Pin(1, false));  // Wejście A
        pins.put(2, new Pin(2, false));  // Wejście B
        pins.put(3, new Pin(3, false));  // Wejście C
        pins.put(4, new Pin(4, false));  // Wejście D

        // Wyjścia
        pins.put(5, new Pin(5, true));   // Wyjście S1 (wynik bitowy)
        pins.put(6, new Pin(6, true));   // Wyjście S2 (wynik sumy)
        pins.put(7, new Pin(7, true));   // Wyjście C (carry out, przeniesienie)

        return pins;
    }

    @Override
    public void performLogic() {
        Pin inputA = pins.get(1);
        Pin inputB = pins.get(2);
        Pin inputC = pins.get(3);
        Pin inputD = pins.get(4);

        Pin outputS1 = pins.get(5);
        Pin outputS2 = pins.get(6);
        Pin outputC = pins.get(7);

        // Obsługa stanu UNKNOWN
        if (inputA.getState() == PinState.UNKNOWN || inputB.getState() == PinState.UNKNOWN ||
                inputC.getState() == PinState.UNKNOWN || inputD.getState() == PinState.UNKNOWN) {
            outputS1.setState(PinState.UNKNOWN);
            outputS2.setState(PinState.UNKNOWN);
            outputC.setState(PinState.UNKNOWN);
            return;
        }

        // Obliczanie wyjść na podstawie stanu wejść
        boolean a = inputA.getState() == PinState.HIGH;
        boolean b = inputB.getState() == PinState.HIGH;
        boolean c = inputC.getState() == PinState.HIGH;
        boolean d = inputD.getState() == PinState.HIGH;

        // Wynik pierwszego sumatora
        boolean s1 = a ^ b ^ c;  // S1 = A XOR B XOR C
        boolean carry1 = (a && b) || (b && c) || (a && c);  // Carry1 = AB + BC + AC

        // Wynik drugiego sumatora (zależny od wyniku pierwszego)
        boolean s2 = s1 ^ d;  // S2 = S1 XOR D
        boolean carry2 = (s1 && d) || carry1;  // Carry2 = S1D + Carry1

        // Ustawienie stanów wyjść
        outputS1.setState(s1 ? PinState.HIGH : PinState.LOW);
        outputS2.setState(s2 ? PinState.HIGH : PinState.LOW);
        outputC.setState(carry2 ? PinState.HIGH : PinState.LOW);
    }
}
