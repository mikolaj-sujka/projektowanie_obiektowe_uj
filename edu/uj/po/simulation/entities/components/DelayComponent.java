package edu.uj.po.simulation.entities.components;

import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.Pin;

public class DelayComponent extends Component {

    private PinState[] previousStates; // Tablica przechowująca poprzednie stany wejść

    public DelayComponent(int id, int numberOfDelays) {
        super(id);
        previousStates = new PinState[numberOfDelays];
        for (int i = 0; i < numberOfDelays; i++) {
            previousStates[i] = PinState.LOW; // Inicjalizacja stanów początkowych
        }
    }

    @Override
    public void performLogic() {
        // Przesuń stany z wejść do wyjść z opóźnieniem
        for (int i = 1; i <= previousStates.length; i++) {
            Pin input = pins.get(i);
            Pin output = pins.get(previousStates.length + i);

            // Ustawienie wyjścia na podstawie poprzedniego stanu wejścia
            output.setState(previousStates[i - 1]);

            // Zapamiętaj aktualny stan wejścia dla przyszłych opóźnień
            previousStates[i - 1] = input.getState();
        }
    }
}
