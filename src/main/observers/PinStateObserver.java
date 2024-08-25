package src.main.observers;


import src.main.edu.uj.po.simulation.interfaces.PinState;

public class PinStateObserver implements IPinStateObserver{
    @Override
    public void updatePinState(PinState state) {
        System.out.println("Pin state changed to: " + state);
    }
}
