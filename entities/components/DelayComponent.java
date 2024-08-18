package entities.components;

import entities.Component;
import entities.Pin;

public class DelayComponent extends Component {

    public DelayComponent(int id, int numberOfDelays) {
        super(id);
        for (int i = 1; i <= numberOfDelays; i++) {
            addPin(i, new Pin(i, false));  // Wejście
            addPin(numberOfDelays + i, new Pin(numberOfDelays + i, true)); // Wyjście
        }
    }

    @Override
    public void performLogic() {
        // Możesz zaimplementować logikę opóźnienia na poziomie ticka zegara
        // Na przykład dodając mechanizm, który zmienia stan wyjścia z opóźnieniem względem wejścia
    }
}
