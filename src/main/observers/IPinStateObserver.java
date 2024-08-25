package src.main.observers;


import src.main.edu.uj.po.simulation.interfaces.PinState;

public interface IPinStateObserver {
    void updatePinState(PinState state);
}
