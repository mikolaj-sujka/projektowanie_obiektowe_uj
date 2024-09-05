package edu.uj.po.simulation.services;

import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.extensions.*;

import java.util.Map;

public class ComponentService {
    private static int componentId = 0;
    ComponentFactory componentFactory = new ComponentFactory();
    private final SimulationService simulationService;

    public ComponentService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public int createChip(int code) throws UnknownChip {
        Component component = componentFactory.createChip(code, componentId++);
        int componentId = getComponentId(component);
        simulationService.addComponent(component);
        return componentId;
    }

    public int createOutputPinHeader(int size) {
        Component pinHeader = componentFactory.createOutputPinHeader(size, componentId++);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public int createInputPinHeader(int size) {
        Component pinHeader = componentFactory.createInputPinHeader(size, componentId++);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public void connect(int component1Id, int pin1, int component2Id, int pin2) throws UnknownComponent, UnknownPin, ShortCircuitException
    {

        if (!getComponents().containsKey(component1Id)) {
            throw new UnknownComponent(component1Id);
        }

        if (!getComponents().containsKey(component2Id)) {
            throw new UnknownComponent(component2Id);
        }

        Component component1 = getComponents().get(component1Id);

        Pin firstPin = getPinByPinNumber(pin1, component1Id, component1.getPins());

        Component component2 = getComponents().get(component2Id);

        Pin secondPin = getPinByPinNumber(pin2, component2Id, component2.getPins());

        if (component1.isPinHeader()) firstPin.setIsPinHeaderPin(true);
        if (component2.isPinHeader()) secondPin.setIsPinHeaderPin(true);

        firstPin.connect(secondPin);
    }

    private Map<Integer, Component> getComponents() {
        return simulationService.getComponents();
    }

    private int getComponentId(Component component) {
        return component.getId();
    }

    private Pin getPinByPinNumber(int pinNumber, int componentId, Map<Integer, Pin> pins) throws UnknownPin {
        for (Pin pin : pins.values()) {
            if (pin.getPinNumber() == pinNumber) {
                return pin;
            }
        }
        throw new UnknownPin(componentId, pinNumber);
    }
}
