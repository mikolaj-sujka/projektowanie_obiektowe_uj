package src.main.extensions;


import src.main.entities.Component;
import src.main.entities.PinHeaderComponent;

import java.util.HashMap;
import java.util.Map;

public class PinHeaderFactory implements IPinHeaderFactory{
    private static int nextComponentId = 1; // Generator unikalnych ID
    private Map<Integer, Component> components = new HashMap<>();

    @Override
    public int createOutputPinHeader(int size) {
        Component pinHeader = new PinHeaderComponent(nextComponentId, size, true);
        int componentId = nextComponentId++;
        components.put(componentId, pinHeader);
        return componentId;
    }

    @Override
    public int createInputPinHeader(int size) {
        Component pinHeader = new PinHeaderComponent(nextComponentId, size, false);
        int componentId = nextComponentId++;
        components.put(componentId, pinHeader);
        return componentId;
    }
}
