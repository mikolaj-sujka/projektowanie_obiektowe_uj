import edu.uj.po.simulation.interfaces.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ConnectionTests {

    @Test(expected = ShortCircuitException.class)
    public void testConnectTwoOutputPins() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGate1 = simulation.createChip(7408); // AND gate
        int notGate1 = simulation.createChip(7404); // NOT gate

        // Próba połączenia dwóch pinów wyjściowych
        simulation.connect(andGate1, 3, notGate1, 7); // Should throw ShortCircuitException
    }

    @Test()
    public void testConnectTwoInputPins() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGate1 = simulation.createChip(7408); // AND gate
        int notGate1 = simulation.createChip(7404); // NOT gate

        // Próba połączenia dwóch pinów wejściowych
        simulation.connect(andGate1, 1, notGate1, 1); // Should throw ShortCircuitException
    }

    @Test
    public void testConnectInputToOutput() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        int andGate1 = simulation.createChip(7408); // AND gate
        int notGate1 = simulation.createChip(7404); // NOT gate

        // Poprawne połączenie pinu wyjściowego z wejściowym
        simulation.connect(andGate1, 3, notGate1, 1); // Should not throw exception
    }

    @Test
    public void testConnectOutputToMultipleInputs() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        // Create components
        int andGate1 = simulation.createChip(7408); // AND gate
        int andGate2 = simulation.createChip(7408); // AND gate
        int andGate3 = simulation.createChip(7408); // AND gate

        // Connect output of andGate1 to input1 of both andGate2 and andGate3
        simulation.connect(andGate1, 3, andGate2, 1); // andGate1 output -> andGate2 input1
        simulation.connect(andGate1, 3, andGate3, 1); // andGate1 output -> andGate3 input1

        // No exceptions should be thrown, indicating the connections are valid
    }

    @Test(expected = ShortCircuitException.class)
    public void testConnectMultipleOutputsToSingleInput() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        // Create components
        int andGate1 = simulation.createChip(7408); // AND gate
        int andGate2 = simulation.createChip(7408); // AND gate
        int orGate = simulation.createChip(7432); // OR gate

        // Attempt to connect two different outputs to the same input (should cause ShortCircuitException)
        simulation.connect(andGate1, 3, orGate, 1); // andGate1 output -> orGate input1
        simulation.connect(andGate2, 3, orGate, 1); // andGate2 output -> orGate input1
    }

    @Test
    public void testAndToOrGateConnection() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        // Create components
        int andGate = simulation.createChip(7408); // AND gate
        int orGate = simulation.createChip(7432);  // OR gate

        // Connect output of andGate to input1 of orGate
        simulation.connect(andGate, 3, orGate, 1); // andGate output -> orGate input1

        // No exceptions should be thrown, indicating the connection is valid
    }

    @Test
    public void testNotToAndGateConnection() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();

        // Create components
        int notGate = simulation.createChip(7404); // NOT gate
        int andGate = simulation.createChip(7408); // AND gate

        // Connect output of notGate to input1 of andGate
        simulation.connect(notGate, 2, andGate, 1); // notGate output -> andGate input1

        // No exceptions should be thrown, indicating the connection is valid
    }

    @Test
    public void testValidConnectionScenario() throws UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip, UnknownStateException {
        Simulation simulation = new Simulation();

        // Tworzenie komponentów
        int u1 = simulation.createChip(7408); // AND gate
        int u2 = simulation.createChip(7408); // AND gate
        int u3 = simulation.createChip(7404); // NOT gate
        int u4 = simulation.createChip(7432); // OR gate

        // Połączenia zielone (dozwolone)
        simulation.connect(u1, 1, u1, 3); // We1 -> Wy1 (U1)
        simulation.connect(u1, 3, u2, 2); // Wy1 (U1) -> We2 (U2)
        simulation.connect(u2, 3, u1, 5); // Wy2 (U2) -> We5 (U1)
        simulation.connect(u1, 3, u3, 1); // Wy1 (U1) -> We1 (U3)
        simulation.connect(u1, 3, u4, 2); // Wy1 (U1) -> We2 (U4)
        simulation.connect(u1, 3, u4, 3); // Wy1 (U1) -> We3 (U4)

        Set<ComponentPinState> initialState = new HashSet<>();
        initialState.add(new ComponentPinState(u1, 1, PinState.HIGH)); // Set We1 of U1 to HIGH
        initialState.add(new ComponentPinState(u1, 2, PinState.HIGH)); // Set We2 of U1 to HIGH
        initialState.add(new ComponentPinState(u2, 2, PinState.LOW));  // Set We2 of U2 to LOW
        initialState.add(new ComponentPinState(u1, 5, PinState.HIGH));
        initialState.add(new ComponentPinState(u3, 1, PinState.LOW)); // lub PinState.HIGH w zależności od potrzeby
        initialState.add(new ComponentPinState(u4, 2, PinState.LOW));

        // sprawdzenie stationaryState
        simulation.stationaryState(initialState);
    }

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
    }
}
