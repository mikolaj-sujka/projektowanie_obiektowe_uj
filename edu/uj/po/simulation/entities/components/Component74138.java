package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.*;
import java.util.List;

public class Component74138 extends Component {

    public Component74138(int id) {
        super(id, false, createInputPins(), createOutputPins());
    }

    private static List<Integer> createOutputPins() {
        return List.of(7, 9, 10, 11, 12, 13, 14, 15);
    }

    private static List<Integer> createInputPins() {
        return List.of(1, 2, 3, 4, 5, 6);
    }

    @Override
    public void performLogic() {
        Pin G1 = pins.get(6);
        Pin G2A = pins.get(4);
        Pin G2B = pins.get(5);
        Pin A = pins.get(1);
        Pin B = pins.get(2);
        Pin C = pins.get(3);

        // Sprawdzanie aktywacji dekodera - jeśli G1 jest LOW lub G2A, G2B są HIGH, wyjścia są ustawione na HIGH
        if (G1.getState() == PinState.LOW || G2A.getState() == PinState.HIGH || G2B.getState() == PinState.HIGH) {
            setAllOutputsToHigh();
            return;
        }

        // Obliczenie indeksu wyjścia na podstawie stanów A, B i C
        int index = (booleanToInt(C.getState()) << 2) | (booleanToInt(B.getState()) << 1) | booleanToInt(A.getState());

        // Ustawienie wybranego wyjścia na LOW, pozostałe na HIGH
        setAllOutputsToHigh();
        setOutputLow(index);
    }

    // Metoda do ustawiania wszystkich wyjść na HIGH (z wyjątkiem pinu 8)
    private void setAllOutputsToHigh() {
        for (int i = 15; i >= 7; i--) {
            if (i != 8) { // Pin 8 jest pomijany
                pins.get(i).setState(PinState.HIGH);
            }
        }
    }

    // Metoda do ustawiania wybranego wyjścia na LOW
    private void setOutputLow(int index) {
        int outputPin = 15 - index;  // Mapowanie indeksu na numer pinu wyjścia
        if (outputPin != 8) { // Pin 8 jest pomijany
            pins.get(outputPin).setState(PinState.LOW);
        }
    }

    // Konwersja stanu pinu na wartość 0/1
    private int booleanToInt(PinState state) {
        return state == PinState.HIGH ? 1 : 0;
    }
}