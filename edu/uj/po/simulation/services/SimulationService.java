package edu.uj.po.simulation.services;


import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.observers.PinStateObserver;
import java.util.*;

public class SimulationService {
    private final Set<PinStateObserver> observers = new HashSet<>();
    private final Map<Integer, Component> components = new HashMap<>();

    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {
        for (ComponentPinState state : states) {
            Component component = getComponentById(state.componentId());

            if (component == null) {
                throw new UnknownStateException(state);
            }

            Pin pin = component.getPin(state.pinId());

            if (pin == null) {
                throw new UnknownStateException(state);
            }

            if (state.state() == PinState.UNKNOWN) {
                throw new UnknownStateException(state);
            }

            pin.setState(state.state());
        }

//        for (Component component : components.values()) {
//            // Wykonanie logiki komponentu
//            component.performLogic();
//
//            for (Pin pin : component.getPins().values()) {
//                // Sprawdzamy stan tylko tych pinów, które są połączone z innymi
//                if (pin.isConnected() && pin.getState() == PinState.UNKNOWN) {
//                    ComponentPinState unknownState = new ComponentPinState(component.getId(), pin.getPinNumber(), pin.getState());
//                    if (!isTemporaryUnknownState(component.getId(), unknownState.pinId())) {
//                        throw new UnknownStateException(unknownState);
//                    }
//                }
//            }
//        }
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
                component.performLogic();

                for (Pin pin : component.getPins().values()) {
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
        return components.get(id); // Pobranie komponentu na podstawie jego ID z mapy
    }

    // Metoda do pobrania mapy komponentów
    public Map<Integer, Component> getComponents() {
        return components;
    }

    // Metoda do usuwania komponentu na podstawie ID
    public void removeComponent(int componentId) {
        components.remove(componentId);
    }

    public void addComponent(Component component) {
        components.put(component.getId(), component); // Dodanie komponentu do mapy z użyciem jego ID jako klucza
    }

    private boolean isTemporaryUnknownState(int componentId, int pinId) {
        Component component = getComponentById(componentId);
        if (component == null) {
            return false;
        }

        Pin pin = component.getPin(pinId);
        if (pin == null || pin.getPinGroup() == null) {
            return false;
        }

        for (Pin connectedPin : pin.getPinGroup().getPins()) {
            if (connectedPin.getState() == PinState.UNKNOWN) {
                return true;
            }
        }

        return false;
    }
}
