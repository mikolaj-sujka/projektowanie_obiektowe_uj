import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7404Logic {

    @Test
    public void chip7404ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(6);
        int chip = simulation.createChip(7404);
        int outputStrip = simulation.createOutputPinHeader(6);

        // Połączenia wejść i wyjść
        simulation.connect(inputStrip, 1, chip, 1);  // A1
        simulation.connect(inputStrip, 2, chip, 3);  // A2
        simulation.connect(inputStrip, 3, chip, 5);  // A3
        simulation.connect(inputStrip, 4, chip, 9);  // A4
        simulation.connect(inputStrip, 5, chip, 11); // A5
        simulation.connect(inputStrip, 6, chip, 13); // A6

        simulation.connect(outputStrip, 1, chip, 2);  // Y1
        simulation.connect(outputStrip, 2, chip, 4);  // Y2
        simulation.connect(outputStrip, 3, chip, 6);  // Y3
        simulation.connect(outputStrip, 4, chip, 8);  // Y4
        simulation.connect(outputStrip, 5, chip, 10); // Y5
        simulation.connect(outputStrip, 6, chip, 12); // Y6

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
        tableForChip7404(states0List);

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
    public void chip7404To7404ConnectionTest() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        // Tworzymy dwa układy 7404
        int chip1 = simulation.createChip(7404);
        int chip2 = simulation.createChip(7404);

        // Tworzymy PinHeaders dla wejść i wyjść
        int inputStrip1 = simulation.createInputPinHeader(1);  // Wejście do chip1
        int outputStrip1 = simulation.createOutputPinHeader(1); // Wyjście z chip1
        int inputStrip2 = simulation.createInputPinHeader(1);  // Wejście do chip2
        int outputStrip2 = simulation.createOutputPinHeader(1); // Wyjście z chip2

        // Łączymy wejście A1 chip1 z wyjściem Y1 chip2
        simulation.connect(inputStrip1, 1, chip1, 1);  // A1 (chip1) -> inputStrip1
        simulation.connect(outputStrip1, 1, chip1, 2); // Y1 (chip1) -> outputStrip1

        simulation.connect(outputStrip1, 1, chip2, 1);  // Y1 (chip1) -> A1 (chip2)
        simulation.connect(outputStrip2, 1, chip2, 2);  // Y1 (chip2) -> outputStrip2

        // Ustawienie stanu na wejściu A1 chip1 na HIGH
        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip1, 1, PinState.HIGH));
        simulation.stationaryState(states);

        // Uruchomienie symulacji dla 1 ticka
        Map<Integer, Set<ComponentPinState>> results = simulation.simulation(states, 1);

        // Sprawdzamy, czy Y1 na chip1 jest LOW (odwrócenie HIGH na wejściu)
        Assert.assertEquals(PinState.LOW, getStateForPin(results, outputStrip1, 1));

        // Sprawdzamy, czy stan Y1 z chip1 został poprawnie przekazany na A1 w chip2, czyli Y1 na chip2
        Assert.assertEquals(PinState.HIGH, getStateForPin(results, outputStrip2, 1));  // A1 chip2 -> Y1 chip2
    }

    // Funkcja pomocnicza do pobierania stanu pinu z wyników symulacji
    private PinState getStateForPin(Map<Integer, Set<ComponentPinState>> results, int outputStrip, int pinNumber) {
        for (Set<ComponentPinState> pinStates : results.values()) {
            for (ComponentPinState pinState : pinStates) {
                if (pinState.componentId() == outputStrip && pinState.pinId() == pinNumber) {
                    return pinState.state();
                }
            }
        }
        return PinState.UNKNOWN; // W przypadku braku wyniku
    }

    @Test
    public void chip7404ComponentConnectionLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(6);
        int chip1 = simulation.createChip(7404);  // Pierwszy układ 7404
        int chip2 = simulation.createChip(7404);  // Drugi układ 7404
        int outputStrip = simulation.createOutputPinHeader(6);

        // Połączenia wejść
        simulation.connect(inputStrip, 1, chip1, 1);  // A1 do chip1
        simulation.connect(inputStrip, 2, chip1, 3);  // A2 do chip1
        simulation.connect(inputStrip, 3, chip1, 5);  // A3 do chip1
        simulation.connect(inputStrip, 4, chip1, 9);  // A4 do chip1
        simulation.connect(inputStrip, 5, chip1, 11); // A5 do chip1
        simulation.connect(inputStrip, 6, chip1, 13); // A6 do chip1

        // Połączenia wyjść chip1 do wejść chip2 (każdy Y do odpowiadającego A)
        simulation.connect(chip1, 2, chip2, 1);  // Y1 chip1 do A1 chip2
        simulation.connect(chip1, 4, chip2, 3);  // Y2 chip1 do A2 chip2
        simulation.connect(chip1, 6, chip2, 5);  // Y3 chip1 do A3 chip2
        simulation.connect(chip1, 8, chip2, 9);  // Y4 chip1 do A4 chip2
        simulation.connect(chip1, 10, chip2, 11); // Y5 chip1 do A5 chip2
        simulation.connect(chip1, 12, chip2, 13); // Y6 chip1 do A6 chip2

        // Połączenia wyjść chip2 do OutputPinHeader
        simulation.connect(outputStrip, 1, chip2, 2);  // Y1 chip2 do output
        simulation.connect(outputStrip, 2, chip2, 4);  // Y2 chip2 do output
        simulation.connect(outputStrip, 3, chip2, 6);  // Y3 chip2 do output
        simulation.connect(outputStrip, 4, chip2, 8);  // Y4 chip2 do output
        simulation.connect(outputStrip, 5, chip2, 10); // Y5 chip2 do output
        simulation.connect(outputStrip, 6, chip2, 12); // Y6 chip2 do output

        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.HIGH));  // A1 HIGH
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));   // A2 LOW
        states.add(new ComponentPinState(inputStrip, 3, PinState.HIGH));  // A3 HIGH
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));   // A4 LOW
        states.add(new ComponentPinState(inputStrip, 5, PinState.HIGH));  // A5 HIGH
        states.add(new ComponentPinState(inputStrip, 6, PinState.LOW));   // A6 LOW
        simulation.stationaryState(states);

        Map<Integer, Set<ComponentPinState>> results = simulation.simulation(states, 5);

        // Oczekiwane stany wyjść
        Assert.assertEquals(PinState.LOW, getStateForPin(results, outputStrip, 1));  // Oczekiwane LOW na wyjściu 1 (A1 -> Y1 -> A1 -> Y1)
        Assert.assertEquals(PinState.HIGH, getStateForPin(results, outputStrip, 2)); // Oczekiwane HIGH na wyjściu 2 (A2 -> Y2 -> A2 -> Y2)
        Assert.assertEquals(PinState.LOW, getStateForPin(results, outputStrip, 3));  // Oczekiwane LOW na wyjściu 3
        Assert.assertEquals(PinState.HIGH, getStateForPin(results, outputStrip, 4)); // Oczekiwane HIGH na wyjściu 4
        Assert.assertEquals(PinState.LOW, getStateForPin(results, outputStrip, 5));  // Oczekiwane LOW na wyjściu 5
        Assert.assertEquals(PinState.HIGH, getStateForPin(results, outputStrip, 6)); // Oczekiwane HIGH na wyjściu 6
    }

    // Tabela stanów wejściowych dla układu 7404
    private void tableForChip7404(Map<Integer, Set<ComponentPinState>> states0List) {
        // Tworzenie zestawu stanów wejść
        // 1: Wysoki na A1, reszta niska
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2: Wysoki na A2, reszta niska
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3: Wysoki na A3, reszta niska
        Set<ComponentPinState> statesSim3 = fillSet(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
    }

    // Uzupełnianie stanów wejściowych dla 6 pinów (A1-A6)
    private Set<ComponentPinState> fillSet(PinState stateA1, PinState stateA2, PinState stateA3, PinState stateA4, PinState stateA5, PinState stateA6) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        // Zakładamy, że inputPinHeader jest podłączony do indeksu 0 i ma 6 pinów
        statesSim.add(new ComponentPinState(0, 1, stateA1));
        statesSim.add(new ComponentPinState(0, 2, stateA2));
        statesSim.add(new ComponentPinState(0, 3, stateA3));
        statesSim.add(new ComponentPinState(0, 4, stateA4));
        statesSim.add(new ComponentPinState(0, 5, stateA5));
        statesSim.add(new ComponentPinState(0, 6, stateA6));
        return statesSim;
    }
}
