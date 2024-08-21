package services;

import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.interfaces.PinState;
import edu.uj.po.simulation.interfaces.UnknownPin;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import entities.Component;
import entities.Pin;
import observers.PinStateObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationService
{
    private Set<PinStateObserver> observers = new HashSet<>();
    private Map<Integer, Component> components = new HashMap<>();

    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException, UnknownPin {
        // Iterujemy przez dostarczone stany pinów
        for (ComponentPinState state : states) {
            // Pobieramy komponent na podstawie jego ID
            Component component = getComponentById(state.componentId());

            if (component == null) {
                throw new UnknownStateException(state);
            }

            // Pobieramy pin z komponentu na podstawie jego ID
            Pin pin = component.getPin(state.pinId());

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
        return null;
    }

    private Set<ComponentPinState> simulateTick(Set<ComponentPinState> states) {
        // Tu przykładowa logika symulacji dla jednego ticka
        return states; // W zależności od potrzeb ta metoda może być rozbudowana
    }

    private void notifyObserversAtTick(Set<ComponentPinState> states, int tick) {
        for (ComponentPinState state : states) {
            for (PinStateObserver observer : observers) {
                //observer.update(state);
            }
        }
    }

    private void notifyObservers(ComponentPinState state) {
        for (PinStateObserver observer : observers) {
            //observer.update(state);
        }
    }

    private Component getComponentById(int id) {
        return components.get(id); // Retrieve the component by its ID from the map
    }

    public void addComponent(Component component) {
        components.put(component.getId(), component); // Add the component to the map using its ID as the key
    }
}
