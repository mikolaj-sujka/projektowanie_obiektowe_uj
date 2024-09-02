package edu.uj.po.simulation.extensions;

import edu.uj.po.simulation.entities.Component;
import edu.uj.po.simulation.entities.PinHeaderComponent;

public class PinHeaderFactory implements IPinHeaderFactory{
    public Component createOutputPinHeader(int size) {
        int componentId = UniqueIdGenerator.getNextId();
        Component pinHeader = new PinHeaderComponent(componentId, size, true);
        pinHeader.setId(componentId);
        return pinHeader;
    }

    public Component createInputPinHeader(int size) {
        int componentId = UniqueIdGenerator.getNextId();
        Component pinHeader = new PinHeaderComponent(componentId, size, false);
        pinHeader.setId(componentId);
        return pinHeader;
    }
}
