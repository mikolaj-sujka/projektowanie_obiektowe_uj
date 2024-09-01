package edu.uj.po.simulation.entities.components;


import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class NandGateComponent extends Component {

    public NandGateComponent(int id, int numberOfGates) {
        super(id);
        for (int i = 1; i <= numberOfGates; i++) {
            addPin(i * 2 - 1, new Pin(i * 2 - 1, false)); // Wejście 1
            addPin(i * 2, new Pin(i * 2, false));         // Wejście 2
            addPin(numberOfGates * 2 + i, new Pin(numberOfGates * 2 + i, true)); // Wyjście
        }
    }

    @Override
    public void performLogic() {
        int numberOfGates = pins.size() / 3;
        for (int i = 0; i < numberOfGates; i++) {
            Pin input1 = pins.get(i * 2 + 1); // Poprawne obliczenie indeksu dla wejścia 1
            Pin input2 = pins.get(i * 2 + 2); // Poprawne obliczenie indeksu dla wejścia 2
            Pin output = pins.get(numberOfGates * 2 + 1 + i); // Poprawny indeks dla wyjścia

            // Sprawdzenie, czy któryś z wejściowych pinów ma stan UNKNOWN
            if (input1.getState() == PinState.UNKNOWN || input2.getState() == PinState.UNKNOWN) {
                output.setState(PinState.UNKNOWN);
            } else if (input1.getState() == PinState.HIGH && input2.getState() == PinState.HIGH) {
                output.setState(PinState.LOW);
            } else {
                output.setState(PinState.HIGH);
            }
        }
    }
}
