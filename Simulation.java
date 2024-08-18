import edu.uj.po.simulation.interfaces.*;
import services.ComponentService;
import services.OptimizationService;
import services.SimulationService;

import java.util.Map;
import java.util.Set;

public class Simulation implements UserInterface {

    private ComponentService service;
    private SimulationService simulationService;
    private OptimizationService optimizationService;

    public Simulation() {
        service = new ComponentService();
        simulationService = new SimulationService();
        optimizationService = new OptimizationService();
    }
    @Override
    public int createChip(int code) throws UnknownChip {
        return service.createChip(code);
    }

    @Override
    public int createInputPinHeader(int size) {
        return service.createInputPinHeader(size);
    }

    @Override
    public int createOutputPinHeader(int size) {
        return service.createOutputPinHeader(size);
    }

    @Override
    public void connect(int component1, int pin1, int component2, int pin2) throws UnknownComponent, UnknownPin, ShortCircuitException {
        service.connect(component1, pin1, component2, pin2);
    }

    @Override
    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {
        simulationService.stationaryState(states);
    }

    @Override
    public Map<Integer, Set<ComponentPinState>> simulation(Set<ComponentPinState> states0, int ticks) throws UnknownStateException {
        return simulationService.simulation(states0, ticks);
    }

    @Override
    public Set<Integer> optimize(Set<ComponentPinState> states0, int ticks) throws UnknownStateException {
        return optimizationService.optimize(states0, ticks);
    }
}
