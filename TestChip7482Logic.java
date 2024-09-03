import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7482Logic {

    int inputStrip;

    @Test
    public void chip7442ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        inputStrip = simulation.createInputPinHeader(5);
        int chip = simulation.createChip(7482);
        int outputStrip = simulation.createOutputPinHeader(3);
        //inputs
        simulation.connect(inputStrip, 1, chip, 2); //A1
        simulation.connect(inputStrip, 2, chip, 14); //A2
        simulation.connect(inputStrip, 3, chip, 3); //B1
        simulation.connect(inputStrip, 4, chip, 13); //B2
        simulation.connect(inputStrip, 5, chip, 5); //C0
        //outputs
        simulation.connect(outputStrip, 1, chip, 1); //E1
        simulation.connect(outputStrip, 2, chip, 12); //E2
        simulation.connect(outputStrip, 3, chip, 10); //C2

        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 3, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 5, PinState.LOW));
        simulation.stationaryState(states);

        Map<Integer,Set<ComponentPinState>> states0List = new HashMap<>();
        //loading table of input states
        List<List<PinState>> simulationPinStates = inputTable();
        List<List<PinState>> simulationResultsStates = outputTable();

        for (int i = 0; i < simulationPinStates.size(); i++) {
            states0List.put(i, fillSet(simulationPinStates.get(i)));
        }

        //24
        for (int i = 0; i < simulationPinStates.size(); i++) {
            Map<Integer,Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 1));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(1));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            System.out.println("actual: " + list.toString() );
            System.out.println("expected: " + simulationResultsStates.get(i).toString() );

            for (int j = 0; j < list.size(); j++) {
                Assert.assertEquals(simulationResultsStates.get(i).get(j), list.get(j).state());
            }
        }
    }

    private Set<ComponentPinState> fillSet(List<PinState> stateList) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        statesSim.add(new ComponentPinState(inputStrip, 1, stateList.get(0))); //A1
        statesSim.add(new ComponentPinState(inputStrip, 3, stateList.get(1))); //B1
        statesSim.add(new ComponentPinState(inputStrip, 2, stateList.get(2))); //A2
        statesSim.add(new ComponentPinState(inputStrip, 4, stateList.get(3))); //B2
        statesSim.add(new ComponentPinState(inputStrip, 5, stateList.get(4))); //C0
        return statesSim;
    }

    private List<List<PinState>> inputTable() {
        //A1,B1,A2,B2 C0
        List<List<PinState>> inputStatesTable = new ArrayList<>();
        //C0 = 0
        //1
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW));
        //2
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW));
        //3
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW));
        //4
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW));
        //5
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW));
        //6
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW));
        //7
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW));
        //8
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW));
        //9
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW));
        //10
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW));
        //11
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW));
        //12
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW));

        //C0 = 1
        //1
        //1
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH));
        //2
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH));
        //3
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH));
        //4
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH));
        //5
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.HIGH));
        //6
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.LOW, PinState.HIGH));
        //7
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH));
        //8
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.HIGH));
        //9
        inputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH));
        //10
        inputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH));
        //11
        inputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH));
        //12
        inputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH));

        return inputStatesTable;
    }

    private List<List<PinState>> outputTable() {
        //E1,E2,C2
        List<List<PinState>> outputStatesTable = new ArrayList<>();

        //C0 = 0
        //1
        outputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.LOW));
        //2
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.LOW));
        //3
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.LOW));
        //4
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW));
        //5
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW));
        //6
        outputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW));
        //7
        outputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW));
        //8
        outputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH));
        //9
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW));
        //10
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH));
        //11
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH));
        //12
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH));

        //C0 = 1
        //1
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.LOW));
        //2
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW));
        //3
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.LOW));
        //4
        outputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW));
        //5
        outputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.LOW));
        //6
        outputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH));
        //7
        outputStatesTable.add(List.of(PinState.LOW, PinState.LOW, PinState.HIGH));
        //8
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH));
        //9
        outputStatesTable.add(List.of(PinState.HIGH, PinState.LOW, PinState.HIGH));
        //10
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH));
        //11
        outputStatesTable.add(List.of(PinState.LOW, PinState.HIGH, PinState.HIGH));
        //12
        outputStatesTable.add(List.of(PinState.HIGH, PinState.HIGH, PinState.HIGH));

        return outputStatesTable;
    }
}