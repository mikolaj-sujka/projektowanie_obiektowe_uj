import edu.uj.po.simulation.interfaces.*;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class ConnectionTests {

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
    @Test(expected = UnknownPin.class)
    public void test7404ConnectionInvalidPin() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Próba połączenia nieistniejących pinów
        simulation.connect(chip7404, 20, chip7404, 21); // Nieistniejące piny
    }

    @Test(expected = ShortCircuitException.class)
    public void test7404ConnectionInvalid1() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Próba połączenia wyjścia z wyjściem
        simulation.connect(chip7404, 2, chip7404, 4); // 1Y -> 2Y
    }

    @Test(expected = ShortCircuitException.class)
    public void test7404ConnectionInvalid2() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Próba połączenia wejścia z wejściem
        simulation.connect(chip7404, 1, chip7404, 3); // 1A -> 2A
    }

    @Test
    public void test7404ConnectionValid1() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Podłącz poprawne wejście i wyjście
        simulation.connect(chip7404, 1, chip7404, 2); // 1A -> 1Y

        // Brak wyjątku oznacza, że połączenie jest poprawne
    }

    @Test
    public void test7404ConnectionValid2() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Testowanie połączenia między innymi pinami
        simulation.connect(chip7404, 3, chip7404, 4); // 2A -> 2Y
    }

    @Test
    public void test7404ConnectionValid3() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Testowanie połączenia między innymi pinami
        simulation.connect(chip7404, 5, chip7404, 6); // 3A -> 3Y
    }

    @Test
    public void test7404ConnectionValid4() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Testowanie połączenia między innymi pinami
        simulation.connect(chip7404, 9, chip7404, 8); // 4A -> 4Y
    }

    @Test
    public void test7404ConnectionValid5() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Testowanie połączenia między innymi pinami
        simulation.connect(chip7404, 11, chip7404, 10); // 5A -> 5Y
    }

    @Test
    public void test7404ConnectionValid6() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Testowanie połączenia między innymi pinami
        simulation.connect(chip7404, 13, chip7404, 12); // 6A -> 6Y
    }

    @Test
    public void test7404ConnectionVccAndGnd() throws Exception {
        Simulation simulation = new Simulation();
        int chip7404 = simulation.createChip(7404);

        // Próba połączenia pinu Vcc lub GND powinna być ignorowana
        // Dlatego nie oczekujemy żadnych wyjątków ani akcji
        simulation.connect(chip7404, 14, chip7404, 7); // Vcc -> GND
    }

    @Test(expected = ShortCircuitException.class)
    public void test7404ConnectionInvalidOutputToOutput() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        simulation.connect(chipId, 2, chipId, 4); // Próbujemy połączyć dwa wyjścia
    }

    @Test(expected = ShortCircuitException.class)
    public void test7404ConnectionValidInputToInput() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia dwóch wejść
        simulation.connect(chipId, 1, chipId, 3); // Próbujemy połączyć dwa wejścia
    }

    @Test
    public void test7404ConnectionValidInputToOutput() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia wejścia z wyjściem
        simulation.connect(chipId, 1, chipId, 8); // Wejście 1 do wyjścia 4Y
    }

    @Test
    public void test7404ConnectionValidOutputToInput() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia wyjścia z wejściem
        simulation.connect(chipId, 8, chipId, 1); // Wyjście 4Y do wejścia 1A
    }

    @Test
    public void test7404ConnectionValidInputToInputDifferentComponents() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId1 = simulation.createChip(7404);
        int chipId2 = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia dwóch wejść z różnych komponentów
        simulation.connect(chipId1, 1, chipId2, 3);
    }

    @Test
    public void test7404ConnectionValidInputToOutputSingle() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia wejścia z wyjściem
        simulation.connect(chipId, 1, chipId, 8);
    }

    @Test
    public void test7404ConnectionInvalidOutputToOutputSameComponent() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId = simulation.createChip(7404);

        assertThrows(ShortCircuitException.class, () -> {
            simulation.connect(chipId, 8, chipId, 6); // Próbujemy połączyć dwa wyjścia w tym samym komponencie
        });
    }

    @Test
    public void test7404ConnectionInvalidOutputToOutputDifferentComponents() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId1 = simulation.createChip(7404);
        int chipId2 = simulation.createChip(7404);

        assertThrows(ShortCircuitException.class, () -> {
            simulation.connect(chipId1, 8, chipId2, 6); // Próbujemy połączyć dwa wyjścia z różnych komponentów
        });
    }

    @Test
    public void test7404ConnectionValidOutputToInputDifferentComponents() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId1 = simulation.createChip(7404);
        int chipId2 = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia wyjścia z wejściem z różnych komponentów
        simulation.connect(chipId1, 8, chipId2, 1);
    }

    @Test
    public void test7404ConnectionValidInputToInputDifferentComponents2() throws UnknownStateException, UnknownComponent, UnknownPin, ShortCircuitException, UnknownChip {
        Simulation simulation = new Simulation();
        int chipId1 = simulation.createChip(7404);
        int chipId2 = simulation.createChip(7404);

        // Sprawdzenie poprawności połączenia wejścia z wejściem z różnych komponentów
        simulation.connect(chipId1, 2, chipId2, 3);
    }
}

