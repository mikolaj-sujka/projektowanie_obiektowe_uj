import edu.uj.po.simulation.interfaces.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class SimulationTests {
    @Test
    public void testSimulationWithValidInitialState() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        // Dodaj poprawne stany pinów początkowych
        initialState.add(new ComponentPinState(1, 1, PinState.HIGH));
        initialState.add(new ComponentPinState(2, 1, PinState.LOW));

        // Przeprowadź symulację
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 5);

        // Sprawdź, czy wynik jest zgodny z oczekiwaniami
        assertNotNull(result);
        assertEquals(6, result.size()); // ticks + initial state
    }

    @Test(expected = UnknownStateException.class)
    public void testSimulationWithMissingInitialState() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        // Dodaj brakujące stany pinów początkowych
        initialState.add(new ComponentPinState(1, 1, PinState.HIGH)); // Brak stanu dla drugiego pinu

        // Przeprowadź symulację, która powinna wyrzucić wyjątek
        simulation.simulation(initialState, 5);
    }

    @Test
    public void testOptimizationIdentifiesRemovableComponents() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        initialState.add(new ComponentPinState(1, 1, PinState.HIGH));
        initialState.add(new ComponentPinState(2, 1, PinState.LOW));

        Set<Integer> removableComponents = simulation.optimize(initialState, 5);

        // Sprawdź, czy poprawnie zidentyfikowano komponenty do usunięcia
        assertNotNull(removableComponents);
        assertTrue(removableComponents.contains(3)); // Zakładając, że komponent 3 jest zbędny
    }

    @Test(expected = UnknownStateException.class)
    public void testSimulationWithUnknownStateGeneration() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        initialState.add(new ComponentPinState(1, 1, PinState.HIGH));
        initialState.add(new ComponentPinState(2, 1, PinState.UNKNOWN)); // Ten stan spowoduje wyjątek

        simulation.simulation(initialState, 5);
    }

    @Test
    public void testOptimizationIdentifiesMultipleRemovableComponents() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        initialState.add(new ComponentPinState(1, 1, PinState.HIGH));
        initialState.add(new ComponentPinState(2, 1, PinState.LOW));

        Set<Integer> removableComponents = simulation.optimize(initialState, 5);

        // Sprawdź, czy poprawnie zidentyfikowano wiele komponentów do usunięcia
        assertNotNull(removableComponents);
        assertTrue(removableComponents.containsAll(Arrays.asList(3, 4))); // Zakładając, że komponenty 3 i 4 są zbędne
    }

    @Test
    public void testSimulationAfterOptimization() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        initialState.add(new ComponentPinState(1, 1, PinState.HIGH));
        initialState.add(new ComponentPinState(2, 1, PinState.LOW));

        // Przeprowadź optymalizację
        simulation.optimize(initialState, 5);

        // Przeprowadź symulację po optymalizacji
        Map<Integer, Set<ComponentPinState>> result = simulation.simulation(initialState, 5);

        assertNotNull(result);
        // Sprawdź, czy wyniki są zgodne z oczekiwaniami po optymalizacji
    }

    @Test
    public void testDuplicateConnections() throws UnknownComponent, UnknownPin, ShortCircuitException {
        Simulation simulation = new Simulation();

        simulation.connect(1, 1, 2, 1); // Pierwsze połączenie
        simulation.connect(1, 1, 2, 1); // Zduplikowane połączenie

        // Jeśli połączenie nie powinno zgłaszać wyjątku, test przejdzie
    }

    @Test(expected = UnknownStateException.class)
    public void testMissingStationaryState() throws UnknownStateException {
        Simulation simulation = new Simulation();
        Set<ComponentPinState> initialState = new HashSet<>();

        // Symulacja bez ustawienia stanu stacjonarnego
        simulation.simulation(initialState, 5);
    }
}
