package edu.uj.po.simulation.observers;

import edu.uj.po.simulation.interfaces.PinState;

public interface IPinStateObserver {
    void updatePinState(PinState state);
}
