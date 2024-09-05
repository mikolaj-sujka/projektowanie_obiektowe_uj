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

            if (state.state() == PinState.UNKNOWN && !pin.isConnected()) {
                throw new UnknownStateException(state);
            }

            pin.setState(state.state());

            if (pin.isConnected()) {
                updateComponentStatesForPinGroup(pin);
            }
        }

        for (Component component : components.values()) {
            component.performLogic();
        }
    }

    public Map<Integer, Set<ComponentPinState>> simulation(Set<ComponentPinState> initialStates, int ticks)
            throws UnknownStateException {
        Map<Integer, Set<ComponentPinState>> simulationResults = new HashMap<>();

        stationaryState(initialStates);

        for (int tick = 0; tick <= ticks; tick++) {
            Queue<ComponentPinState> nextStatesQueue = new LinkedList<>();

            for (Component component : components.values()) {
                component.performLogic();

                for (Pin pin : component.getPins().values()) {
                    if (pin.isOutput() && pin.isConnected()) {
                        nextStatesQueue.add(new ComponentPinState(component.getId(), pin.getPinNumber(), pin.getState()));
                    }
                }
            }

            Set<ComponentPinState> currentTickStates = new HashSet<>();
            while (!nextStatesQueue.isEmpty()) {
                ComponentPinState state = nextStatesQueue.poll();
                Component component = getComponentById(state.componentId());
                Pin outputPin = component.getPin(state.pinId());

                if (!outputPin.isOutput()) {
                    continue;
                }

                if (component.isPinHeader()) {
                    currentTickStates.add(new ComponentPinState(component.getId(), outputPin.getPinNumber(), outputPin.getState()));
                }

                for (Pin connectedPin : outputPin.getPinGroup().getPins()) {
                    if (!connectedPin.equals(outputPin)) {
                        connectedPin.setState(outputPin.getState());
                    }
                }
            }

            if (currentTickStates.isEmpty()) {
                currentTickStates = simulationResults.getOrDefault(tick - 1, new HashSet<>());
            }

            notifyObserversAtTick(currentTickStates, tick);
            simulationResults.put(tick, new HashSet<>(currentTickStates));
        }

        return simulationResults;
    }

    private void notifyObserversAtTick(Set<ComponentPinState> states, int tick) {
        for (ComponentPinState state : states) {
            for (PinStateObserver observer : observers) {
                observer.updatePinState(state.state());
            }
        }
    }

    public Component getComponentById(int id) {
        return components.get(id);
    }

    public Map<Integer, Component> getComponents() {
        return components;
    }

    public void removeComponent(int componentId) {
        components.remove(componentId);
    }

    public void addComponent(Component component) {
        components.put(component.getId(), component);
    }

    private void updateComponentStatesForPinGroup(Pin pin) {
        if (pin.getPinGroup() != null) {
            for (Pin connectedPin : pin.getPinGroup().getPins()) {
                Component connectedComponent = getComponentContainingPin(connectedPin);
                if (connectedComponent != null) {
                    connectedComponent.getPin(connectedPin.getPinNumber()).setState(connectedPin.getState());
                }
            }
        }
    }

    private Component getComponentContainingPin(Pin pin) {
        for (Component component : components.values()) {
            if (component.getPins().containsValue(pin)) {
                return component;
            }
        }
        return null;
    }
}
