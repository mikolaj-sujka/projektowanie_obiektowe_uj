import edu.uj.po.simulation.interfaces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestChip7442Logic {

    @Test
    public void chip7442ComponentLogic() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputStrip = simulation.createInputPinHeader(4);
        int chip = simulation.createChip(7442);
        int outputStrip = simulation.createOutputPinHeader(10);
        //inputs
        simulation.connect(inputStrip, 1, chip, 15); //A
        simulation.connect(inputStrip, 2, chip, 14); //B
        simulation.connect(inputStrip, 3, chip, 13); //C
        simulation.connect(inputStrip, 4, chip, 12); //D
        //outputs
        simulation.connect(outputStrip, 1, chip, 1);
        simulation.connect(outputStrip, 2, chip, 2);
        simulation.connect(outputStrip, 3, chip, 3);
        simulation.connect(outputStrip, 4, chip, 4);
        simulation.connect(outputStrip, 5, chip, 5);
        simulation.connect(outputStrip, 6, chip, 6);
        simulation.connect(outputStrip, 7, chip, 7);
        simulation.connect(outputStrip, 8, chip, 9);
        simulation.connect(outputStrip, 9, chip, 10);
        simulation.connect(outputStrip, 10, chip,11);

        Set<ComponentPinState> states = new HashSet<>();
        states.add(new ComponentPinState(inputStrip, 1, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 2, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 3, PinState.LOW));
        states.add(new ComponentPinState(inputStrip, 4, PinState.LOW));
        simulation.stationaryState(states);

        Map<Integer,Set<ComponentPinState>> states0List = new HashMap<>();
        states0List.put(0,states);
        tableForChip7444(states0List);


        for (int i = 0; i < 11; i++) {
            Map<Integer,Set<ComponentPinState>> results = new HashMap<>(simulation.simulation(states0List.get(i), 5));

            System.out.println(i);
            List<ComponentPinState> list = new ArrayList<>(results.get(5));
            list.sort(Comparator.comparing(ComponentPinState::pinId));

            for (int j = 0; j < list.size(); j++) {
                if (j == i) {
                    Assert.assertEquals(PinState.LOW ,list.get(j).state());
                } else {
                    Assert.assertEquals(PinState.HIGH ,list.get(j).state());
                }
            }
        }
    }

    private void tableForChip7444(Map<Integer,Set<ComponentPinState>> states0List) {
        //sequence of pins: A,B,C,D
        //1
        Set<ComponentPinState> statesSim1 = fillSet(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.LOW);
        states0List.put(1, statesSim1);
        //2
        Set<ComponentPinState> statesSim2 = fillSet(PinState.LOW, PinState.HIGH, PinState.LOW, PinState.LOW);
        states0List.put(2, statesSim2);
        //3
        Set<ComponentPinState> statesSim3 = fillSet(PinState.HIGH, PinState.HIGH, PinState.LOW, PinState.LOW);
        states0List.put(3, statesSim3);
        //4
        Set<ComponentPinState> statesSim4 = fillSet(PinState.LOW, PinState.LOW, PinState.HIGH, PinState.LOW);
        states0List.put(4, statesSim4);
        //5
        Set<ComponentPinState> statesSim5 = fillSet(PinState.HIGH, PinState.LOW, PinState.HIGH, PinState.LOW);
        states0List.put(5, statesSim5);
        //6
        Set<ComponentPinState> statesSim6 = fillSet(PinState.LOW, PinState.HIGH, PinState.HIGH, PinState.LOW);
        states0List.put(6, statesSim6);
        //7
        Set<ComponentPinState> statesSim7 = fillSet(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.LOW);
        states0List.put(7, statesSim7);
        //8
        Set<ComponentPinState> statesSim8 = fillSet(PinState.LOW, PinState.LOW, PinState.LOW, PinState.HIGH);
        states0List.put(8, statesSim8);
        //9
        Set<ComponentPinState> statesSim9 = fillSet(PinState.HIGH, PinState.LOW, PinState.LOW, PinState.HIGH);
        states0List.put(9, statesSim9);
        //10
        Set<ComponentPinState> statesSim10 = fillSet(PinState.HIGH, PinState.HIGH, PinState.HIGH, PinState.HIGH);
        states0List.put(10, statesSim10);
    }

    private Set<ComponentPinState> fillSet(PinState stateA, PinState stateB, PinState stateC, PinState stateD) {
        Set<ComponentPinState> statesSim = new HashSet<>();
        //inputPinHeader should be 0 and have 4 pins
        statesSim.add(new ComponentPinState(0, 1, stateA));
        statesSim.add(new ComponentPinState(0, 2, stateB));
        statesSim.add(new ComponentPinState(0, 3, stateC));
        statesSim.add(new ComponentPinState(0, 4, stateD));
        return statesSim;
    }
}
