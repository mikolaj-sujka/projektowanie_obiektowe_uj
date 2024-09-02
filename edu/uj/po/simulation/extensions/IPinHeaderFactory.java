package edu.uj.po.simulation.extensions;

import edu.uj.po.simulation.entities.Component;

public interface IPinHeaderFactory {
    Component createOutputPinHeader(int size, int componentId);
    Component createInputPinHeader(int size, int componentId);
}
