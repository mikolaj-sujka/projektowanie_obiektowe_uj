import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip74138Logic {

    @Test
    public void chip74138ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(6);
        int chip = simulation.createChip(74138);
        int outputStrip = simulation.createOutputPinHeader(8);

        // Wejścia
        simulation.connect(inputStrip, 1, chip, 1);  // A
        simulation.connect(inputStrip, 2, chip, 2);  // B
        simulation.connect(inputStrip, 3, chip, 3);  // C
        simulation.connect(inputStrip, 4, chip, 4);  // G2A
        simulation.connect(inputStrip, 5, chip, 5);  // G2B
        simulation.connect(inputStrip, 6, chip, 6);  // G1

        // Wyjścia Y0 - Y7 (na pinach 15 do 7 z pominięciem pinu 8)
        for (int i = 0; i < 8; i++) {
            int outputPin = 15 - i;
            if (outputPin == 8) continue;  // Pomijamy pin 8
            simulation.connect(outputStrip, i + 1, chip, outputPin);  // Y0 (15), Y1 (14), ..., Y7 (9)
        }

        // Testowanie różnych kombinacji wejść
        for (int i = 0; i < 8; i++) {
            Set<ComponentPinState> states = new HashSet<>();
            states.add(new ComponentPinState(inputStrip, 1, (i & 1) != 0 ? PinState.HIGH : PinState.LOW)); // A
            states.add(new ComponentPinState(inputStrip, 2, (i & 2) != 0 ? PinState.HIGH : PinState.LOW)); // B
            states.add(new ComponentPinState(inputStrip, 3, (i & 4) != 0 ? PinState.HIGH : PinState.LOW)); // C
            states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));  // G2A (must be LOW)
            states.add(new ComponentPinState(inputStrip, 5, PinState.LOW));  // G2B (must be LOW)
            states.add(new ComponentPinState(inputStrip, 6, PinState.HIGH)); // G1 (must be HIGH)
            simulation.stationaryState(states);

            Map<Integer, Set<ComponentPinState>> results = simulation.simulation(states, 1);
            List<ComponentPinState> list = new ArrayList<>(results.get(1));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            int outputIndex = 0;
            for (ComponentPinState state : list) {
                if (outputIndex == i) { // Oczekiwane wyjście LOW dla odpowiedniego Yx
                    Assert.assertEquals(PinState.LOW, state.state());
                } else {
                    Assert.assertEquals(PinState.HIGH, state.state());
                }
                outputIndex++;
            }
        }

        // Dodatkowy test: G1 = LOW, G2A = HIGH lub G2B = HIGH - wszystkie wyjścia HIGH
        Set<ComponentPinState> statesGDisabled = new HashSet<>();
        statesGDisabled.add(new ComponentPinState(inputStrip, 1, PinState.LOW)); // A
        statesGDisabled.add(new ComponentPinState(inputStrip, 2, PinState.LOW)); // B
        statesGDisabled.add(new ComponentPinState(inputStrip, 3, PinState.LOW)); // C
        statesGDisabled.add(new ComponentPinState(inputStrip, 4, PinState.LOW)); // G2A
        statesGDisabled.add(new ComponentPinState(inputStrip, 5, PinState.LOW)); // G2B
        statesGDisabled.add(new ComponentPinState(inputStrip, 6, PinState.LOW)); // G1 (LOW, all outputs HIGH)
        simulation.stationaryState(statesGDisabled);

        Map<Integer, Set<ComponentPinState>> resultsGDisabled = simulation.simulation(statesGDisabled, 1);
        List<ComponentPinState> listGDisabled = new ArrayList<>(resultsGDisabled.get(1));
        listGDisabled.sort(Comparator.comparing(ComponentPinState::pinId));

        for (ComponentPinState state : listGDisabled) {
            Assert.assertEquals(PinState.HIGH, state.state());
        }
    }
}
