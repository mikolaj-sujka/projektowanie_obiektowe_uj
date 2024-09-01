import edu.uj.po.simulation.interfaces.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimulationTests {
    @Test
    public void testCircuitBasedOnImage() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        // Tworzenie komponentów na podstawie obrazka
        int andGateChip = simulation.createChip(7408); // U01 - AND gate (4 bramki AND)
        int notGateChip = simulation.createChip(7404); // U02 - NOT gate (6 bramek NOT)

        // Połączenia na podstawie obrazka
        simulation.connect(andGateChip, 1, andGateChip, 2);  // We1 -> AND gate 1 (U01 pin 1)
        simulation.connect(andGateChip, 4, andGateChip, 5);  // We2 -> AND gate 1 (U01 pin 2)
        simulation.connect(andGateChip, 6, notGateChip, 1);  // Wyjście z AND gate (U01 pin 3) -> We3 NOT gate (U02 pin 1)

        simulation.connect(notGateChip, 2, andGateChip, 10); // NOT gate (U02 pin 2) -> AND gate 2 (U01 pin 5)
        simulation.connect(notGateChip, 3, notGateChip, 4);  // NOT gate 2 (U02 pin 4) -> NOT gate 3 (U02 pin 5)
        simulation.connect(notGateChip, 5, notGateChip, 6);  // NOT gate 3 (U02 pin 6) -> Wyjście (U02 pin 12)

        // Ustawienie początkowych stanów wejściowych
        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // We1 = HIGH
        initialState.add(new ComponentPinState(andGateChip, 4, PinState.LOW));  // We2 = LOW
        initialState.add(new ComponentPinState(andGateChip, 6, PinState.HIGH)); // We4 = HIGH

        // Przeprowadzenie symulacji stanu stacjonarnego
        simulation.stationaryState(initialState);

        // Sprawdzenie wyników symulacji
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);
        assertNotNull(result);

        // Sprawdzenie stanów wyjść
        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);

        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 3) {
                    assertEquals(PinState.HIGH, state.state()); // Sprawdź stan pinu 3 AND gate (U01)
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 6) {
                    assertEquals(PinState.LOW, state.state()); // Sprawdź stan pinu 6 NOT gate (U02)
                }
            }
        }
    }

    @Test
    public void testAndNandNotGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGateChip = simulation.createChip(7408); // 4x AND gate
        int nandGateChip = simulation.createChip(7400); // 4x NAND gate
        int notGateChip = simulation.createChip(7404);  // 6x NOT gate

        simulation.connect(andGateChip, 1, nandGateChip, 1); // AND output -> NAND input
        simulation.connect(nandGateChip, 2, notGateChip, 1); // NAND output -> NOT input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // Set AND gate input to HIGH
        initialState.add(new ComponentPinState(andGateChip, 2, PinState.HIGH)); // Set AND gate input to HIGH

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 3) {
                    assertEquals(PinState.HIGH, state.state()); // AND gate output should be HIGH
                }
            }
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NAND gate output should be LOW
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.HIGH, state.state()); // NOT gate output should be HIGH
                }
            }
        }
    }

    @Test
    public void testNandNotOrGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int nandGateChip = simulation.createChip(7400); // 4x NAND gate
        int notGateChip = simulation.createChip(7404);  // 6x NOT gate
        int orGateChip = simulation.createChip(7432);   // 4x OR gate

        simulation.connect(nandGateChip, 1, notGateChip, 1); // NAND output -> NOT input
        simulation.connect(notGateChip, 2, orGateChip, 1);   // NOT output -> OR input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(nandGateChip, 1, PinState.LOW));  // Set NAND gate input to LOW
        initialState.add(new ComponentPinState(nandGateChip, 2, PinState.LOW));  // Set NAND gate input to LOW

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.HIGH, state.state()); // NAND gate output should be HIGH
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NOT gate output should be LOW
                }
            }
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // OR gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testNandAndNorGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int nandGateChip = simulation.createChip(7400); // 4x NAND gate
        int norGateChip = simulation.createChip(7402);  // 4x NOR gate

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(nandGateChip, 1, PinState.HIGH)); // Set NAND gate input to HIGH
        initialState.add(new ComponentPinState(nandGateChip, 2, PinState.LOW));  // Set NAND gate input to LOW

        simulation.connect(nandGateChip, 1, norGateChip, 1); // NAND output -> NOR input

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 1) {
                    assertEquals(PinState.HIGH, state.state()); // NAND gate output should be HIGH
                }
            }
            if (state.componentId() == norGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NOR gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testAndOrNandGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGateChip = simulation.createChip(7408); // 4x AND gate
        int orGateChip = simulation.createChip(7432); // 4x OR gate
        int nandGateChip = simulation.createChip(7400); // 4x NAND gate

        simulation.connect(andGateChip, 1, orGateChip, 1);   // AND output -> OR input
        simulation.connect(orGateChip, 3, nandGateChip, 1);  // OR output -> NAND input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // Set AND gate input to HIGH
        initialState.add(new ComponentPinState(andGateChip, 2, PinState.HIGH)); // Set AND gate input to HIGH

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 3) {
                    assertEquals(PinState.HIGH, state.state()); // AND gate output should be HIGH
                }
            }
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 4) {
                    assertEquals(PinState.HIGH, state.state()); // OR gate output should be HIGH
                }
            }
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NAND gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testAndNotOrGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGateChip = simulation.createChip(7408); // 4x AND gate
        int notGateChip = simulation.createChip(7404); // 6x NOT gate
        int orGateChip = simulation.createChip(7432); // 4x OR gate

        simulation.connect(andGateChip, 1, notGateChip, 1); // AND output -> NOT input
        simulation.connect(notGateChip, 2, orGateChip, 1);  // NOT output -> OR input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // Set AND gate input to HIGH
        initialState.add(new ComponentPinState(andGateChip, 2, PinState.HIGH)); // Set AND gate input to HIGH

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 3) {
                    assertEquals(PinState.HIGH, state.state()); // AND gate output should be HIGH
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NOT gate output should be LOW
                }
            }
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // OR gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testOrAndNotGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int orGateChip = simulation.createChip(7432); // 4x OR gate
        int notGateChip = simulation.createChip(7404); // 6x NOT gate

        simulation.connect(orGateChip, 1, notGateChip, 1); // OR output -> NOT input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(orGateChip, 1, PinState.HIGH)); // Set OR gate input to HIGH
        initialState.add(new ComponentPinState(orGateChip, 2, PinState.LOW));  // Set OR gate input to LOW

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 1) {
                    assertEquals(PinState.HIGH, state.state()); // OR gate output should be HIGH
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NOT gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testNotAndNandGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int notGateChip = simulation.createChip(7404); // 6x NOT gate
        int nandGateChip = simulation.createChip(7400); // 4x NAND gate

        simulation.connect(notGateChip, 1, nandGateChip, 1); // NOT output -> NAND input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(notGateChip, 1, PinState.HIGH)); // Set NOT gate input to HIGH

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NOT gate output should be LOW
                }
            }
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.HIGH, state.state()); // NAND gate output should be HIGH
                }
            }
        }
    }

    @Test
    public void testAndAndOrGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGateChip = simulation.createChip(7408); // 4x AND gate
        int orGateChip = simulation.createChip(7432); // 4x OR gate

        simulation.connect(andGateChip, 1, orGateChip, 1); // AND output -> OR input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // Set AND gate input to HIGH
        initialState.add(new ComponentPinState(andGateChip, 2, PinState.HIGH)); // Set AND gate input to HIGH

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 3) {
                    assertEquals(PinState.HIGH, state.state()); // AND gate output should be HIGH
                }
            }
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.HIGH, state.state()); // OR gate output should be HIGH
                }
            }
        }
    }

    @Test
    public void testOrAndNandGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int orGateChip = simulation.createChip(7432); // 4x OR gate
        int nandGateChip = simulation.createChip(7400); // 4x NAND gate

        simulation.connect(orGateChip, 1, nandGateChip, 1); // OR output -> NAND input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(orGateChip, 1, PinState.HIGH)); // Set OR gate input to HIGH
        initialState.add(new ComponentPinState(orGateChip, 2, PinState.LOW));  // Set OR gate input to LOW

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == orGateChip) {
                if (state.pinId() == 1) {
                    assertEquals(PinState.HIGH, state.state()); // OR gate output should be HIGH
                }
            }
            if (state.componentId() == nandGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // NAND gate output should be LOW
                }
            }
        }
    }

    @Test
    public void testAndAndNotGates() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGateChip = simulation.createChip(7408); // 4x AND gate
        int notGateChip = simulation.createChip(7404); // 6x NOT gate

        simulation.connect(andGateChip, 1, notGateChip, 1); // AND output -> NOT input
        simulation.connect(notGateChip, 2, andGateChip, 2); // NOT output -> AND input

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(andGateChip, 1, PinState.HIGH)); // Set AND gate input to HIGH
        initialState.add(new ComponentPinState(andGateChip, 2, PinState.LOW));  // Set AND gate input to LOW

        simulation.stationaryState(initialState);
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 1);

        Set<ComponentPinState> tick1States = result.get(1);
        assertNotNull(tick1States);
        for (ComponentPinState state : tick1States) {
            if (state.componentId() == andGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.LOW, state.state()); // AND gate output should be LOW
                }
            }
            if (state.componentId() == notGateChip) {
                if (state.pinId() == 2) {
                    assertEquals(PinState.HIGH, state.state()); // NOT gate output should be HIGH
                }
            }
        }
    }

}
