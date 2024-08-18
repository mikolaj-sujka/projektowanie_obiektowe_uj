package services;

import edu.uj.po.simulation.interfaces.ComponentPinState;
import edu.uj.po.simulation.interfaces.UnknownStateException;
import observers.PinStateObserver;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationService
{
    private Set<PinStateObserver> observers = new HashSet<>();
    public void stationaryState(Set<ComponentPinState> states) throws UnknownStateException {

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
}
