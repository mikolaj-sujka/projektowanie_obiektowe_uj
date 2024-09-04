import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7400Logic {

    @Test
    public void chip7400ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(8);
        int chip = simulation.createChip(7400);
        int outputStrip = simulation.createOutputPinHeader(4);

        // Połączenia wejść i wyjść
        simulation.connect(inputStrip, 1, chip, 1);  // A1
        simulation.connect(inputStrip, 2, chip, 2);  // B1
        simulation.connect(inputStrip, 3, chip, 4);  // A2
        simulation.connect(inputStrip, 4, chip, 5);  // B2
        simulation.connect(inputStrip, 5, chip, 9);  // A3
        simulation.connect(inputStrip, 6, chip, 10); // B3
        simulation.connect(inputStrip, 7, chip, 12); // A4
        simulation.connect(inputStrip, 8, chip, 13); // B4

        simulation.connect(outputStrip, 1, chip, 3);  // Y1
        simulation.connect(outputStrip, 2, chip, 6);  // Y2
        simulation.connect(outputStrip, 3, chip, 8);  // Y3
        simulation.connect(outputStrip, 4, chip, 11); // Y4

        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 3, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 5, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 6, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 7, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 8, PinState.LOW));

        simulation.stationaryState(states);

        Map<Integer, Set<ComponentPinState>> states0List = new HashMap<>();
        states0List.put(0, states);
        tableForChip7400(states0List);

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
    public void chip7400To7400ConnectionTest() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException, UnknownPin, ShortCircuitException, UnknownComponent {
        Simulation simulation = new Simulation();

        // Tworzymy dwa układy 7400
        int chip1 = simulation.createChip(7400);
        int chip2 = simulation.createChip(7400);

        // Tworzymy PinHeaders dla wejść i wyjść
        int inputStrip1 = simulation.createInputPinHeader(2);  // Wejście do chip1
        int outputStrip1 = simulation.createOutputPinHeader(1); // Wyjście z chip1
        int inputStrip2 = simulation.createInputPinHeader(2);  // Wejście do chip2
        int outputStrip2 = simulation.createOutputPinHeader(1); // Wyjście z chip2

        // Łączymy wejścia i wyjścia dla chip1 i chip2
        simulation.connect(inputStrip1, 1, chip1, 1);  // A1 chip1
        simulation.connect(inputStrip1, 2, chip1, 2);  // B1 chip1
        simulation.connect(outputStrip1, 1, chip1, 3); // Y1 chip1

        simulation.connect(outputStrip1, 1, chip2, 1);  // Y1 chip1 -> A1 chip2
        simulation.connect(inputStrip2, 2, chip2, 2);   // B1 chip2
        simulation.connect(outputStrip2, 1, chip2, 3);  // Y1 chip2

        // Ustawienie stanu na wejściu A1 i B1 chip1 na HIGH
        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip1, 1, PinState.HIGH));  // A1 HIGH
        states.add(new ComponentPinState(inputStrip1, 2, PinState.HIGH));  // B1 HIGH
        simulation.stationaryState(states);

        // Uruchomienie symulacji dla 1 ticka
        Map<Integer, Set<ComponentPinState>> results = simulation.simulation(states, 1);

        // Sprawdzamy, czy Y1 na chip1 jest LOW (odwrócenie NAND)
        Assert.assertEquals(PinState.LOW, getStateForPin(results, outputStrip1, 1));

        // Sprawdzamy, czy stan Y1 z chip1 został przekazany na A1 w chip2
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

    // Tabela stanów wejściowych dla układu 7400
    private void tableForChip7400(Map<Integer, Set<ComponentPinState>> states0List) {
        // Tworzenie zestawu stanów wejść
        // 1: Wysoki na A1 i B1, reszta niska
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2: Wysoki na A2 i B2, reszta niska
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3: Wysoki na A3 i B3, reszta niska
        Set<ComponentPinState> statesSim3 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
    }

    // Uzupełnianie stanów wejściowych dla 8 pinów (A1-B4)
    private Set<ComponentPinState> fillSet(PinState stateA1, PinState stateB1, PinState stateA2, PinState stateB2, PinState stateA3, PinState stateB3, PinState stateA4, PinState stateB4) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        // Zakładamy, że inputPinHeader jest podłączony do indeksu 0 i ma 8 pinów
        statesSim.add(new ComponentPinState(0, 1, stateA1));
        statesSim.add(new ComponentPinState(0, 2, stateB1));
        statesSim.add(new ComponentPinState(0, 3, stateA2));
        statesSim.add(new ComponentPinState(0, 4, stateB2));
        statesSim.add(new ComponentPinState(0, 5, stateA3));
        statesSim.add(new ComponentPinState(0, 6, stateB3));
        statesSim.add(new ComponentPinState(0, 7, stateA4));
        statesSim.add(new ComponentPinState(0, 8, stateB4));
        return statesSim;
    }
}