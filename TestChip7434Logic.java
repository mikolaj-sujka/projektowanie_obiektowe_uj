import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7434Logic {

    @Test
    public void chip7434ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException, UnknownStateException, UnknownStateException, UnknownPin, ShortCircuitException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(6);
        int chip = simulation.createChip(7434);
        int outputStrip = simulation.createOutputPinHeader(6);

        // Wejścia dla każdego bufora inwertującego
        simulation.connect(inputStrip, 1, chip, 1);  // 1A
        simulation.connect(inputStrip, 2, chip, 3);  // 2A
        simulation.connect(inputStrip, 3, chip, 5);  // 3A
        simulation.connect(inputStrip, 4, chip, 9);  // 4A
        simulation.connect(inputStrip, 5, chip, 11); // 5A
        simulation.connect(inputStrip, 6, chip, 13); // 6A

        // Wyjścia dla każdego bufora inwertującego
        simulation.connect(outputStrip, 1, chip, 2);  // 1Y
        simulation.connect(outputStrip, 2, chip, 4);  // 2Y
        simulation.connect(outputStrip, 3, chip, 6);  // 3Y
        simulation.connect(outputStrip, 4, chip, 8);  // 4Y
        simulation.connect(outputStrip, 5, chip, 10); // 5Y
        simulation.connect(outputStrip, 6, chip, 12); // 6Y

        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 3, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 5, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 6, PinState.LOW));
        simulation.stationaryState(states);

        Map<Integer, Set<ComponentPinState>> states0List = new HashMap<>();
        states0List.put(0, states);
        tableForChip7434(states0List);

        for (int i = 1; i <= 6; i++) {
            Map<Integer, Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            for (int j = 0; j < list.size(); j++) {
                if (j == i - 1) { // Sprawdzenie odpowiedniego wyjścia
                    Assert.assertEquals(PinState.HIGH, list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.LOW, list.get(j).state());
                }
            }
        }
    }

    private void tableForChip7434(Map<Integer, Set<ComponentPinState>> states0List) {
        // Tworzenie zestawu stanów wejść
        // 1
        Set<ComponentPinState> statesSim1 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2
        Set<ComponentPinState> statesSim2 = fillSet(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3
        Set<ComponentPinState> statesSim3 = fillSet(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
        // 4
        Set<ComponentPinState> statesSim4 = fillSet(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(4, statesSim4);
        // 5
        Set<ComponentPinState> statesSim5 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW);
        states0List.put(5, statesSim5);
        // 6
        Set<ComponentPinState> statesSim6 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW);
        states0List.put(6, statesSim6);
    }

    private Set<ComponentPinState> fillSet(PinState state1A, PinState state2A, PinState state3A, PinState state4A, PinState state5A, PinState state6A) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        statesSim.add(new ComponentPinState(0, 1, state1A));
        statesSim.add(new ComponentPinState(0, 2, state2A));
        statesSim.add(new ComponentPinState(0, 3, state3A));
        statesSim.add(new ComponentPinState(0, 4, state4A));
        statesSim.add(new ComponentPinState(0, 5, state5A));
        statesSim.add(new ComponentPinState(0, 6, state6A));
        return statesSim;
    }
}
