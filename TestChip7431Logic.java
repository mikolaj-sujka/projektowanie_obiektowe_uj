import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7431Logic {

    @Test
    public void chip7431ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(9);
        int chip = simulation.createChip(7431);
        int outputStrip = simulation.createOutputPinHeader(6);

        // Wejścia dla każdej bramki
        simulation.connect(inputStrip, 1, chip, 1);  // 1A (NOT)
        simulation.connect(inputStrip, 2, chip, 3);  // 2A (BUFOR)
        simulation.connect(inputStrip, 3, chip, 5);  // 3A (NAND)
        simulation.connect(inputStrip, 4, chip, 6);  // 3B (NAND)
        simulation.connect(inputStrip, 5, chip, 11); // 3C (NAND)
        simulation.connect(inputStrip, 6, chip, 13); // 4A (BUFOR)
        simulation.connect(inputStrip, 7, chip, 15); // 5A (NOT)
        simulation.connect(inputStrip, 8, chip, 9);  // 6A (NAND)
        simulation.connect(inputStrip, 9, chip, 10); // 6B (NAND)

        // Wyjścia
        simulation.connect(outputStrip, 1, chip, 2);  // 1Y (NOT)
        simulation.connect(outputStrip, 2, chip, 4);  // 2Y (BUFOR)
        simulation.connect(outputStrip, 3, chip, 7);  // 3Y (NAND)
        simulation.connect(outputStrip, 4, chip, 12); // 4Y (BUFOR)
        simulation.connect(outputStrip, 5, chip, 14); // 5Y (NOT)
        simulation.connect(outputStrip, 6, chip, 8);  // 6Y (NAND)

        // Inicjalizacja początkowych stanów
        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 3, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 5, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 6, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 7, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 8, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 9, PinState.LOW));
        simulation.stationaryState(states);

        Map<Integer, Set<ComponentPinState>> states0List = new HashMap<>();
        states0List.put(0, states);
        tableForChip7431(states0List);

        for (int i = 1; i <= 6; i++) {
            Map<Integer, Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            for (int j = 0; j < list.size(); j++) {
                // Sprawdzenie odpowiedniego wyjścia
                if (j == i - 1) {
                    Assert.assertEquals(PinState.LOW, list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.HIGH, list.get(j).state());
                }
            }
        }
    }

    private void tableForChip7431(Map<Integer, Set<ComponentPinState>> states0List) {
        // Tworzenie zestawu stanów wejść
        // 1
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3
        Set<ComponentPinState> statesSim3 = fillSet(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
        // 4
        Set<ComponentPinState> statesSim4 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(4, statesSim4);
        // 5
        Set<ComponentPinState> statesSim5 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW);
        states0List.put(5, statesSim5);
        // 6
        Set<ComponentPinState> statesSim6 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH);
        states0List.put(6, statesSim6);
    }

    private Set<ComponentPinState> fillSet(PinState state1A, PinState state2A, PinState state3A, PinState state3B, PinState state3C, PinState state4A, PinState state5A, PinState state6A, PinState state6B) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        statesSim.add(new ComponentPinState(0, 1, state1A));
        statesSim.add(new ComponentPinState(0, 2, state2A));
        statesSim.add(new ComponentPinState(0, 3, state3A));
        statesSim.add(new ComponentPinState(0, 4, state3B));
        statesSim.add(new ComponentPinState(0, 5, state3C));
        statesSim.add(new ComponentPinState(0, 6, state4A));
        statesSim.add(new ComponentPinState(0, 7, state5A));
        statesSim.add(new ComponentPinState(0, 8, state6A));
        statesSim.add(new ComponentPinState(0, 9, state6B));
        return statesSim;
    }
}
