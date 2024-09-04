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

        List<Integer> componentsToRemove = new ArrayList<>(simulationService.getComponents().keySet());

        for (Integer componentId : componentsToRemove) {
            Component component = simulationService.getComponentById(componentId);

            simulationService.removeComponent(component.getId());

            try {
                Map<Integer, Set<ComponentPinState>> modifiedSimulation = simulationService.simulation(states0, ticks);

                if (baselineSimulation.equals(modifiedSimulation)) {
                    removableComponents.add(component.getId());
                }

            } catch (UnknownStateException e) {
                simulationService.addComponent(component);
            }

            simulationService.addComponent(component);
        }

        return removableComponents;
    }
}
