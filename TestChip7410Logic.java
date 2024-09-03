import edu.uj.po.simulation.entities.Pin;
import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7410Logic {

    @Test
    public void chip7410ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException, UnknownPin, ShortCircuitException, UnknownComponent {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(9);
        int chip = simulation.createChip(7410);
        int outputStrip = simulation.createOutputPinHeader(3);

        // Połączenia wejść
        simulation.connect(inputStrip, 1, chip, 1);  // A1
        simulation.connect(inputStrip, 2, chip, 2);  // B1
        simulation.connect(inputStrip, 3, chip, 13); // C1

        simulation.connect(inputStrip, 4, chip, 5);  // A2
        simulation.connect(inputStrip, 5, chip, 3);  // B2
        simulation.connect(inputStrip, 6, chip, 4);  // C2

        simulation.connect(inputStrip, 7, chip, 9);  // A3
        simulation.connect(inputStrip, 8, chip, 10); // B3
        simulation.connect(inputStrip, 9, chip, 11); // C3

        // Połączenia wyjść
        simulation.connect(outputStrip, 1, chip, 12); // Y1
        simulation.connect(outputStrip, 2, chip, 6);  // Y2
        simulation.connect(outputStrip, 3, chip, 8);  // Y3

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
        tableForChip7410(states0List);

        for (int i = 1; i <= 3; i++) {
            Map<Integer, Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            for (int j = 0; j < list.size(); j++) {
                if (j == i - 1) { // Sprawdzenie odpowiedniego wyjścia
                    Assert.assertEquals(PinState.LOW, list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.HIGH, list.get(j).state());
                }
            }
        }
    }

    @Test
    public void chip7410ComponentLogic2() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(9);
        int chip = simulation.createChip(7410);
        int outputStrip = simulation.createOutputPinHeader(3);

        // Połączenia wejść
        simulation.connect(inputStrip, 1, chip, 1);  // A1
        simulation.connect(inputStrip, 2, chip, 2);  // B1
        simulation.connect(inputStrip, 3, chip, 13); // C1

        simulation.connect(inputStrip, 4, chip, 5);  // A2
        simulation.connect(inputStrip, 5, chip, 3);  // B2
        simulation.connect(inputStrip, 6, chip, 4);  // C2

        simulation.connect(inputStrip, 7, chip, 9);  // A3
        simulation.connect(inputStrip, 8, chip, 10); // B3
        simulation.connect(inputStrip, 9, chip, 11); // C3

        // Połączenia wyjść
        simulation.connect(outputStrip, 1, chip, 12); // Y1
        simulation.connect(outputStrip, 2, chip, 6);  // Y2
        simulation.connect(outputStrip, 3, chip, 8);  // Y3

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
        tableForChip7410(states0List);

        for (int i = 1; i <= 3; i++) {
            Map<Integer, Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            // Sprawdzanie, czy zwrócone są tylko piny wyjściowe
            for (ComponentPinState pinState : list) {
                // Zakładamy, że pinY są na pinach 12, 6 i 8
                int pinNumber = pinState.pinId();
                Assert.assertTrue("Pin " + pinNumber + " should be an output pin",
                        pinNumber == 12 || pinNumber == 6 || pinNumber == 8);
            }

            for (int j = 0; j < list.size(); j++) {
                if (j == i - 1) { // Sprawdzenie odpowiedniego wyjścia
                    Assert.assertEquals(PinState.LOW, list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.HIGH, list.get(j).state());
                }
            }
        }
    }

    private void tableForChip7410(Map<Integer, Set<ComponentPinState>> states0List) {
        // Tworzenie zestawu stanów wejść
        // 1
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3
        Set<ComponentPinState> statesSim3 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH);
        states0List.put(3, statesSim3);
    }

    private Set<ComponentPinState> fillSet(PinState stateA1, PinState stateB1, PinState stateC1, PinState stateA2, PinState stateB2, PinState stateC2, PinState stateA3, PinState stateB3, PinState stateC3) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        // inputPinHeader should be 0 and have 9 pins
        statesSim.add(new ComponentPinState(0, 1, stateA1));
        statesSim.add(new ComponentPinState(0, 2, stateB1));
        statesSim.add(new ComponentPinState(0, 3, stateC1));
        statesSim.add(new ComponentPinState(0, 4, stateA2));
        statesSim.add(new ComponentPinState(0, 5, stateB2));
        statesSim.add(new ComponentPinState(0, 6, stateC2));
        statesSim.add(new ComponentPinState(0, 7, stateA3));
        statesSim.add(new ComponentPinState(0, 8, stateB3));
        statesSim.add(new ComponentPinState(0, 9, stateC3));
        return statesSim;
    }
}
