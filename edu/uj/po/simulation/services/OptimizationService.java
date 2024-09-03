package edu.uj.po.simulation.services;



import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.entities.Component;

import java.util.*;

public class OptimizationService {
    private final SimulationService simulationService;

    public OptimizationService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public Set<Integer> optimize(Set<ComponentPinState> states0, int ticks) throws UnknownStateException {
        Set<Integer> removableComponents = new HashSet<>();
        Map<Integer, Set<ComponentPinState>> baselineSimulation = simulationService.simulation(states0, ticks);

        // Przechodzimy przez wszystkie komponenty, aby sprawdzić, które można usunąć
        for (Component component : simulationService.getComponents().values()) {
            simulationService.removeComponent(component.getId());

            Map<Integer, Set<ComponentPinState>> modifiedSimulation = simulationService.simulation(states0, ticks);

            if (baselineSimulation.equals(modifiedSimulation)) {
                removableComponents.add(component.getId());
            }

            simulationService.addComponent(component);
        }

        return removableComponents;
    }
}
