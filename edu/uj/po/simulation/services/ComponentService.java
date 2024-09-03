package edu.uj.po.simulation.services;

import edu.uj.po.simulation.interfaces.*;
import edu.uj.po.simulation.entities.*;
import edu.uj.po.simulation.extensions.*;

import java.util.HashMap;
import java.util.Map;

public class ComponentService {
    private static int componentId = 0;
    ComponentFactory componentFactory = new ComponentFactory();
    PinHeaderFactory pinHeaderFactory = new PinHeaderFactory();
    HashMap<Integer, Component> components = new HashMap<>();
    private final SimulationService simulationService;

    public ComponentService(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public int createChip(int code) throws UnknownChip {
        Component component = componentFactory.createChip(code, componentId++);
        int componentId = getComponentId(component);
        components.put(componentId, component);
        simulationService.addComponent(component);
        return componentId;
    }

    public int createOutputPinHeader(int size) {
        Component pinHeader = pinHeaderFactory.createOutputPinHeader(size, componentId++);
        components.put(getComponentId(pinHeader), pinHeader);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public int createInputPinHeader(int size) {
        Component pinHeader = pinHeaderFactory.createInputPinHeader(size, componentId++);
        components.put(getComponentId(pinHeader), pinHeader);
        simulationService.addComponent(pinHeader);
        return pinHeader.getId();
    }

    public void connect(int component1Id, int pin1, int component2Id, int pin2) throws UnknownComponent, UnknownPin, ShortCircuitException {

        if (!components.containsKey(component1Id)) {
            throw new UnknownComponent(component1Id);
        }

        if (!components.containsKey(component2Id)) {
            throw new UnknownComponent(component2Id);
        }

        Component component1 = components.get(component1Id);

        Pin pin1Obj = getPinByPinNumber(pin1, component1Id, component1.getPins());

        Component component2 = components.get(component2Id);

        Pin pin2Obj = getPinByPinNumber(pin2, component2Id, component2.getPins());

        if ( component1.isPinHeader() && component2.isPinHeader() ) {
            if (pin1Obj.isOutput() && pin2Obj.isOutput()) {
                throw new ShortCircuitException();
            }
        }

        if ( !component1.isPinHeader() && !component2.isPinHeader() ) {
            if (pin1Obj.isOutput() && pin2Obj.isOutput()) {
                throw new ShortCircuitException();
            }
        }

        pin1Obj.connect(pin2Obj);
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
