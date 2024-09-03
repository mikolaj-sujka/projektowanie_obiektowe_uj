import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.services.ComponentService;
import edu.uj.po.simulation.services.SimulationService;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7400Logic {

    @Test
    public void chip7400ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException, UnknownStateException, UnknownPin, ShortCircuitException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(8);
        int chip = simulation.createChip(7400);
        int outputStrip = simulation.createOutputPinHeader(4);

        // Połączenia wejść
        simulation.connect(inputStrip, 1, chip, 1); // 1A
        simulation.connect(inputStrip, 2, chip, 2); // 1B
        simulation.connect(inputStrip, 3, chip, 4); // 2A
        simulation.connect(inputStrip, 4, chip, 5); // 2B
        simulation.connect(inputStrip, 5, chip, 9); // 3A
        simulation.connect(inputStrip, 6, chip, 10); // 3B
        simulation.connect(inputStrip, 7, chip, 12); // 4A
        simulation.connect(inputStrip, 8, chip, 13); // 4B

        // Połączenia wyjść
        simulation.connect(outputStrip, 1, chip, 3);  // 1Y
        simulation.connect(outputStrip, 2, chip, 6);  // 2Y
        simulation.connect(outputStrip, 3, chip, 8);  // 3Y
        simulation.connect(outputStrip, 4, chip, 11); // 4Y

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

        for (int i = 0; i < 4; i++) {
            Map<Integer, Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            for (int j = 0; j < list.size(); j++) {
                if (j == i) {
                    Assert.assertEquals(PinState.LOW, list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.HIGH, list.get(j).state());
                }
            }
        }
    }

    @Test
    public void testValidConnectionComponent7400() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        SimulationService simulation = new SimulationService();
        ComponentService service = new ComponentService(simulation);

        int component7400 = service.createChip(7400);

        // Test poprawnego połączenia wejścia A1 i B1 z wyjściem Y1
        service.connect(component7400, 1, component7400, 3); // Połączenie 1A z 1Y
        service.connect(component7400, 2, component7400, 3); // Połączenie 1B z 1Y
    }

    @Test
    public void testInvalidPinConnectionComponent7400() throws UnknownChip {
        SimulationService simulation = new SimulationService();
        ComponentService service = new ComponentService(simulation);

        int component7400 = service.createChip(7400);

        // Test połączenia nieprawidłowych pinów
        Assert.assertThrows(UnknownPin.class, () -> {
            service.connect(component7400, 1, component7400, 7); // Pin 7 nie istnieje, powinien rzucić wyjątek
        });
    }

    @Test
    public void testDuplicateConnectionComponent7400() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent {
        SimulationService simulation = new SimulationService();
        ComponentService service = new ComponentService(simulation);

        int component7400 = service.createChip(7400);

        // Test próby podwójnego połączenia tych samych pinów
        service.connect(component7400, 1, component7400, 3); // Połączenie 1A z 1Y
        service.connect(component7400, 1, component7400, 3); // To samo połączenie, powinno być dozwolone
    }

    private void tableForChip7400(Map<Integer, Set<ComponentPinState>> states0List) {
        // Sekwencja stanów: A1, B1, A2, B2, A3, B3, A4, B4
        // 1
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        // 2
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        // 3
        Set<ComponentPinState> statesSim3 = fillSet(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
        // 4
        Set<ComponentPinState> statesSim4 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH);
        states0List.put(4, statesSim4);
    }

    private Set<ComponentPinState> fillSet(PinState stateA1, PinState stateB1, PinState stateA2, PinState stateB2, PinState stateA3, PinState stateB3, PinState stateA4, PinState stateB4) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        // inputPinHeader powinno być 0 i mieć 8 pinów
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
