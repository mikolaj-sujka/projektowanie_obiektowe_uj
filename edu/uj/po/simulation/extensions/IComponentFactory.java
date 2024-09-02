package edu.uj.po.simulation.extensions;


import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.entities.Component;

public interface IComponentFactory {
    public Component createChip(int code, int componentId) throws UnknownChip;
}
