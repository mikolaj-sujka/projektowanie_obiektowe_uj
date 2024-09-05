package edu.uj.po.simulation.extensions;


import edu.uj.po.simulation.interfaces.UnknownChip;
import edu.uj.po.simulation.entities.Component;

public interface IComponentFactory {
    Component createChip(int code, int componentId) throws UnknownChip;
    Component createOutputPinHeader(int size, int componentId);
    Component createInputPinHeader(int size, int componentId);
}
