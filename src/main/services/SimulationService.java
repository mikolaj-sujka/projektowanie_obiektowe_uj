package src.main.services;


import src.main.edu.uj.po.simulation.interfaces.ComponentPinState;
import src.main.edu.uj.po.simulation.interfaces.PinState;
import src.main.edu.uj.po.simulation.interfaces.UnknownPin;
import src.main.edu.uj.po.simulation.interfaces.UnknownStateException;
import src.main.entities.Component;
import src.main.entities.Pin;
import src.main.observers.PinStateObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationService
{
    private final Set<PinStateObserver> observers = new HashSet<>();
    private final Map<Integer, Component> components = new HashMap<>();

    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {
        // Iterujemy przez dostarczone stany pinów
        for (ComponentPinState state : states) {
            // Pobieramy komponent na podstawie jego ID
            Component component = getComponentById(state.componentId());

            if (component == null) {
                throw new UnknownStateException(state);
            }

            // Pobieramy pin z komponentu na podstawie jego ID
            Pin pin;
            try {
                pin = component.getPin(state.pinId());
            } catch (UnknownPin e) {
                throw new RuntimeException(e);
            }

            if (pin == null) {
                throw new UnknownStateException(state);
            }

            // Sprawdzamy, czy stan jest UNKNOWN - jeśli tak, zgłaszamy wyjątek
            if (state.state() == PinState.UNKNOWN) {
                throw new UnknownStateException(state);
            }

            // Ustawiamy stan pinu
            pin.setState(state.state());
        }

        // Po ustawieniu stanów, iterujemy przez wszystkie komponenty, aby sprawdzić, czy są w stanie stacjonarnym
        for (Component component : components.values()) {
            for (Pin pin : component.getPins()) {
                // Jeśli którykolwiek pin pozostaje w stanie UNKNOWN, zgłaszamy wyjątek
                if (pin.getState() == PinState.UNKNOWN) {
                    ComponentPinState unknownState = new ComponentPinState(component.getId(), pin.getPinNumber(), pin.getState());
                    throw new UnknownStateException(unknownState);
                }
            }

            // Wykonujemy logikę komponentu, aby sprawdzić jego stabilność
            component.performLogic();

            // Sprawdzamy ponownie, czy po wykonaniu logiki nie ma już stanów UNKNOWN
            for (Pin pin : component.getPins()) {
                if (pin.getState() == PinState.UNKNOWN) {
                    ComponentPinState unknownState = new ComponentPinState(component.getId(), pin.getPinNumber(), pin.getState());
                    throw new UnknownStateException(unknownState);
                }
            }
        }
    }

    public Map<Integer, Set<ComponentPinState>> simulation(Set<ComponentPinState> states0, int ticks)
            throws UnknownStateException {
        Map<Integer, Set<ComponentPinState>> simulationResults = new HashMap<>();

        // Inicjalizacja systemu na podstawie stanów w chwili 0
        stationaryState(states0);

        // Symulacja dla każdego ticku
        for (int tick = 0; tick <= ticks; tick++) {
            Set<ComponentPinState> currentTickStates = simulateTick(states0);

            // Powiadomienie observerów o stanach w bieżącym ticku
            notifyObserversAtTick(currentTickStates, tick);

            // Przechowaj stany dla bieżącego ticku
            simulationResults.put(tick, currentTickStates);

            // Aktualizacja stanów na potrzeby kolejnego ticku
            states0 = currentTickStates;
        }

        return simulationResults;
    }

    private Set<ComponentPinState> simulateTick(Set<ComponentPinState> states) {
        Set<ComponentPinState> nextStates = new HashSet<>();

        for (ComponentPinState state : states) {
            Component component = getComponentById(state.componentId());
            if (component != null) {
                component.performLogic(); // Wykonanie logiki komponentu

                for (Pin pin : component.getPins()) {
                    // Jeśli stan pinu się zmienił, dodajemy nowy stan do zbioru nextStates
                    if (pin.getState() != state.state()) {
                        component.setPinState(pin.getPinNumber(), pin.getState());
                        nextStates.add(new ComponentPinState(component.getId(), pin.getPinNumber(), pin.getState()));
                    }
                }
            }
        }

        return nextStates;
    }

    private void notifyObserversAtTick(Set<ComponentPinState> states, int tick) {
        for (ComponentPinState state : states) {
            for (PinStateObserver observer : observers) {
                observer.updatePinState(state.state());
            }
        }
    }
    private Component getComponentById(int id) {
        return components.get(id); // Retrieve the component by its ID from the map
    }

    // Method to get the components map
    public Map<Integer, Component> getComponents() {
        return components;
    }

    // Method to remove a component by ID
    public void removeComponent(int componentId) {
        components.remove(componentId);
    }

    public void addComponent(Component component) {
        components.put(component.getId(), component); // Add the component to the map using its ID as the key
    }
}
