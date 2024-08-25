package src.main.services;


import src.main.edu.uj.po.simulation.interfaces.ComponentPinState;
import src.main.edu.uj.po.simulation.interfaces.UnknownStateException;
import src.main.entities.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
            // Temporarily remove the component from the system
            simulationService.removeComponent(component.getId());

            // Run the simulation without this component
            Map<Integer, Set<ComponentPinState>> modifiedSimulation = simulationService.simulation(states0, ticks);

            // Compare the baseline and modified simulations
            if (baselineSimulation.equals(modifiedSimulation)) {
                // If the results are the same, this component can be removed
                removableComponents.add(component.getId());
            }

            // Restore the component to the system
            simulationService.addComponent(component);
        }

        return removableComponents;
    }
}
