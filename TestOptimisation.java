import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.services.OptimizationService;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestOptimisation {
    @Test
    public void testOptimise() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        ComponentPinState pin1 = new ComponentPinState(0, 1, PinState.HIGH);
        ComponentPinState pin2 = new ComponentPinState(0, 2, PinState.HIGH);
        ComponentPinState pin3 = new ComponentPinState(0, 3, PinState.LOW);
        ComponentPinState pin4 = new ComponentPinState(0, 4, PinState.LOW);
        ComponentPinState pin5 = new ComponentPinState(0, 5, PinState.HIGH);
        ComponentPinState pin6 = new ComponentPinState(0, 6, PinState.HIGH);
        ComponentPinState pin7 = new ComponentPinState(0, 7, PinState.LOW);

        Set<ComponentPinState> pinStates = Set.of(pin1, pin2, pin3, pin4, pin5, pin6, pin7);

        simulation.createInputPinHeader(7); //0
        simulation.createChip(7408); //1
        simulation.createChip(7408); //2
        simulation.createChip(7408); //3
        simulation.createOutputPinHeader(2); //4

        simulation.createChip(7444);  //5
        simulation.createOutputPinHeader(1); //6
        //0 - input, 1-3 - 7408, 5 - 7444, 4,6 - output

        //input 7444
        simulation.connect(0, 1, 5, 14);
        simulation.connect(0, 1, 5, 15);
        simulation.connect(0, 1, 5, 12);
        simulation.connect(0, 1, 5, 13);
        //output - 7444
        simulation.connect(6, 1, 5, 1);

        simulation.connect(0, 1, 3, 5);
        simulation.connect(0, 2, 1, 2);
        simulation.connect(0, 3, 1, 1);
        simulation.connect(0, 4, 2, 2);
        simulation.connect(0, 5, 2, 1);
        simulation.connect(0, 6, 2, 5);
        simulation.connect(0, 7, 2, 4);

        simulation.connect(1, 3, 3, 13);
        simulation.connect(2, 3, 3, 12);
        simulation.connect(2, 6, 3, 4);

        simulation.connect(3, 6, 4, 1);
        simulation.connect(3, 11, 4, 2);


        try {
            simulation.stationaryState(pinStates);
        } catch (UnknownStateException e) {
            System.out.println(e.pinState());
        }

        pin5 = new ComponentPinState(0, 5, PinState.LOW);
        pin7 = new ComponentPinState(0, 7, PinState.HIGH);

        Set<ComponentPinState> pinStates0 = Set.of(pin1, pin2, pin3, pin4, pin5, pin6, pin7);


        Map<Integer, Set<ComponentPinState>> map = simulation.simulation(pinStates0, 5);
        System.out.println(map.get(0) + " size: " + map.get(0).size());
        System.out.println(map.get(1) + " size: " + map.get(0).size());
        System.out.println(map.get(2) + " size: " + map.get(0).size());
        System.out.println(map.get(3) + " size: " + map.get(0).size());
        System.out.println(map.get(4) + " size: " + map.get(0).size());
        System.out.println(map.get(5));

        System.out.println(simulation.optimize(pinStates0, 5));
    }

    @Test
    public void testSimOpt5() throws UnknownChip, UnknownPin, ShortCircuitException, UnknownComponent, UnknownStateException {
        Simulation simulation = new Simulation();

        int inputPinHeader = simulation.createInputPinHeader(11); //0
        int chip7404_1 = simulation.createChip(7404); //1
        int chip7404_2 = simulation.createChip(7404); //2
        int chip7404_3 = simulation.createChip(7404); //3
        int chip7402 = simulation.createChip(7402); //4
        int chip7408 = simulation.createChip(7408); //5
        int chip7408_2 = simulation.createChip(7408); //6 -unconnected
        int chip7431 = simulation.createChip(7431); //7
        int chip7432 = simulation.createChip(7432); //8
        int chip74152 = simulation.createChip(74152); //9
        int outputPinHeader = simulation.createOutputPinHeader(2); //10
        //inputStrip
        simulation.connect(inputPinHeader,1,chip74152,5);
        simulation.connect(inputPinHeader,2,chip74152,4);
        simulation.connect(inputPinHeader,3,chip74152,3);
        simulation.connect(inputPinHeader,4,chip74152,2);
        simulation.connect(inputPinHeader,5,chip74152,1);
        simulation.connect(inputPinHeader,6,chip74152,13);
        simulation.connect(inputPinHeader,7,chip74152,12);
        simulation.connect(inputPinHeader,8,chip74152,11);
        simulation.connect(inputPinHeader,9,chip74152,10);
        simulation.connect(inputPinHeader,10,chip74152,9);
        simulation.connect(inputPinHeader,11,chip74152,8);

        simulation.connect(chip7432,1,chip7432,2);
        //connection between chip74152 and chip7432
        simulation.connect(chip74152,6,chip7432,1);
//        simulation.connect(chip74152,6,chip7404_1,1);
//        simulation.connect(chip74152,6,chip7404_2,1);

        simulation.connect(chip7432,3,chip7404_1,1);
        simulation.connect(chip7432,3,chip7404_2,1);

        //up part
        simulation.connect(chip7404_1,2,chip7404_3,1);
        simulation.connect(chip7404_3,2,chip7404_3,3);
        simulation.connect(chip7404_3,4,chip7408,1);
        //down part
        simulation.connect(chip7404_2,2,chip7404_2,3);
        simulation.connect(chip7404_2,4,chip7408,2);
        //left
//        simulation.connect(chip7408,3,chip7402,3);
//        simulation.connect(chip7432,3,chip7402,3);
        simulation.connect(chip7432,3,chip7431,15);
        simulation.connect(chip7431,14,chip7402,3);


        simulation.connect(chip7408,3,chip7431,1); // to right paprt
        //right
        simulation.connect(chip7431,2,chip7431,13);
        simulation.connect(chip7431,12,chip7402,2);


        simulation.connect(outputPinHeader,1,chip7402,1);
        //debug outputs
        simulation.connect(outputPinHeader,2,chip74152,6);

        //only changing pin1 for test
        ComponentPinState pin1 = new ComponentPinState(0, 1, PinState.HIGH);
        ComponentPinState pin2 = new ComponentPinState(0, 2, PinState.HIGH);
        ComponentPinState pin3 = new ComponentPinState(0, 3, PinState.HIGH);
        ComponentPinState pin4 = new ComponentPinState(0, 4, PinState.HIGH);
        ComponentPinState pin5 = new ComponentPinState(0, 5, PinState.HIGH);
        ComponentPinState pin6 = new ComponentPinState(0, 6, PinState.HIGH);
        ComponentPinState pin7 = new ComponentPinState(0, 7, PinState.HIGH);
        ComponentPinState pin8 = new ComponentPinState(0, 8, PinState.HIGH);
        ComponentPinState pin9 = new ComponentPinState(0, 9, PinState.LOW);
        ComponentPinState pin10 = new ComponentPinState(0, 10, PinState.LOW);
        ComponentPinState pin11 = new ComponentPinState(0, 11, PinState.LOW);

        Set<ComponentPinState> pinStates = Set.of(pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, pin9, pin10, pin11);

        try {
            simulation.stationaryState(pinStates);
        } catch (UnknownStateException e) {
            System.out.println(e.pinState());
        }
//        pin1 = new ComponentPinState(0, 1, PinState.LOW);
//        pin7 = new ComponentPinState(0, 7, PinState.HIGH);

        Set<ComponentPinState> pinStates0 = Set.of(pin1, pin2, pin3, pin4, pin5, pin6, pin7, pin8, pin9, pin10, pin11);


        Map<Integer, Set<ComponentPinState>> map = simulation.simulation(pinStates0, 8);
        System.out.println(map.get(0));
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(3));
        System.out.println(map.get(4));
        System.out.println(map.get(5));
        System.out.println(map.get(6));
        System.out.println(map.get(7));
        System.out.println(map.get(8));
//        System.out.println(map.get(9));
//        System.out.println(map.get(10));
//        System.out.println(map.get(11));


        System.out.println("Optimization");
        //When pin1 state - LOW - [2,6]
        //When pin1 state - HIGH - [1,2,3,5,6]
        System.out.println(simulation.optimize(pinStates0, 8));

    }
}